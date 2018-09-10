package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.constant.ErrorCodeEnum;
import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.dispatcher.biz.dto.UserDTO;
import cn.tpson.kulu.dispatcher.biz.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login.html")
    public String html() {
        return "login";
    }

    @RequestMapping("/login.do")
    public String login(String username, String password, HttpSession session) {
        UserDTO user = userService.findByUsername(username);
        Assert.notNull(user, ResultVO.failResult(ErrorCodeEnum.USER_NOT_EXISTS).toString());
        Assert.isTrue(user.getPassword().equalsIgnoreCase(password), ResultVO.failResult(ErrorCodeEnum.USER_PASSWORD_ERROR).toString());

        session.setAttribute("_USER_", user);
        return "redirect:/";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpSession session) {
        session.removeAttribute("_USER_");
        return "redirect:/login.html";
    }
}
