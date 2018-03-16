package com.yapengren.e3mall.search.service.impl;

import com.yapengren.e3mall.common.pojo.E3Result;
import com.yapengren.e3mall.search.mapper.ItemMapper;
import com.yapengren.e3mall.search.pojo.Item;
import com.yapengren.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 索引库维护 Service
 *
 * @author renyapeng
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    /**
     * 把商品数据导入到索引库中
     *
     * @return
     */
    @Override
    public E3Result importAllItems() {
        try {
            // 查询全部商品数据
            List<Item> itemList = itemMapper.getItemList();
            // 遍历商品列表
            for (Item item : itemList) {
                // 创建一个文档对象
                SolrInputDocument document = new SolrInputDocument();
                // 向文档对象中添加域
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                // 把文档对象写入到索引库
                solrServer.add(document);
            }
            // 提交
            solrServer.commit();
            // 返回成功
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, e.getMessage());
        }
    }
}
