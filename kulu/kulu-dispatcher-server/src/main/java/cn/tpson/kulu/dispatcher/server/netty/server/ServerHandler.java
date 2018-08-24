package cn.tpson.kulu.dispatcher.server.netty.server;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.client.KuluAgent;
import cn.tpson.kulu.dispatcher.server.service.RemoteBackendService;
import cn.tpson.kulu.dispatcher.server.vo.BackendVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by Zhangka in 2018/04/20
 */
public abstract class ServerHandler extends ChannelInboundHandlerAdapter {
    public static final AttributeKey<Channel> P = AttributeKey.valueOf("platform");
    public static final AttributeKey<Channel> E = AttributeKey.valueOf("eqp");
    public static final AttributeKey<String> K = AttributeKey.valueOf("key");

    private Integer serverPort;
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Autowired
    KuluAgent kuluAgent;
    @Autowired
    FlowControlExecutor flowControlExecutor;
    @Autowired
    RemoteBackendService remoteBackendService;
    @Autowired
    private Producer<String, String> producer;

    @Value("${kafka.topic.hexmsg}")
    private String topicHexmsg;
    @Value("${kafka.topic.active}")
    private String topicActive;

    public abstract String decode(String key);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        byte[] content = (byte[])msg;
        String hexMsg = toHexString(content);
        Channel platform = ctx.channel().attr(P).get();

        // 已连接，直接转发
        if (platform != null) {
            boolean autoRead = platform.isWritable() ? true : false;
            platform.config().setAutoRead(autoRead);
            platform.writeAndFlush(msg);                   //转发
            logHexMsg(ctx.channel().attr(K).get(), hexMsg);
            return;
        }

        // ===================================================
        String key = remoteBackendService.getKey(getServerPort(), hexMsg);
        if (StringUtils.isBlank(key)) {
            close(ctx);
            return;
        }

        key = decode(key);
        log.info("hash-key:{}", key);
        BackendVO backend = remoteBackendService.getBackend(getServerPort(), key);
        log.info(backend == null ? "Dispatcher-" + getServerPort() + "找不到路由服务器." : backend.toString());
        if (backend == null) {
            close(ctx);
            return;
        }

        // 关联硬件端和平台端连接
        platform = kuluAgent.connect(backend.getIp(), backend.getPort());
        Channel eqp = ctx.channel();
        platform.attr(E).setIfAbsent(eqp);
        eqp.attr(P).setIfAbsent(platform);
        eqp.attr(K).setIfAbsent(key);
        flowControlExecutor.addChannel(platform);
        flowControlExecutor.addChannel(eqp);
        platform.writeAndFlush(msg);    // 转发

        //记录日志
        logIsActive(key, true);
        logHexMsg(key, hexMsg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        log.error("exceptionCaught", cause);
        close(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
//        log.info("channelInactive");
        close(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        log.info("channelActive");
    }

    public void close(ChannelHandlerContext ctx) {
        final Channel eqp = ctx.channel();
        final Channel platform = eqp.attr(P).get();

        String key = eqp.attr(K).get();
        logIsActive(key, false);
        if (eqp != null && eqp.isActive()) {
            log.info("eqp.close():{},key:{}", eqp, key);
            eqp.close();
        }
        if (platform != null && platform.isActive()) {
            log.info("platform.close():{},key:{}", platform, key);
            platform.close();
        }
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    protected String toHexString(byte[] buf) {
        return DatatypeConverter.printHexBinary(buf);
    }

    protected void logIsActive(String key, boolean isActive) {
        if (StringUtils.isNotBlank(key)) {
            log.info("key:{}, isActive:{}", key, isActive);
            producer.send(new ProducerRecord<>(topicActive, key, Boolean.toString(isActive)));
        }
    }

    protected void logHexMsg(String key, String hexMsg) {
        log.info("key:{}, content hex:{}", key, hexMsg);
        //心跳消息不记录.
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(hexMsg) && !hexMsg.startsWith("7E0002")) {
            JSONObject msg = new JSONObject();
            msg.put("port", getServerPort());
            msg.put("msg", hexMsg);
            producer.send(new ProducerRecord<>(topicHexmsg, key, msg.toJSONString()));
        }
    }
}
