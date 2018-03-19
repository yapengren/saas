package com.yapengren.e3mall.sso.controller;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.utils.CookieUtils;
import com.yapengren.e3mall.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录处理 Controller
 *
 * @author renyapeng
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 展示登录页面
     *
     * @return
     */
    @RequestMapping(value = "/page/login")
    public String showLogin() {
        return "login";
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public E3Result userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        // 用户登录处理
        E3Result e3Result = userService.userLogin(username, password);
        // 判断登录结果
        if (e3Result.getStatus() == 200) {
            // 取 token
            String token = e3Result.getData().toString();
            // 如果登录成功把 token 写入 cookie
            CookieUtils.setCookie(request, response, "token", token);
        }
        // 返回 E3Result
        return e3Result;
    }

}
