package cn.tpson.kulu.common.util;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Snappy（以前称Zippy）是Google基于LZ77的思路用C++语言编写的快速数据压缩与解压程序库，并在2011年开源。
 * 它的目标并非最大压缩率或与其他压缩程序库的兼容性，而是非常高的速度和合理的压缩率。
 * <p>
 * Created by Zhangka in 2018/08/24
 */
public class SnappyCompresser {
    private SnappyCompresser() {
        throw new AssertionError("No SnappyCompresser instances for you!");
    }

    public static byte[] compress(byte srcBytes[]) throws IOException {
        return Snappy.compress(srcBytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }

}
