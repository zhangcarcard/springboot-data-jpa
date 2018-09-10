package cn.tpson.kulu.dispatcher.server.netty.server.watch;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.server.Server;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 云环转发服务.
 */
@Component
public class WatchServer extends Server {
    private static final Logger log = LoggerFactory.getLogger(WatchServer.class);

    @Autowired
    private WatchServerHandler watchServerHandler;
    @Autowired
    private WatchServerConfig watchServerConfig;


    @PostConstruct
    public void init() {
        log.info("正在启动监听在{}端口的服务.", watchServerConfig.getPort());
        watchServerHandler.setServerPort(watchServerConfig.getPort());

        setServerConfig(watchServerConfig);
        setBossGroup(new NioEventLoopGroup(watchServerConfig.getBoss()));
        setWorkerGroup(new NioEventLoopGroup(watchServerConfig.getWorker()));
        setChannelInitializer(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline()
//                        .addLast("decoder", new WatchDecoder())
//                        .addLast("encoder", new ByteArrayEncoder())
                        .addLast(watchServerHandler);
            }
        });

        try {
            start();
            log.info("服务启动成功，服务监听在{}端口.", watchServerConfig.getPort());
        } catch (InterruptedException e) {
            log.error("启动服务出错.", e);
        }
    }

    @PreDestroy
    public void destroy() {
        shutdown();
    }
}
