package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum CommonEnum {
    UNSUBMITTED((byte)0, "未提交"),
    SUBMITTED((byte)1, "已提交");

    CommonEnum(Byte code, String name) {
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
        if (Objects.equals(UNSUBMITTED.code, code)) {
            return UNSUBMITTED.name;
        } else if (Objects.equals(SUBMITTED.code, code)) {
            return SUBMITTED.name;
        } else {
            return null;
        }
    }

    public static Byte codeOf(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (UNSUBMITTED.name.contains(name)) {
            return UNSUBMITTED.code;
        } else if (SUBMITTED.name.contains(name)) {
            return SUBMITTED.code;
        } else {
            return null;
        }
    }

}
