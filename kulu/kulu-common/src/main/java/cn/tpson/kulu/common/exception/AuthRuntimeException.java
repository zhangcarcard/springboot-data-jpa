package cn.tpson.kulu.common.exception;

/**
 * Created by Zhangka in 2018/06/19
 */
public class AuthRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1359511522887055269L;

    /**
     *
     */
    public AuthRuntimeException() {
        super();
    }

    /**
     *
     * @param message
     */
    public AuthRuntimeException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public AuthRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public AuthRuntimeException(Throwable cause) {
        super(cause);
    }
}

