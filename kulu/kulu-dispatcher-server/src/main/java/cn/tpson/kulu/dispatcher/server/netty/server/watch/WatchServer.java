package cn.tpson.kulu.dispatcher.server.netty.server.watch;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.server.Server;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerConfig;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerHandler;
import org.springframework.stereotype.Component;

/**
 * 云环转发服务.
 */
@Component
public class WatchServer extends Server {
    public WatchServer(ServerHandler watchServerHandler, ServerConfig watchServerConfig) {
        super(watchServerHandler, watchServerConfig);
    }
}
