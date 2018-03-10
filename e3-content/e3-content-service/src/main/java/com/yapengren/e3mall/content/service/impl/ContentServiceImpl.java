package com.yapengren.e3mall.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.content.service.ContentService;
import com.yapengren.e3mall.mapper.TbContentMapper;
import com.yapengren.e3mall.pojo.TbContent;
import com.yapengren.e3mall.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容管理 Service
 *
 * @author renyapeng
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    /**
     * 内容列表查询
     *
     * @param categoryId
     * @param page
     * @param rows
     */
    @Override
    public EasyUIDataGridResult getContentListDataGrid(long categoryId, int page, int rows) {
        // 创建一个查询条件，设置查询条件，根据内容分类 id 查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        // 设置分页条件，使用 PageHelper
        PageHelper.startPage(page, rows);
        // 执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);
        // 从查询结果中取出分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        // 创建一个 DataGridResult 对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        // 设置属性
        result.setTotal(total);
        result.setRows(list);
        // 返回结果
        return result;
    }
}
