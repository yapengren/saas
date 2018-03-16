package com.yapengren.e3mall.search.dao.impl;

import com.yapengren.e3mall.search.dao.SearchDao;
import com.yapengren.e3mall.search.pojo.Item;
import com.yapengren.e3mall.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 索引查询 Dao
 *
 * @author renyapeng
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        // 执行查询
        QueryResponse queryResponse = solrServer.query(query);
        // 取查询结果总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        long numFound = solrDocumentList.getNumFound();
        // 取商品列表，应该取高亮结果
        List<Item> itemList = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            Item item = new Item();
            item.setId((String) solrDocument.get("id"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            // 取高亮
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            // 添加到商品列表
            itemList.add(item);
        }
        // 把结果封装到 SearchResult 对象中
        SearchResult result = new SearchResult();
        result.setItemList(itemList);
        result.setRecourdCount(numFound);
        // 返回 SearchResult 对象
        return result;
    }
}
