package cn.tpson.kulu.dispatcher.server.netty.server.car;

import cn.tpson.kulu.common.util.ConvertUtils;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerHandler;
import io.netty.channel.ChannelHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
@ChannelHandler.Sharable
public class CarServerHandler extends ServerHandler {
    @Override
    public String decode(String hexToken) {
        return StringUtils.isBlank(hexToken)
                ? ConvertUtils.bcd2Str(DatatypeConverter.parseHexBinary(hexToken))
                : hexToken;
    }
}
