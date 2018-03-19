package com.yapengren.e3mall.sso.service.impl;

import com.yapengren.e3mall.common.jedis.JedisClient;
import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.utils.JsonUtils;
import com.yapengren.e3mall.mapper.TbUserMapper;
import com.yapengren.e3mall.pojo.TbUser;
import com.yapengren.e3mall.pojo.TbUserExample;
import com.yapengren.e3mall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.yapengren.e3mall.pojo.TbUserExample.Criteria;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户管理 Service
 *
 * @author renyapeng
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${session.expire}")
    private Integer sessionExpire;

    /**
     * 数据有效性校验
     *
     * @param data
     * @param type
     * @return
     */
    @Override
    public E3Result checkDate(String data, int type) {
        // 根据不用的参数创建不用的查询条件
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        // 1：用户名  2：手机号  3：邮箱
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(data);
        } else if (type == 3) {
            criteria.andEmailEqualTo(data);
        } else {
            return E3Result.build(400, "数据类型错误");
        }

        // 执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        // 判断查询结果，如果结果中有值返回 false，如果没有值返回 tre
        if (list != null && list.size() > 0) {
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public E3Result addUser(TbUser user) {
        // 做数据有效性校验
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return E3Result.build(400, "用户名密码不能为空");
        }
        // 判断用户名、手机号、邮箱是否可用
        //1：用户名  2：手机号  3：邮箱
        E3Result e3Result = checkDate(user.getUsername(), 1);
        if (!(Boolean) e3Result.getData()) {
            return e3Result.build(400, "用户名已经存在");
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            e3Result = checkDate(user.getPhone(), 2);
            if (!(boolean) e3Result.getData()) {
                return e3Result.build(400, "手机号已经存在");
            }
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            e3Result = checkDate(user.getEmail(), 3);
            if (!(boolean) e3Result.getData()) {
                return e3Result.build(400, "邮箱已经存在");
            }
        }
        // 密码需要进行 md5 加密
        String password = user.getPassword();
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        // 补全 pojo 对象的属性
        user.setPassword(md5Pass);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // 把用户信息插入到数据库中
        userMapper.insert(user);
        return E3Result.ok();
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public E3Result userLogin(String username, String password) {
        // 判断用户名密码是否正确
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        // 执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return E3Result.build(400, "用户名或密码错误！");
        }
        TbUser user = list.get(0);
        // 如果不正确登录失败
        // 判断密码是否正确
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return E3Result.build(400, "用户名或密码错误！");
        }
        // 如果正确把用户信息保存到 Session 中
        // 生成 token，可以使用 uuid 生成
        String token = UUID.randomUUID().toString();
        // 把用户信息保存到 redis
        user.setPassword("");
        jedisClient.hset("session:" + token, "user", JsonUtils.objectToJson(user));
        // 设置 Session 的过期时间，实际就是 token 对应的 key 的过期时间
        jedisClient.expire("session:" + token, sessionExpire);
        // 把 token 写入 cookie，在服务端无法写 cookie，应该返回 token，在表现层中处理
        // 返回 E3Result，其中包含 token
        return E3Result.ok(token);
    }

    /**
     * 根据 token 查询用户信息
     *
     * @param token
     */
    @Override
    public E3Result getUserByToken(String token) {
        // 根据 token 查询 redis
        String json = jedisClient.hget("session:" + token, "user");
        // 如果没有查询到用户信息，返回登录过期
        if (StringUtils.isBlank(json)) {
            return E3Result.build(400, "用户登录已经过期");
        }
        // 如果查询到用户信息，重置过期时间
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        jedisClient.expire("session:" + token, sessionExpire);
        // 把用户信息包装到 E3Result 中返回
        return E3Result.ok(user);
    }
}
