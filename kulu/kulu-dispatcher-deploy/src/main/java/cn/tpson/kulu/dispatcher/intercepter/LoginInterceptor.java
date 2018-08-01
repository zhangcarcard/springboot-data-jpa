package cn.tpson.kulu.dispatcher.intercepter;

import cn.tpson.kulu.common.exception.AuthRuntimeException;
import cn.tpson.kulu.common.util.RequestContextUtils;
import cn.tpson.kulu.gas.cache.SysUserCache;
import cn.tpson.kulu.gas.dto.SysUserDTO;
import cn.tpson.kulu.gas.exception.AuthRuntimeException;
import cn.tpson.kulu.gas.util.RequestContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by Zhangka in 2018/06/14
 * 登录状态验证.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String sid = RequestContextUtils.getValue(SysUserDTO.SID);
        if (StringUtils.isBlank(sid)) {
            throw new AuthRuntimeException("请登录.");
        }

        SysUserDTO sysUserDTO = sysUserCache.get(sid);
        if (sysUserDTO == null) {
            throw new AuthRuntimeException("登录已过期.");
        }

        return true;
    }
}
