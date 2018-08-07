package cn.tpson.kulu.dispatcher.server.netty.server;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 云环转发服务.
 */
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerHandler serverHandler;
    private ServerConfig serverConfig;
    public Server(ServerHandler serverHandler, ServerConfig serverConfig) {
        this.serverHandler = serverHandler;
        this.serverConfig = serverConfig;
        bossGroup = new NioEventLoopGroup(serverConfig.getBoss());
        workerGroup = new NioEventLoopGroup(serverConfig.getWorker());
    }

    /**
     * 启动
     * @throws InterruptedException
     */
    @PostConstruct
    public void init() throws InterruptedException {
        ServerConfig config = serverConfig;
        ServerHandler handler = serverHandler;
        log.info("begin to start KuluAgent-{}", config.getPort());

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, config.getBacklog())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getTimeout())
                //注意是childOption
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(4 * 1024, 16 * 1024))
//                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(4 * 1024, 16 * 1024))
//                .childOption(ChannelOption.SO_SNDBUF, 1024 * 256)
//                .childOption(ChannelOption.SO_RCVBUF, 1024 * 32768)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
//                                .addLast("decoder", new KuluWatchDecoder())
//                                .addLast("encoder", new KuluWatchEncoder())
                                .addLast(handler);
                    }
                });

        handler.setServerPort(config.getPort());
        channel = serverBootstrap.bind(config.getPort()).sync().channel();
        log.info("KuluAgent-{}服务监听在{}端口", config.getPort(), config.getPort());
    }

    @PreDestroy
    public void destory() {
        log.info("destroy KuluAgent-{} resources", serverConfig.getPort());
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (channel != null) {
            channel.closeFuture().syncUninterruptibly();
        }
    }
}
