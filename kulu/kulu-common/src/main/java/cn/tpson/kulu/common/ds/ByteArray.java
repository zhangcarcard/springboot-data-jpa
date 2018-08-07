package cn.tpson.kulu.common.ds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangka in 2018/05/28
 */
public class ByteArray {
    private byte[] value;

    public ByteArray(byte[] bytes) {
        this.value = bytes;
    }

    public static ByteArray asByteArray(byte[] bytes) {
        return new ByteArray(bytes);
    }

    public static ByteArray asByteArray(String str) {
        return asByteArray(str.getBytes());
    }

    public static byte[] subBytes(byte[] src, int begin, int end) {
        int length = end - begin;
        byte[] dest = new byte[length];
        System.arraycopy(src, begin, dest, 0, length);
        return dest;
    }

    public int indexOf(byte[] src, int begin) {
        if (src == null || src.length <= 0 || begin < 0 || begin >= value.length)
            return -1;

        int index = 0;
        for (int i = begin; i < value.length; i++) {
            if (value[i] == src[index++]) {
                if (src.length == index) {
                    return i - src.length + 1;
                }
            } else {
                index = 0;
            }
        }

        return -1;
    }

    public int indexOf(byte[] src) {
        return indexOf(src, 0);
    }

    public int indexOf(byte b, int begin) {
        byte[] src = {b};
        return indexOf(src, begin);
    }

    public int indexOf(byte b) {
        byte[] src = {b};
        return indexOf(src, 0);
    }

    public ByteArray subBytes(int begin) {
        return subBytes(begin, value.length);
    }

    public ByteArray subBytes(int begin, int end) {
        int length = end - begin;
        byte[] dest = new byte[length];
        System.arraycopy(value, begin, dest, 0, length);
        return new ByteArray(dest);
    }

    public byte[] asBytes() {
        return this.value;
    }

    public int length() {
        return this.value.length;
    }

    public boolean startsWith(byte[] prefix) {
        return startsWith(prefix, 0);
    }

    public boolean startsWith(byte[] prefix, int toffset) {
        byte[] ta = value;
        int to = toffset;
        byte[] pa = prefix;
        int po = 0;
        int pc = prefix.length;

        while (--pc >= 0) {
            if (ta[to++] != pa[po++]) {
                return false;
            }
        }
        return true;
    }

    public boolean endsWith(byte[] suffix) {
        return startsWith(suffix, value.length - suffix.length);
    }

    public List<ByteArray> split(byte[] split) {
        List<ByteArray> list = new ArrayList<>();
        int begin = 0;
        while (begin < value.length) {
            int end = this.indexOf(split, begin);
            if (end == -1) {
                list.add(this.subBytes(begin));
                break;
            }

            if (begin == end) {
                begin = end + 1;
                continue;
            }
            list.add(this.subBytes(begin, end));
            begin = end + 1;
        }

        return list;
    }
}
