package com.yapengren.e3mall.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yapengren.e3mall.common.jedis.JedisClient;
import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.common.utils.IDUtils;
import com.yapengren.e3mall.common.utils.JsonUtils;
import com.yapengren.e3mall.service.ItemService;
import com.yapengren.e3mall.mapper.TbItemDescMapper;
import com.yapengren.e3mall.mapper.TbItemMapper;
import com.yapengren.e3mall.pojo.TbItem;
import com.yapengren.e3mall.pojo.TbItemDesc;
import com.yapengren.e3mall.pojo.TbItemExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
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

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination topicDestionation;

    @Autowired
    private JedisClient jedisClient;

    @Value("${item.cache.expire}")
    private Integer itemCacheExpire;

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

    /**
     * 添加商品
     */
    @Override
    public E3Result addItem(TbItem item, String desc) {
        // 生成商品 id
        final long itemId = IDUtils.genItemId();
        // 把 TbItem 对象的属性补充完整
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 向商品表插入数据
        tbItemMapper.insert(item);
        // 创建一个 TbItemDesc 对象
        TbItemDesc itemDesc = new TbItemDesc();
        // 补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        // 向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 发送消息
                jmsTemplate.send(topicDestionation, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(itemId + "");
                    }
                });
            }
        }).start();
        // 返回成功
        return E3Result.ok();
    }

    /**
     * 查询商品描述通过 id
     *
     * @param itemId
     * @return
     */
    @Override
    public TbItemDesc getItemDescById(long itemId) {
        try {
            // 查询缓存
            String json = jedisClient.get("item_info:" + itemId + ":desc");
            // 如果缓存中有数据，直接返回
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        try {
            // 把结果添加到缓存
            jedisClient.set("item_info:" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
            // 设置缓存的过期时间，可以动态调节
            jedisClient.expire("item_info:" + itemId + ":desc", itemCacheExpire);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbItemDesc;
    }
}
