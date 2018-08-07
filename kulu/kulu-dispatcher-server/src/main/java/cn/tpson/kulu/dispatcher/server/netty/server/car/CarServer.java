package cn.tpson.kulu.dispatcher.server.netty.server.car;

import cn.tpson.kulu.dispatcher.server.netty.server.Server;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerConfig;
import org.springframework.stereotype.Component;

/**
 * 云盒转发服务.
 */
@Component
public class CarServer extends Server {
    public CarServer(CarServerHandler carServerHandler, ServerConfig carServerConfig) {
        super(carServerHandler, carServerConfig);
    }
}
