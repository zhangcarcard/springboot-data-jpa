package cn.tpson.kulu.gas.common.remote;

import java.io.Serializable;

/**
 * Created by Zhangka in 2018/07/27
 */
public class RemoteResult<T> implements Serializable {
    private boolean success;
    private T result;
    private String message;
    private int code;

    public RemoteResult() {}

    public RemoteResult(boolean success, T result, String message, int code) {
        super();
        this.success = success;
        this.result = result;
        this.message = message;
        this.code = code;
    }

    public static <T> RemoteResult<T> successResult(T t) {
        return new RemoteResult<>(true, t, "成功", 0);
    }

    public static <T> RemoteResult<T> failResult(String message) {
        return new RemoteResult<>(false, null, message, -1);
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
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
}
