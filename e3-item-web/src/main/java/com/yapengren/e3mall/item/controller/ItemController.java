package com.yapengren.e3mall.item.controller;

import com.yapengren.e3mall.item.pojo.Item;
import com.yapengren.e3mall.pojo.TbItem;
import com.yapengren.e3mall.pojo.TbItemDesc;
import com.yapengren.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品详情页面处理 Controller
 *
 * @author renyapeng
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/{itemId}")
    public String showItemInfo(@PathVariable long itemId, Model model) {
        // 取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        // 把 TbItem 对象转换成 Item 对象
        Item item = new Item(tbItem);
        // 取商品描述
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        // 把数据传递给 jsp
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        // 返回逻辑视图
        return "item";
    }
}
