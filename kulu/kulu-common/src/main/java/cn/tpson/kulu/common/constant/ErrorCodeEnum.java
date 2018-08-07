package cn.tpson.kulu.common.constant;

/**
 * Created by Zhangka in 2018/06/20
 */
public enum ErrorCodeEnum {
	RESULT_NOT_FOUND(404, "结果不存在"),
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
