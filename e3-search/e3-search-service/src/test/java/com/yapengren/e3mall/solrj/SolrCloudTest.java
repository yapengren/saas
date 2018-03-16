package com.yapengren.e3mall.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solr 集群
 *
 * @author renyapeng
 */
public class SolrCloudTest {

    /**
     * solr 集群版添加索引
     *
     * @throws Exception
     */
    @Test
    public void testCloudSolrServer() throws Exception {
        // 创建一个 CloudSolrServer 对象，构造参数 zkHost，字符串就是 zookeeper 的地址列表
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2181, 192.168.25.128:2182");
        // 设置一个属性 defaultCollection，如果不设置进行时报错
        solrServer.setDefaultCollection("collection2");
        // 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 向文档对象中添加域，必须有 id 域，每个域必须先定义后使用
        document.addField("id", "test01");
        document.addField("item_title", "测试001");
        document.addField("item_price", "1000");
        // 把文档对象添加到索引库
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }
}
