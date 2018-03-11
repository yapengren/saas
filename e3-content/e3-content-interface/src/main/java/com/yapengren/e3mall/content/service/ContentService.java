package com.yapengren.e3mall.content.service;

import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.pojo.TbContent;

import java.util.List;

/**
 * 内容管理 Service
 *
 * @author renyapeng
 */
public interface ContentService {

    /**
     * 内容列表查询
     */
    EasyUIDataGridResult getContentListDataGrid(long categoryId, int page, int rows);

    /**
     * 根据分类 id 查询内容列表
     */
    List<TbContent> getContentList(long cid);
}
