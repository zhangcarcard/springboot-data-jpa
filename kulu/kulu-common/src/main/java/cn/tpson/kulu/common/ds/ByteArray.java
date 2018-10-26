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

    public int indexOf(byte[] target, int begin) {
        return indexOf(value, 0, value.length, target, 0, target.length, begin);
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

    /**
     * Code shared by String and StringBuffer to do searches. The
     * source is the character array being searched, and the target
     * is the string being searched for.
     *
     * @param   source       the characters being searched.
     * @param   sourceOffset offset of the source string.
     * @param   sourceCount  count of the source string.
     * @param   target       the characters being searched for.
     * @param   targetOffset offset of the target string.
     * @param   targetCount  count of the target string.
     * @param   fromIndex    the index to begin searching from.
     */
    static int indexOf(byte[] source, int sourceOffset, int sourceCount,
                       byte[] target, int targetOffset, int targetCount,
                       int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        byte first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first byte. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first byte, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole array. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }

    public ByteArray subBytes(int beginIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        int subLen = value.length - beginIndex;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }
        return (beginIndex == 0) ? this : subBytes(beginIndex, subLen);
    }

    public ByteArray subBytes(int beginIndex, int length) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (length > value.length) {
            throw new StringIndexOutOfBoundsException(length);
        }
        int subLen = length;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }

        if ((beginIndex == 0) && (subLen == value.length)) {
            return this;
        } else {
            byte[] dest = new byte[length];
            System.arraycopy(value, beginIndex, dest, 0, subLen);
            return new ByteArray(dest);
        }
    }

    public static byte[] subBytes(byte[] src, int beginIndex, int length) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (length > src.length) {
            throw new StringIndexOutOfBoundsException(length);
        }

        byte[] dest = new byte[length];
        System.arraycopy(src, beginIndex, dest, 0, length);
        return dest;
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
