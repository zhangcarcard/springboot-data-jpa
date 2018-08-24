package cn.tpson.kulu.dispatcher.server.netty.server.car;

import cn.tpson.kulu.common.ds.ByteArray;
import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zhangka in 2018/08/22
 */

public class CarDecoder extends ByteToMessageDecoder {
    private static final byte[] flag = {0x7e};

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] msg = new byte[in.readableBytes()];
        in.getBytes(0, msg);

        ByteArray byteArray = ByteArray.asByteArray(msg);
        if (byteArray.startsWith(flag)) {
            int end = byteArray.indexOf(flag[0], 1);
            if (end != -1) {
                out.add(byteArray.subBytes(0, end + 1).asBytes());
                in.readerIndex(end + 1);
            }
        }
    }
}
