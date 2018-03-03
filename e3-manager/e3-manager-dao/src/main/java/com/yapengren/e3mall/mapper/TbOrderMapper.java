package com.yapengren.e3mall.mapper;

import com.yapengren.pojo.po.TbOrder;

import java.util.List;

public interface TbOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(TbOrder record);

    TbOrder selectByPrimaryKey(String orderId);

    List<TbOrder> selectAll();

    int updateByPrimaryKey(TbOrder record);
}