package com.yapengren.e3mall.content.service;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 商品分类管理 Service
 *
 * @author renyapeng
 */
public interface ContentCatService {

    /**
     * 展示内容分类
     */
    List<EasyUITreeNode> getContentCatList(long parentId);

    /**
     * 新增节点
     */
    E3Result addContentCategory(long parentId, String name);
}
