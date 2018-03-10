package com.yapengren.e3mall.controller;

import com.yapengren.e3mall.common.pojo.EasyUITreeNode;
import com.yapengren.e3mall.content.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理 Controller
 *
 * @author renyapeng
 */
@Controller
public class ContentCatController {

    @Autowired
    private ContentCatService contentCatService;

    /**
     * 展示内容分类
     */
    @RequestMapping(value = "/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") long parentId) {
        List<EasyUITreeNode> list = contentCatService.getContentCatList(parentId);
        return list;
    }

}
