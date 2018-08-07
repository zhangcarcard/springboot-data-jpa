package cn.tpson.kulu.dispatcher.server.netty.client;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.server.netty.server.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
@ChannelHandler.Sharable
public class KuluAgentHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(KuluAgentHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf)msg;
        int count = buf.readableBytes();
        byte[] dst = new byte[count];
        buf.getBytes(0, dst);
        log.info("platform.remoteAddress:" + ctx.channel().remoteAddress().toString() + "|platform.content:" + DatatypeConverter.printHexBinary(dst));

        final Channel platform = ctx.channel();
        final Channel eqp = platform.attr(ServerHandler.E).get();
        boolean autoRead = eqp.isWritable() ? true : false;
        eqp.config().setAutoRead(autoRead);
        eqp.writeAndFlush(buf);
    }
}
