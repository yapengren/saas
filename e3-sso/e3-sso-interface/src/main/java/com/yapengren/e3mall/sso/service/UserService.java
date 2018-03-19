package com.yapengren.e3mall.sso.service;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.pojo.TbUser;

/**
 * 用户管理 interface
 *
 * @author renyapeng
 */
public interface UserService {

    /**
     * 数据有效性校验
     *
     * @param data
     * @param type
     * @return
     */
    E3Result checkDate(String data, int type);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    E3Result addUser(TbUser user);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    E3Result userLogin(String username, String password);

    /**
     * 根据 token 查询用户信息
     */
    E3Result getUserByToken(String token);
}
