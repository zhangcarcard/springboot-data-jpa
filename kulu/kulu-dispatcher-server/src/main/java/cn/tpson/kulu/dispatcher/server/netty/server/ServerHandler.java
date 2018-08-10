package cn.tpson.kulu.dispatcher.server.netty.server;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.client.KuluAgent;
import cn.tpson.kulu.dispatcher.server.service.RemoteBackendService;
import cn.tpson.kulu.dispatcher.server.vo.BackendVO;
import cn.tpson.kulu.dispatcher.server.vo.HexMsgVO;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by Zhangka in 2018/04/20
 */
public abstract class ServerHandler extends ChannelInboundHandlerAdapter {
    public static final String DISPATCHER_CONNECTION_HASH = "DISPATCHER_CONNECTION_HASH";
    public static final String DISPATCHER_HEXMSG_LIST = "DISPATCHER_HEXMSG_LIST";

    protected static final int MAX_BUFFER_SIZE = 256;
    public static final AttributeKey<Channel> P = AttributeKey.valueOf("platform");
    public static final AttributeKey<Channel> E = AttributeKey.valueOf("eqp");
    public static final AttributeKey<String> K = AttributeKey.valueOf("key");
    protected final AttributeKey<ByteBuf> b = AttributeKey.valueOf("buffers");

    private Integer serverPort;
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Autowired
    KuluAgent kuluAgent;
    @Autowired
    FlowControlExecutor flowControlExecutor;
    @Autowired
    RemoteBackendService remoteBackendService;
    @Autowired
    RedisTemplate redisTemplate;

    public abstract String decode(String key);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel platform = ctx.channel().attr(P).get();

        // 已连接，直接转发
        if (platform != null) {
            String hexMsg = toHexString((ByteBuf)msg);
            boolean autoRead = platform.isWritable() ? true : false;
            platform.config().setAutoRead(autoRead);
            platform.writeAndFlush(msg);                   //转发
            logHexMsg(ctx.channel().attr(K).get(), hexMsg);
            return;
        }

        // ===================================================
        // 处理分包,为了处理性能,没有单独在decoder中解码.
        ByteBuf buf = ctx.channel().attr(b).get();
        if (buf != null) {
            buf.writeBytes((ByteBuf)msg);
        } else {
            buf = Unpooled.buffer(64);
            buf.writeBytes((ByteBuf)msg);
            ctx.channel().attr(b).setIfAbsent(buf);
        }

        String hexMsg = toHexString(buf);
        String key = remoteBackendService.getKey(getServerPort(), hexMsg);
        if (StringUtils.isBlank(key)) {
            if (buf.readableBytes() >= MAX_BUFFER_SIZE)
                close(ctx);
            return;
        }

        key = decode(key);
        log.info("hash-key:{}", key);
        BackendVO backend = remoteBackendService.getBackend(getServerPort(), key);
        log.info(backend == null ? "KuluAgent-" + getServerPort() + "找不到路由服务器." : backend.toString());
        if (backend == null) {
            close(ctx);
            return;
        }

        logHexMsg(key, hexMsg);
        platform = kuluAgent.connect(backend.getIp(), backend.getPort());
        Channel eqp = ctx.channel();
        platform.attr(P).setIfAbsent(eqp);
        eqp.attr(E).setIfAbsent(platform);
        eqp.attr(K).setIfAbsent(key);
        flowControlExecutor.addChannel(platform);
        flowControlExecutor.addChannel(eqp);
        buf.readerIndex(0);
        platform.writeAndFlush(buf);    // 转发

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("exceptionCaught", cause);
        close(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("channelInactive");
        close(ctx);
        logIsActive(ctx.channel().attr(K).get(), false);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("channelActive");
        logIsActive(ctx.channel().attr(K).get(), true);
    }

    public void close(ChannelHandlerContext ctx) {
        final Channel eqp = ctx.channel();
        final Channel platform = eqp.attr(P).get();

        if (eqp != null && eqp.isActive()) {
            log.info("eqp.close():{}", eqp);
            eqp.close();
        }
        if (platform != null && platform.isActive()) {
            log.info("platform.close():{}", platform);
            platform.close();
        }
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    protected String toHexString(ByteBuf buf) {
        int count = buf.readableBytes();
        byte[] dst = new byte[count];
        buf.getBytes(0, dst);
        return DatatypeConverter.printHexBinary(dst);
    }

    protected void logIsActive(String key, boolean isActive) {
        log.info("key:{}, isActive:{}", key, isActive);
        if (StringUtils.isNotBlank(key)) {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(DISPATCHER_CONNECTION_HASH, key, isActive ? 1 : 0);
        }
    }

    protected void logHexMsg(String key, String hexMsg) {
        log.info("key:{}, content hex:{}", key, hexMsg);
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(hexMsg)) {
            RedisOperations<String, String> redisOperations = redisTemplate.opsForList().getOperations();
            HexMsgVO hexMsgVO = new HexMsgVO(getServerPort(), key, hexMsg);
            redisOperations.opsForList().rightPush(DISPATCHER_HEXMSG_LIST, JSON.toJSONString(hexMsgVO));
        }
    }
}
