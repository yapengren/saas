package com.yapengren.e3mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.mapper.TbItemMapper;
import com.yapengren.e3mall.pojo.TbItem;
import com.yapengren.e3mall.pojo.TbItemExample;
import com.yapengren.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理 service
 *
 * @author renyapeng
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    /**
     * 根据商品 id 查询商品信息
     */
    @Override
    public TbItem getItemById(long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }

    /**
     * 查询商品列表
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        // 创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        // 取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        // 取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }
}
