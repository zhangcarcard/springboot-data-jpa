package cn.tpson.kulu.common.exception;

/**
 * Created by Zhangka in 2018/06/22
 */
public class BusinessRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1359511522887055269L;

    /**
     *
     */
    public BusinessRuntimeException() {
        super();
    }

    /**
     *
     * @param message
     */
    public BusinessRuntimeException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }
}

