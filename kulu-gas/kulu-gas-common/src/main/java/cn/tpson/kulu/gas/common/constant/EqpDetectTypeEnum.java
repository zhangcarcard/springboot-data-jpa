package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum EqpDetectTypeEnum {
    FIRST((byte)0, "进场检测"),
    NORMAL((byte)1, "日常检测");

    EqpDetectTypeEnum(Byte code, String name) {
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
        if (Objects.equals(FIRST.code, code)) {
            return FIRST.name;
        } else if (Objects.equals(NORMAL.code, code)) {
            return NORMAL.name;
        } else {
            return null;
        }
    }

    public static Byte codeOf(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (FIRST.name.contains(name)) {
            return FIRST.code;
        } else if (NORMAL.name.contains(name)) {
            return NORMAL.code;
        } else {
            return null;
        }
    }
}
