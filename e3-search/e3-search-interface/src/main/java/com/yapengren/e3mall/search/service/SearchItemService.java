package com.yapengren.e3mall.search.service;

import com.yapengren.e3mall.common.pojo.E3Result;

/**
 * @author renyapeng
 */
public interface SearchItemService {

    /**
     * 把商品数据导入到索引库中
     */
    E3Result importAllItems();

}
