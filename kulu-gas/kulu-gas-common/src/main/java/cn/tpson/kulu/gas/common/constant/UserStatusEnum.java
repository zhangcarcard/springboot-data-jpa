package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum UserStatusEnum {
    NORMAL((byte)0, "已审核"),
    WAITING((byte)1, "待审核"),
    REFUSE((byte)2, "拒绝");

    UserStatusEnum(Byte code, String name) {
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
        if (Objects.equals(NORMAL.code, code)) {
            return NORMAL.name;
        } else if (Objects.equals(WAITING.code, code)) {
            return WAITING.name;
        } else if (Objects.equals(REFUSE.code, code)) {
            return REFUSE.name;
        } else {
            return null;
        }
    }

    public static Byte codeOf(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (NORMAL.name.contains(name)) {
            return NORMAL.code;
        } else if (WAITING.name.contains(name)) {
            return WAITING.code;
        } else if (REFUSE.name.contains(name)) {
            return REFUSE.code;
        } else {
            return null;
        }
    }
}
