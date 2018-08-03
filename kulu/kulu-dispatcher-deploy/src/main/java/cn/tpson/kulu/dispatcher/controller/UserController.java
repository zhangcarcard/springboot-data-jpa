package cn.tpson.kulu.dispatcher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login.html")
    public String html() {
        return "login";
    }

    @RequestMapping("/login.do")
    public String login(String username, String password, HttpServletResponse resp) {
        return "redirect:/";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletResponse resp) {
        return "redirect:login.html";
    }
}
