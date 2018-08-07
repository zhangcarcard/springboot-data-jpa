package cn.tpson.kulu.dispatcher.util;

import cn.tpson.kulu.common.ds.ByteArray;
import cn.tpson.kulu.common.util.ConvertUtils;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Zhangka in 2018/08/06
 */
public class ProtocalUtils {
    public static final String OFFSET_TYPE_OFFSET = "偏移量";
    public static final String OFFSET_TYPE_SPLIT = "分隔符";

    private ProtocalUtils() {
        throw new AssertionError("No ProtocalUtils instances for you!");
    }

    public static String getKey(ProtocalDTO protocal, byte[] msg) {
        if (!check(protocal, msg)) {
            return null;
        }

        if (OFFSET_TYPE_OFFSET.equals(protocal.getOffsetType())) {
            return getKeyForOffset(protocal, msg);
        } else if (OFFSET_TYPE_SPLIT.equals(protocal.getOffsetType())) {
            return getKeyForSplit(protocal, msg);
        }
        return null;
    }

    /**
     *
     * @param protocal
     * @param msg
     * @return
     */
    public static String getKeyForOffset(ProtocalDTO protocal, byte[] msg) {
        if (!check(protocal, msg)) {
            return null;
        }

        String startFlag = protocal.getStartFlag();
        String endFlag = protocal.getEndFlag();
        Integer offset = protocal.getOffset();
        Integer count = protocal.getCount();
        byte[] sf = parse(startFlag);
        byte[] ef = parse(endFlag);

        ByteArray array = ByteArray.asByteArray(msg);
        if (!array.startsWith(sf) || !array.endsWith(ef)) {
            return null;
        }

        byte[] token = array.subBytes(offset, count + offset).asBytes();
        String key = DatatypeConverter.printHexBinary(token);
        return key;
    }

    public static String getKeyForSplit(ProtocalDTO protocal, byte[] msg) {
        if (!check(protocal, msg)) {
            return null;
        }

        String startFlag = protocal.getStartFlag();
        String endFlag = protocal.getEndFlag();
        String split = protocal.getSplit();
        Integer count = protocal.getCount();
        byte[] sf = parse(startFlag);
        byte[] ef = parse(endFlag);

        ByteArray array = ByteArray.asByteArray(msg);
        if (!array.startsWith(sf) || array.endsWith(ef)) {
            return null;
        }

        List<ByteArray> list = array.split(split.getBytes());
        return  list.size() < count ? null : new String(list.get(count - 1).asBytes());
    }


    protected static boolean check(ProtocalDTO protocal, byte[] msg) {
        String name = protocal.getName();
        byte[] startFlag = parse(protocal.getStartFlag());
        byte[] endFlag = parse(protocal.getEndFlag());
        String split = protocal.getSplit();
        Integer offset = protocal.getOffset();
        Integer count = protocal.getCount();

        if (StringUtils.isBlank(name))
            return false;
        if (startFlag == null)
            return false;
        if (endFlag == null)
            return false;
        if (StringUtils.isBlank(split) && offset == null)
            return false;
        if (offset != null && offset < startFlag.length)
            return false;
        if (count == null || count <= 0)
            return false;
        if (msg == null || msg.length <= startFlag.length + endFlag.length)
            return false;
        if (msg.length < offset)
            return false;

        return true;
    }

    protected static byte[] parse(String flag) {
        if (StringUtils.isBlank(flag))
            return null;

        byte[] array;
        if (flag.toUpperCase().startsWith("0X")) {
            flag = flag.substring(2);
            array = new BigInteger(flag, 16).toByteArray();
        } else {
            array = flag.getBytes();
        }

        return array;
    }
}
