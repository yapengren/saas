package com.yapengren.e3mall.sso.controller;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.pojo.TbUser;
import com.yapengren.e3mall.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户注册 Controller
 *
 * @author renyapeng
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 展示注册页面
     */
    @RequestMapping(value = "/page/register")
    public String showRegister() {
        return "register";
    }

    /**
     * 数据有效性校验
     *
     * @param data
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/check/{data}/{type}")
    @ResponseBody
    public E3Result checkDate(@PathVariable String data, @PathVariable int type) {
        E3Result result = userService.checkDate(data, type);
        return result;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public E3Result addUser(TbUser user) {
        E3Result result = userService.addUser(user);
        return result;
    }
}
