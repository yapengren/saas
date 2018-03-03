package com.yapengren.e3mall.service.impl;

import com.yapengren.e3mall.mapper.TbItemMapper;
import com.yapengren.e3mall.pojo.TbItem;
import com.yapengren.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
