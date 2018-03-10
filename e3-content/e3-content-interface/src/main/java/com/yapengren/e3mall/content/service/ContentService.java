package com.yapengren.e3mall.content.service;

import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;

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
}
