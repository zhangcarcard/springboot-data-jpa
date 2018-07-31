package cn.tpson.kulu.gas.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum EqpStatusEnum {
    INSTALL_NORMAL((byte)0, "装机已审核"),
    INSTALL_WAITING((byte)1, "装机待审核"),
    INSTALL_REFUSE((byte)2, "装机已拒绝"),
    INSTALL_CANCEL((byte)3, "装机已取消"),
    UNINSTALL_WAITING((byte)4, "拆机待审核"),
    UNINSTALL_REFUSE((byte)5, "拆机已拒绝"),
    UNINSTALL_CANCEL((byte)6, "拆机已取消"),
    UNINSTALL_NORMAL((byte)7, "拆机已审核");

    EqpStatusEnum(Byte code, String name) {
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
        if (Objects.equals(INSTALL_NORMAL.code, code)) {
            return INSTALL_NORMAL.name;
        } else if (Objects.equals(INSTALL_WAITING.code, code)) {
            return INSTALL_WAITING.name;
        } else if (Objects.equals(INSTALL_REFUSE.code, code)) {
            return INSTALL_REFUSE.name;
        } else if (Objects.equals(INSTALL_CANCEL.code, code)) {
            return INSTALL_CANCEL.name;
        } else if (Objects.equals(UNINSTALL_WAITING.code, code)) {
            return UNINSTALL_WAITING.name;
        } else if (Objects.equals(UNINSTALL_REFUSE.code, code)) {
            return UNINSTALL_REFUSE.name;
        } else if (Objects.equals(UNINSTALL_CANCEL.code, code)) {
            return UNINSTALL_CANCEL.name;
        } else if (Objects.equals(UNINSTALL_NORMAL.code, code)) {
            return UNINSTALL_NORMAL.name;
        } else {
            return null;
        }
    }

    public static Byte codeOf(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (INSTALL_NORMAL.name.contains(name)) {
            return INSTALL_NORMAL.code;
        } else if (INSTALL_WAITING.name.contains(name)) {
            return INSTALL_WAITING.code;
        } else if (INSTALL_REFUSE.name.contains(name)) {
            return INSTALL_REFUSE.code;
        } else if (INSTALL_CANCEL.name.contains(name)) {
            return INSTALL_CANCEL.code;
        } else if (UNINSTALL_WAITING.name.contains(name)) {
            return UNINSTALL_WAITING.code;
        } else if (UNINSTALL_REFUSE.name.contains(name)) {
            return UNINSTALL_REFUSE.code;
        } else if (UNINSTALL_CANCEL.name.contains(name)) {
            return UNINSTALL_CANCEL.code;
        } else if (UNINSTALL_NORMAL.name.contains(name)) {
            return UNINSTALL_NORMAL.code;
        } else {
            return null;
        }
    }
}
