package com.yapengren.e3mall.content.service.impl;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.pojo.EasyUITreeNode;
import com.yapengren.e3mall.content.service.ContentCatService;
import com.yapengren.e3mall.mapper.TbContentCategoryMapper;
import com.yapengren.e3mall.pojo.TbContentCategory;
import com.yapengren.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 新增节点
     */
    @Override
    public E3Result addContentCategory(long parentId, String name) {
        // 创建一个 TbContentCategory 对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        // 设置对象的属性
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        // 1(正常) 2(删除)
        tbContentCategory.setStatus(1);
        // 默认是 1
        tbContentCategory.setSortOrder(1);
        // 新增节点一定是叶子节点
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        // 把数据插入到数据库中
        tbContentCategoryMapper.insert(tbContentCategory);
        // 取父节点的信息
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        // 判断父节点的 isparent 属性是否是 true，如果不是应该改为 true
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            // 更新到数据库
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }
        // 返回 E3Result 其中包含 TbContentCategory 对象
        return  E3Result.ok(tbContentCategory);
    }
}
