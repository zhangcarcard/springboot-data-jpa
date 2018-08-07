package cn.tpson.kulu.common.exception;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Zhangka in 2018/04/11
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Throwable.class)
    public String defaultErrorHandler(HttpServletRequest req, Throwable e) {
        String message = "请求有误.";
        int errorCode = -1;

        if (e instanceof BusinessRuntimeException) {
            return e.getMessage();
        } else if (e instanceof AuthRuntimeException) {
            message = e.getMessage();
            errorCode = 403;
        } else if (e instanceof BindException) {
            BindException ex = (BindException)e;
            BindingResult bindingResult = ex.getBindingResult();
            if (bindingResult.hasErrors()) {
                message = bindingResult.getFieldError().getDefaultMessage();
            }
        } else if (e instanceof NoHandlerFoundException) {
            message = "地址不存在";
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException ex = (HttpRequestMethodNotSupportedException)e;
            message = "不支持[" + ex.getMethod() + "]方法";
        } else if (e instanceof HttpMessageNotReadableException) {
            message = "请求参数不能为空";
        } else if (e instanceof PermRuntimeException) {
            message = "无权操作";
        } else if (e instanceof ParamRuntimeException) {
            message = e.getMessage();
        }

        log.error("捕获异常", e);
        return ResultVO.failResult(message, errorCode).toString();
    }
}
