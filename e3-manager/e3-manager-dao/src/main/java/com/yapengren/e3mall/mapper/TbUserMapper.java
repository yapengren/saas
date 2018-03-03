package com.yapengren.e3mall.mapper;

import com.yapengren.e3mall.pojo.TbUser;

import java.util.List;

public interface TbUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbUser record);

    TbUser selectByPrimaryKey(Long id);

    List<TbUser> selectAll();

    int updateByPrimaryKey(TbUser record);
}