package com.yapengren.e3mall.controller;

import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理 Controller
 *
 * @author renyapeng
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        EasyUIDataGridResult gridResult = contentService.getContentListDataGrid(categoryId, page, rows);
        return gridResult;
    }
}
