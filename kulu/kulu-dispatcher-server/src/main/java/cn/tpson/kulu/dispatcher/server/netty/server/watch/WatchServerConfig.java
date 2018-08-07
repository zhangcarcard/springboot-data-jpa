package cn.tpson.kulu.dispatcher.server.netty.server.watch;

import cn.tpson.kulu.dispatcher.server.netty.server.ServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Zhangka in 2018/04/12
 */
@Component
@ConfigurationProperties(prefix = "kuluagent.watch")
public class WatchServerConfig extends ServerConfig {
}
