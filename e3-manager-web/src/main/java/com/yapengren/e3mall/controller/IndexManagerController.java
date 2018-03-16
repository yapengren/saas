package com.yapengren.e3mall.controller;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护
 *
 * @author renyapeng
 */
@Controller
public class IndexManagerController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping(value = "/index/item/import")
    @ResponseBody
    public E3Result importAllItems() {
        E3Result result = searchItemService.importAllItems();
        return result;
    }
}
