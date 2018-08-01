package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.util.CookieUtils;
import cn.tpson.kulu.common.util.JsonUtils;
import cn.tpson.kulu.common.util.UUIDUtils;
import cn.tpson.kulu.dispatcher.biz.dto.UserDTO;
import cn.tpson.kulu.dispatcher.biz.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResultVO login(@NotBlank(message = "用户名不能为空.") String username,
                          @NotBlank(message = "密码不能为空.") String password) {
        UserDTO user = userService.findByUsername(username);
        if (user == null)
            return ResultVO.failResult("用户不存在.");
        if (!password.equalsIgnoreCase(user.getPassword()))
            return ResultVO.failResult("密码错误.");

        String sid = UUIDUtils.uuid();
        CookieUtils.setCookie(RequestContextUtils.getResponse(), SysUserDTO.SID, sid, SysUserCache.TIMEOUT * 60);
        sysUserCache.put(sid, sysUserDTO);
        return ResultVO.successResult(user);
    }
}
