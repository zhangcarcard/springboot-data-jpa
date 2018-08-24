package cn.tpson.kulu.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;

/**
 * gzip的实现算法还是deflate，只是在deflate格式上增加了文件头和文件尾，
 * 同样jdk也对gzip提供了支持，分别是GZIPOutputStream和GZIPInputStream类，
 * 同样可以发现GZIPOutputStream是继承于DeflaterOutputStream的，GZIPInputStream继承于InflaterInputStream，
 * 并且可以在源码中发现writeHeader和writeTrailer方法。
 *
 * Created by Zhangka in 2018/08/24
 */
public class GzipCompresser {
    private GzipCompresser() {
        throw new AssertionError("No GzipCompresser instances for you!");
    }

    public static byte[] compress(byte srcBytes[]) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;

        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(srcBytes);
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);

        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
