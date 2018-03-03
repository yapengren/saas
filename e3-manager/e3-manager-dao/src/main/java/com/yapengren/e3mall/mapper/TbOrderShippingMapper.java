package com.yapengren.e3mall.mapper;

import com.yapengren.pojo.po.TbOrderShipping;

import java.util.List;

public interface TbOrderShippingMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(TbOrderShipping record);

    TbOrderShipping selectByPrimaryKey(String orderId);

    List<TbOrderShipping> selectAll();

    int updateByPrimaryKey(TbOrderShipping record);
}