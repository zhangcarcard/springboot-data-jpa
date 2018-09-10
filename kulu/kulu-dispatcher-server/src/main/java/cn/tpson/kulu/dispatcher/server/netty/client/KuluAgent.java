package cn.tpson.kulu.dispatcher.server.netty.client;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class KuluAgent {
    private static final Logger log = LoggerFactory.getLogger(KuluAgent.class);
    private Bootstrap b;
    private EventLoopGroup workerGroup;

    public KuluAgent() {
        this.b = new Bootstrap();
        this.workerGroup = new NioEventLoopGroup();
    }

    @Autowired
    KuluAgentHandler kuluAgentHandler;

    @PostConstruct
    public void init() {
        Bootstrap b = this.b;
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
//                        .addLast("decoder", new ByteArrayDecoder())
//                        .addLast("encoder", new ByteArrayEncoder())
                        .addLast(kuluAgentHandler);
            }
        });
    }

    @PreDestroy
    public void destory() {
        if (workerGroup != null && !workerGroup.isShutdown()) {
            log.info("workerGroup.shutdownGracefully()");
            workerGroup.shutdownGracefully();
            workerGroup = null;
        }
    }

    public Channel connect(String host, int port) {
        ChannelFuture f = null;
        try {
            f = b.connect(InetAddress.getByName(host).getHostAddress(), port);
        } catch (UnknownHostException e) {
            log.error("域名解析出错,host:" + host, e);
        }
        f.awaitUninterruptibly();

        assert f.isDone();

        Channel proxy;
        if (f.isCancelled()) {
            throw new RuntimeException("Connection attempt cancelled by user[host:" + host + ",port:" + port + "]");
        } else if (!f.isSuccess()) {
            f.cause().printStackTrace();
            throw new RuntimeException("failed to connect to server[host:" + host + ",port:" + port + "]");
        } else {
            proxy = f.channel();
        }

        return proxy;
    }
}
