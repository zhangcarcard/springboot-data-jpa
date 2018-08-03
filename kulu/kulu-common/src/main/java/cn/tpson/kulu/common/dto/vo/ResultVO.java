package cn.tpson.kulu.common.dto.vo;

import cn.tpson.kulu.common.constant.ErrorCodeEnum;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO {
	private boolean success;
	private String message;
	private int code;
	private Object data;
	
	public ResultVO(boolean success, String message, int code, Object data) {
		this.success = success;
		this.message = message;
		this.code = code;
		this.data = data;
	}
	
	public static ResultVO successResult() {
		return new ResultVO(true, "成功", 0, null);
	}
	
	public static ResultVO successResult(Object data) {
		return new ResultVO(true, "成功", 0, data);
	}
	
	public static ResultVO failResult(String message) {
		return new ResultVO(false, message, -1, null);
	}

	public static ResultVO failResult(String message, int code) {
		return new ResultVO(false, message, code, null);
	}

    public static ResultVO failResult(final ErrorCodeEnum errorCodeEnum) {
        return new ResultVO(false, errorCodeEnum.getMessage(), errorCodeEnum.getCode(), null);
    }
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("success", success);
		json.put("message", message);
		json.put("code", code);
		json.put("data", data);

		return json.toJSONString();
	}
}
