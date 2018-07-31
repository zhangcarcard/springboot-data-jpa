package cn.tpson.kulu.gas.common.constant;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum ErrorCodeEnum {
	EQP_NOT_FOUND(1004, "设备不存在"),
	EQP_BS_NOT_FOUND(1014, "设备所在工地不存在"),
    EQP_DETECT_NOT_FOUND(1024, "检测结果不存在"),

	BLOCK_NOT_FOUND(1204, "区块信息不存在"),
    USER_NOT_FOUND(1304, "用户信息不存在"),

	SYS_OPERATE_FAIL(2000, "操作失败"),

	PERM_ERROR(403, "无权操作"),
	PARAM_ERROR(400, "参数错误");

	private int code;
	private String message;
	
	ErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
