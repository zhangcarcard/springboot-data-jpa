package cn.tpson.kulu.gas.common.ds;

/**
 * Created by Zhangka in 2018/05/28
 */
public class ByteArray {
    private byte[] data;

    public ByteArray(byte[] bytes) {
        this.data = bytes;
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
        if (src == null || src.length <= 0 || begin < 0 || begin >= data.length)
            return -1;

        int index = 0;
        for (int i = begin; i < data.length; i++) {
            if (data[i] == src[index++]) {
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
        return subBytes(begin, data.length);
    }

    public ByteArray subBytes(int begin, int end) {
        int length = end - begin;
        byte[] dest = new byte[length];
        System.arraycopy(data, begin, dest, 0, length);
        return new ByteArray(dest);
    }

    public byte[] asBytes() {
        return this.data;
    }

    public int length() {
        return this.data.length;
    }
}
