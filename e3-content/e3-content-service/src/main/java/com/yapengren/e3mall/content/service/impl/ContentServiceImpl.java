package com.yapengren.e3mall.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yapengren.e3mall.common.jedis.JedisClient;
import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.common.pojo.EasyUIDataGridResult;
import com.yapengren.e3mall.common.utils.JsonUtils;
import com.yapengren.e3mall.content.service.ContentService;
import com.yapengren.e3mall.mapper.TbContentMapper;
import com.yapengren.e3mall.pojo.TbContent;
import com.yapengren.e3mall.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.AbstractDocument;
import java.util.Date;
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

    @Autowired
    private JedisClient jedisClient;

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

    /**
     * 根据分类 id 查询内容列表
     */
    @Override
    public List<TbContent> getContentList(long cid) {
        /*============查询缓存 start ==============*/
        // 如果有结果直接返回
        try {
            String json = jedisClient.hget("content-info", cid + "");
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*============查询缓存 end ==============*/

        // 创建一个查询条件，设置查询条件，根据内容分类 id 查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        // 执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);

        /*============向缓存中添加数据 start ==============*/
        try {
            jedisClient.hset("content-info", cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*============向缓存中添加数据 end ==============*/

        // 返回查询结果
        return list;
    }

    /**
     * 新增内容
     */
    @Override
    public E3Result addContent(TbContent tbContent) {
        // 补全 pojo 对象的属性
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        // 插入数据
        tbContentMapper.insert(tbContent);
        // 缓存同步
        jedisClient.hdel("content-info", tbContent.getCategoryId().toString());
        // 返回成功呢
        return E3Result.ok();
    }
}
