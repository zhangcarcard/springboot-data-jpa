import cn.tpson.dfs.common.logger.Logger;
import cn.tpson.dfs.common.logger.LoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.AttributeKey;

/**
 * Created by Zhangka in 2018/04/20
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);
    public static final AttributeKey<Channel> E = AttributeKey.valueOf("eqp");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        byte[] array = new byte[in.readableBytes()];
        in.getBytes(0, array);
        log.info(new String(array));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        log.error("exceptionCaught", cause);
        close(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
//        log.info("channelInactive");
        close(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        log.info("channelActive");
    }

    public void close(ChannelHandlerContext ctx) {

    }
}
