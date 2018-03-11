package com.yapengren.e3mall.controller;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.content.service.ContentService;
import com.yapengren.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    /**
     * 根据分类 id 查询内容列表
     *
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        EasyUIDataGridResult gridResult = contentService.getContentListDataGrid(categoryId, page, rows);
        return gridResult;
    }

    /**
     * 新增内容
     *
     * @param content
     * @return
     */
    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent content) {
        E3Result result = contentService.addContent(content);
        return result;
    }
}
