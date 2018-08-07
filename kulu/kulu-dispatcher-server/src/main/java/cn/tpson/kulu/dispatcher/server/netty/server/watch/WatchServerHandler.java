package cn.tpson.kulu.dispatcher.server.netty.server.watch;

import cn.tpson.kulu.dispatcher.server.netty.server.ServerHandler;
import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WatchServerHandler extends ServerHandler {
    @Override
    public String decode(String key) {
        return key;
    }
}
