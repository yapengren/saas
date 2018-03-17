package com.yapengren.e3mall.search.listener;

import com.yapengren.e3mall.search.mapper.ItemMapper;
import com.yapengren.e3mall.search.pojo.Item;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 商品添加消息监听器
 *
 * @author renyapeng
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            // 接收消息
            TextMessage textMessage = (TextMessage) message;
            // 从消息中取商品 id
            String text = textMessage.getText();
            long itemId = Long.parseLong(text);
            // 根据商品 id 查询商品信息(在 mapper 中添加一个根据商品 id 查询商品信息的方法)
            Item item = itemMapper.getItemById(itemId);
            // 创建一个文档对象
            SolrInputDocument document = new SolrInputDocument();
            // 向文档对象中添加域
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            // 把文档对象写入索引库
            solrServer.add(document);
            // 提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 写日志
        }
    }
}
