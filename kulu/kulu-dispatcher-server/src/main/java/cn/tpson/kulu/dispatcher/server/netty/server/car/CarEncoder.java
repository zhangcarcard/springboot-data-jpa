package cn.tpson.kulu.dispatcher.server.netty.server.car;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Zhangka in 2018/08/23
 */
public class CarEncoder extends MessageToByteEncoder<byte[]> {
    @Override
    public void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) {
        out.writeBytes(msg);
    }
}
