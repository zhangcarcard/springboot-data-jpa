package cn.tpson.dfs.core.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Zhangka in 2018/04/20
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        byte header = in.readByte();
        int filenameLen = in.readByte();
        byte[] filename = new byte[filenameLen];
        in.readBytes(filename);
        if (header == 'r') {

        } else if (header == 'w') {
            byte[] content = new byte[in.readableBytes()];
            in.readBytes(content);
            FileStore.put(filename, content);
        }
    }

    public static void main(String[] args) {
        byte b = 'r';
        System.out.println(b);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        log.error("exceptionCaught", cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
//        log.info("channelInactive");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        log.info("channelActive");
    }
}
