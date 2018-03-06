package com.yapengren.e3mall.service.impl;

import com.yapengren.e3mall.common.pojo.EasyUITreeNode;
import com.yapengren.e3mall.mapper.TbItemCatMapper;
import com.yapengren.e3mall.pojo.TbItemCat;
import com.yapengren.e3mall.pojo.TbItemCatExample;
import com.yapengren.e3mall.pojo.TbItemCatExample.Criteria;
import com.yapengren.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类管理
 *
 * @author renyapeng
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /**
     * 展示商品分类列表
     */
    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        // 根据 parentId 查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        // 设置查询条件
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        // 创建返回结果 List
        List<EasyUITreeNode> resultList = new ArrayList<>();
        // 把列表转换成 EasyUITreeNode 列表
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            // 设置属性
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            // 添加到结果列表
            resultList.add(node);
        }

        // jdk1.8
        // list.stream().map(node -> {
        //     EasyUITreeNode treeNode = new EasyUITreeNode();
        //     // 设置属性
        //     treeNode.setId(tbItemCat.getId());
        //     treeNode.setText(tbItemCat.getName());
        //     treeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
        //
        //     return treeNode;
        // }).collect(Collectors.toList());

        return resultList;
    }
}
