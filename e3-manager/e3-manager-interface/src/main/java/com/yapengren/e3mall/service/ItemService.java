package com.yapengren.e3mall.service;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.pojo.TbItem;
import com.yapengren.e3mall.pojo.TbItemDesc;

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

    /**
     * 添加商品
     */
    E3Result addItem(TbItem item, String desc);

    /**
     * 根据商品 id 查询商品描述
     */
    TbItemDesc getItemDescById(long itemId);
}
