package com.yapengren.e3mall.portal.controller;

import com.yapengren.e3mall.content.service.ContentService;
import com.yapengren.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页处理 Controller
 *
 * @author renyapeng
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @Value("${index.ad1.cid}")
    private Long indexAd1Cid;

    /**
     * 根据分类 id 查询内容列表-首页轮播图展示
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String showIndex(Model model) {
        // 查询内容列表
        List<TbContent> ad1List = contentService.getContentList(indexAd1Cid);
        // 把数据传递给 jsp
        model.addAttribute("ad1List", ad1List);
        // 返回逻辑视图
        return "index";
    }
}
