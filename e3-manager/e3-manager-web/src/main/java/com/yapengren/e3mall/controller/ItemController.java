package com.yapengren.e3mall.controller;

import com.yapengren.e3mall.pojo.TbItem;
import com.yapengren.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理 Controller
 *
 * @author renyapeng
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据商品 id 查询商品信息
     */
    @RequestMapping(value = "/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }
}
