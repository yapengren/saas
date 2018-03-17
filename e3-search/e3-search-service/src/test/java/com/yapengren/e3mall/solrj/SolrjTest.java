package com.yapengren.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * @author renyapeng
 */
public class SolrjTest {

    /**
     * 测试 索引库增加
     */
    @Test
    public void addDocument() throws IOException, SolrServerException {
        // 创建一个 SolrServer 对象，连接单机版应该使用 HttpSolrServer 类
        HttpSolrServer solrServer = new HttpSolrServer("http://solrserver:8983/solr/collection2");
        // 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 向文档对象中添加 filed，每个域必须先定义后使用，并且每个文档中必须有 id
        document.addField("id", "test01");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 1000);
        // 把文档对象写入索引库
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }

    /**
     * 测试 索引库删除
     */
    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        // 创建一个 SolrServer 对象
        HttpSolrServer solrServer = new HttpSolrServer("http://solrserver:8983/solr/collection2");
        solrServer.deleteByQuery("*:*");
        // 提交
        solrServer.commit();
    }

    /**
     * 测试 查询索引库
     */
    @Test
    public void queryDocument() throws SolrServerException {
        // 创建一个 SolrServer 对象
        HttpSolrServer solrServer = new HttpSolrServer("http://solrserver:8983/solr/collection2");
        // 创建一个 solrQuery 对象
        SolrQuery query = new SolrQuery();
        // 向 solrQuery 中添加查询条件
        query.set("q", "*:*");
        // 执行查询，得到一个 Response 对象
        QueryResponse response = solrServer.query(query);
        // 取查询结果
        SolrDocumentList results = response.getResults();
        // 查询结果总记录数
        System.out.println("查询结果的总记录数：" + results.getNumFound());

        // 遍历结果打印
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            System.out.println(result.get("item_title"));
            System.out.println(result.get("item_price"));
        }

        results.forEach(entries -> entries.get("id"));
    }
}
