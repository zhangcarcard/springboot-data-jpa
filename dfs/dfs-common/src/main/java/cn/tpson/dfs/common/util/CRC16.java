package cn.tpson.dfs.common.util;

/**
 * Created by Zhangka in 2018/08/30
 */
public class CRC16 {
    private final int polynomial = 0x8408;
    private int[] table = new int[256];

    public short checksum(int[] bytes) {
        int crc = 0xffff;
        for (int i = 0; i < bytes.length; ++i) {
            int index = (crc ^ bytes[i]) % 256;
            crc = (crc >> 8) ^ table[index];
        }
        return (short)crc;
    }

    public CRC16() {
        int value;
        int temp;
        for (int i = 0; i < table.length; ++i) {
            value = 0;
            temp = i;
            for (byte j = 0; j < 8; ++j) {
                if (((value ^ temp) & 0x0001) != 0) {
                    value = (value >> 1) ^ polynomial;
                } else {
                    value >>= 1;
                }
                temp >>= 1;
            }
            table[i] = value;
        }
    }
}

