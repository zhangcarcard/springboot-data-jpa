package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum EqpDetectStatusEnum {
    UNSUBMITTED((byte)0, "未提交"),
    SERVICE_SUBMITTED((byte)1, "提交到服务公司"),
    GOV_SUBMITTED((byte)2, "提交到政府部门");

    EqpDetectStatusEnum(Byte code, String name) {
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
        } else if (Objects.equals(SERVICE_SUBMITTED.code, code)) {
            return SERVICE_SUBMITTED.name;
        } else if (Objects.equals(GOV_SUBMITTED.code, code)) {
            return GOV_SUBMITTED.name;
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
        } else if (SERVICE_SUBMITTED.name.contains(name)) {
            return SERVICE_SUBMITTED.code;
        } else if (GOV_SUBMITTED.name.contains(name)) {
            return GOV_SUBMITTED.code;
        } else {
            return null;
        }
    }
}
