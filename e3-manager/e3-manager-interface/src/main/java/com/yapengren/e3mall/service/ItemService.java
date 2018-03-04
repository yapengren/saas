package com.yapengren.e3mall.service;

import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.pojo.TbItem;

/**
 * @author renyapeng
 */
public interface ItemService {

    /**
     * 根据商品 id 查询商品信息
     */
    TbItem getItemById(long itemId);

    /**
     * 查询商品列表
     */
    EasyUIDataGridResult getItemList(int page, int rows);
}
