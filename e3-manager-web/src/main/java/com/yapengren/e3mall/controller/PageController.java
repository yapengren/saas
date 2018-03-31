package com.yapengren.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转 Controller
 *
 * @author renyapeng
 */
@Controller
public class PageController {
    /**
     * 展示后台首页
     */
    @RequestMapping(value = "/")
    public String showIndex() {
        return "index";
    }

    /**
     * 展示对应页面
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
