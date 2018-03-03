package com.yapengren.e3mall.mapper;

import com.yapengren.pojo.po.TbContentCategory;

import java.util.List;

public interface TbContentCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbContentCategory record);

    TbContentCategory selectByPrimaryKey(Long id);

    List<TbContentCategory> selectAll();

    int updateByPrimaryKey(TbContentCategory record);
}