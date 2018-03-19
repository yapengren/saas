package com.yapengren.e3mall.sso.controller;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根据 token 查询用户信息
 *
 * @author renyapeng
 */
@Controller
public class TokenController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        E3Result e3Result = userService.getUserByToken(token);
        // 判断是否是 jsonp 请求
        if (StringUtils.isNotBlank(callback)) {
            // 如果是 jsonp 请求应该响应是 js 语句
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(e3Result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        // 否则返回 json 数据
        return e3Result;
    }

}
