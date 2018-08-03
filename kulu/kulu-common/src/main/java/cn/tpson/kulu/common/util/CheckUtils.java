package cn.tpson.kulu.common.util;

import cn.tpson.kulu.common.constant.ErrorCodeEnum;
import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.exception.BusinessRuntimeException;
import cn.tpson.kulu.common.exception.PermRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Objects;

/**
 * 抛出的异常被GlobalExceptionHandler统一捕获.
 * @see org.springframework.util.Assert
 */
@Deprecated
public class CheckUtils {
	private CheckUtils() {
		throw new AssertionError("No CheckUtils instances for you!");
	}
	
	public static boolean checkNull(Object object, ErrorCodeEnum errorCode) {
		if (object == null) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkNotNull(Object object, ErrorCodeEnum errorCode) {
		if (object != null) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkBlank(String source, ErrorCodeEnum errorCode) {
		if (StringUtils.isBlank(source)) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkEquals(Object src, Object dest, ErrorCodeEnum errorCode) {
		if (!Objects.equals(src, dest)) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkNotEquals(Object src, Object dest, ErrorCodeEnum errorCode) {
		if (Objects.equals(src, dest)) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkId(Integer id, ErrorCodeEnum errorCode) {
		if (id == null || id <= 0) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkEmpty(List<?> list, ErrorCodeEnum errorCode) {
		if (list == null || list.isEmpty()) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}

	public static boolean checkNotEmpty(List<?> list, ErrorCodeEnum errorCode) {
		if (list != null && !list.isEmpty()) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}
	
	public static boolean checkTrue(Boolean bool, ErrorCodeEnum errorCode) {
		if (bool == null || !bool) {
			throw new BusinessRuntimeException(ResultVO.failResult(errorCode).toString());
		}
		return true;
	}

    public static boolean checkNumber(String number, ErrorCodeEnum errorCode) {
        if (!NumberUtils.isParsable(number)) {
            throw new BusinessRuntimeException(ResultVO.failResult(ErrorCodeEnum.PARAM_ERROR).toString());
        }
        return true;
	}
}
