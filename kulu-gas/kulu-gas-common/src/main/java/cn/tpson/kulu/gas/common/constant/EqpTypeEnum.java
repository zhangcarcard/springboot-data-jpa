package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum EqpTypeEnum {
    TYPE_1((byte)1, "挖掘机"),
    TYPE_2((byte)2, "装载机"),
    TYPE_3((byte)3, "挖掘装载机"),
    TYPE_4((byte)4, "叉车"),
    TYPE_5((byte)5, "推土机"),
    TYPE_6((byte)6, "压路机"),
    TYPE_7((byte)7, "摊铺机"),
    TYPE_8((byte)8, "铣刨机"),
    TYPE_9((byte)9, "旋挖钻机"),
    TYPE_10((byte)10, "打桩机"),
    TYPE_11((byte)11, "混泥土输送泵"),
    TYPE_12((byte)12, "强夯机"),
    TYPE_13((byte)13, "履带式吊车"),
    TYPE_14((byte)14, "发电机组"),
    TYPE_99((byte)99, "其他");

    EqpTypeEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    private Byte code;
    private String name;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String nameOf(Byte code) {
        if (Objects.equals(TYPE_1.code, code)) {
            return TYPE_1.name;
        } else if (Objects.equals(TYPE_2.code, code)) {
            return TYPE_2.name;
        } else if (Objects.equals(TYPE_3.code, code)) {
            return TYPE_3.name;
        } else if (Objects.equals(TYPE_4.code, code)) {
            return TYPE_4.name;
        } else if (Objects.equals(TYPE_5.code, code)) {
            return TYPE_5.name;
        } else if (Objects.equals(TYPE_6.code, code)) {
            return TYPE_6.name;
        } else if (Objects.equals(TYPE_7.code, code)) {
            return TYPE_7.name;
        } else if (Objects.equals(TYPE_8.code, code)) {
            return TYPE_8.name;
        } else if (Objects.equals(TYPE_9.code, code)) {
            return TYPE_9.name;
        } else if (Objects.equals(TYPE_10.code, code)) {
            return TYPE_10.name;
        } else if (Objects.equals(TYPE_11.code, code)) {
            return TYPE_11.name;
        } else if (Objects.equals(TYPE_12.code, code)) {
            return TYPE_12.name;
        } else if (Objects.equals(TYPE_13.code, code)) {
            return TYPE_13.name;
        } else if (Objects.equals(TYPE_14.code, code)) {
            return TYPE_14.name;
        } else if (Objects.equals(TYPE_99.code, code)) {
            return TYPE_99.name;
        } else {
            return null;
        }
    }

    public static Byte codeOf(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (TYPE_1.name.contains(name)) {
            return TYPE_1.code;
        } else if (TYPE_2.name.contains(name)) {
            return TYPE_2.code;
        } else if (TYPE_3.name.contains(name)) {
            return TYPE_3.code;
        } else if (TYPE_4.name.contains(name)) {
            return TYPE_4.code;
        } else if (TYPE_5.name.contains(name)) {
            return TYPE_5.code;
        } else if (TYPE_6.name.contains(name)) {
            return TYPE_6.code;
        } else if (TYPE_7.name.contains(name)) {
            return TYPE_7.code;
        } else if (TYPE_8.name.contains(name)) {
            return TYPE_8.code;
        } else if (TYPE_9.name.contains(name)) {
            return TYPE_9.code;
        } else if (TYPE_10.name.contains(name)) {
            return TYPE_10.code;
        } else if (TYPE_11.name.contains(name)) {
            return TYPE_11.code;
        } else if (TYPE_12.name.contains(name)) {
            return TYPE_12.code;
        } else if (TYPE_13.name.contains(name)) {
            return TYPE_13.code;
        } else if (TYPE_14.name.contains(name)) {
            return TYPE_14.code;
        } else if (TYPE_99.name.contains(name)) {
            return TYPE_99.code;
        } else {
            return null;
        }
    }
}
