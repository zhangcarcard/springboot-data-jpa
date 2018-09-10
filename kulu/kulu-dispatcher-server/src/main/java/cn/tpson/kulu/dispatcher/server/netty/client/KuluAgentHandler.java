package cn.tpson.kulu.dispatcher.server.netty.client;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.springframework.stereotype.Component;
import scala.Byte;

import javax.xml.bind.DatatypeConverter;

@Component
@ChannelHandler.Sharable
public class KuluAgentHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(KuluAgentHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        byte[] array = new byte[in.readableBytes()];
        in.getBytes(0, array);
        log.info("platform.remoteAddress:" + ctx.channel().remoteAddress().toString() + "|platform.content:" + DatatypeConverter.printHexBinary(array));

        final Channel platform = ctx.channel();
        final Channel eqp = platform.attr(ServerHandler.E).get();
        boolean autoRead = eqp.isWritable() ? true : false;
        eqp.config().setAutoRead(autoRead);
        eqp.writeAndFlush(msg);
    }
}
