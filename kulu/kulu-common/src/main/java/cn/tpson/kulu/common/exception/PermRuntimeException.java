package cn.tpson.kulu.common.exception;

/**
 * Created by Zhangka in 2018/06/22
 */
public class PermRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1359511522887055269L;

    /**
     *
     */
    public PermRuntimeException() {
        super();
    }

    /**
     *
     * @param message
     */
    public PermRuntimeException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public PermRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public PermRuntimeException(Throwable cause) {
        super(cause);
    }
}

