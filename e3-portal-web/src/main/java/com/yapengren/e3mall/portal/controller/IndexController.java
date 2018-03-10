package com.yapengren.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页处理 Controller
 *
 * @author renyapeng
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public String showIndex() {
        return "index";
    }
}
