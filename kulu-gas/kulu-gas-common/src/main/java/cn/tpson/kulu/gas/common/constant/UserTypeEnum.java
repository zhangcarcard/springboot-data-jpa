package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum UserTypeEnum {
    SYS((byte)0, "系统管理员"),
    GOV((byte)1, "政府"),
    SER((byte)2, "服务公司"),
    BUI((byte)3, "施工单位"),
    PER((byte)4, "个人机主"),
    PRO((byte)5, "工程安装");

    UserTypeEnum(Byte code, String name) {
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
        if (Objects.equals(SYS.code, code)) {
            return SYS.name;
        } else if (Objects.equals(GOV.code, code)) {
            return GOV.name;
        } else if (Objects.equals(SER.code, code)) {
            return SER.name;
        } else if (Objects.equals(BUI.code, code)) {
            return BUI.name;
        } else if (Objects.equals(PER.code, code)) {
            return PER.name;
        } else if (Objects.equals(PRO.code, code)) {
            return PRO.name;
        } else {
            return null;
        }
    }

    public static Byte codeOf(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (SYS.name.contains(name)) {
            return SYS.code;
        } else if (GOV.name.contains(name)) {
            return GOV.code;
        } else if (SER.name.contains(name)) {
            return SER.code;
        } else if (BUI.name.contains(name)) {
            return BUI.code;
        } else if (PER.name.contains(name)) {
            return PER.code;
        } else if (PRO.name.contains(name)) {
            return PRO.code;
        } else {
            return null;
        }
    }
}
