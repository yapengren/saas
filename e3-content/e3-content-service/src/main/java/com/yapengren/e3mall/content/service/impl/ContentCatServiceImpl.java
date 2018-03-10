package com.yapengren.e3mall.content.service.impl;

import com.yapengren.e3mall.common.pojo.EasyUITreeNode;
import com.yapengren.e3mall.content.service.ContentCatService;
import com.yapengren.e3mall.mapper.TbContentCategoryMapper;
import com.yapengren.e3mall.pojo.TbContentCategory;
import com.yapengren.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容分类管理 Service
 *
 * @author renyapeng
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 展示内容分类
     *
     * @param parentId
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        // 创建一个查询条件，根据 parentId 查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        // 把 ContentCategoryList 转换成 EasyUITreeNode 的列表
        List<EasyUITreeNode> treeNodes = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode treeNode = new EasyUITreeNode();
            treeNode.setId(tbContentCategory.getId());
            treeNode.setText(tbContentCategory.getName());
            // 如果节点下有子节点是"closed", 否则是"open"
            treeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            // 添加到节点列表
            treeNodes.add(treeNode);
        }

        // 返回 TreeNode 列表
        return treeNodes;
    }
}
