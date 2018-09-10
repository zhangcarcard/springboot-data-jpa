package cn.tpson.kulu.dispatcher.intercepter;

import cn.tpson.kulu.common.exception.AuthRuntimeException;
import cn.tpson.kulu.dispatcher.biz.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Zhangka in 2018/06/14
 * 登录状态验证.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("_USER_");

        if (user == null) {
            try {
                response.sendRedirect("/user/login.html");
                return false;
            } catch (IOException e) {
                log.error("出错了", e);
                throw new AuthRuntimeException("请登录.");
            }
        }

        return true;
    }
}
