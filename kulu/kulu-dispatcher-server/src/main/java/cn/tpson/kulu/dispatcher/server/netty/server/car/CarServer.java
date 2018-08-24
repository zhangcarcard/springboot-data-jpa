package cn.tpson.kulu.dispatcher.server.netty.server.car;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.server.Server;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 云盒转发服务.
 */
@Component
public class CarServer extends Server {
    private static final Logger log = LoggerFactory.getLogger(CarServer.class);

    @Autowired
    private CarServerHandler carServerHandler;
    @Autowired
    private CarServerConfig carServerConfig;


    @PostConstruct
    public void init() {
        log.info("正在启动监听在{}端口的服务.", carServerConfig.getPort());
        carServerHandler.setServerPort(carServerConfig.getPort());

        setServerConfig(carServerConfig);
        setBossGroup(new NioEventLoopGroup(carServerConfig.getBoss()));
        setWorkerGroup(new NioEventLoopGroup(carServerConfig.getWorker()));
        setChannelInitializer(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline()
                        .addLast("decoder", new CarDecoder())
                        .addLast("encoder", new CarEncoder())
                        .addLast(carServerHandler);
            }
        });

        try {
            start();
            log.info("服务启动成功，服务监听在{}端口.", carServerConfig.getPort());
        } catch (InterruptedException e) {
            log.error("启动服务出错.", e);
        }
    }

    @PreDestroy
    public void destroy() {
        shutdown();
    }
}
