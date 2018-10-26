package cn.tpson.kulu.dispatcher.server.netty.server.watch;

import cn.tpson.kulu.dispatcher.server.netty.server.ServerHandler;
import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
@ChannelHandler.Sharable
public class WatchServerHandler extends ServerHandler {
    @Override
    public String decodeKey(String hexKey) {
        return new String(DatatypeConverter.parseHexBinary(hexKey));
    }
}
