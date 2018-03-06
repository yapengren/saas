package com.yapengren.e3mall.service;

import com.yapengren.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author renyapeng
 */
public interface ItemCatService {

    /**
     * 展示商品分类列表
     */
    List<EasyUITreeNode> getItemCatList(long parentId);
}
