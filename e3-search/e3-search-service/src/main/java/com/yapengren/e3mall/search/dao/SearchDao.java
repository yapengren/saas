package com.yapengren.e3mall.search.dao;

import com.yapengren.e3mall.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * 索引查询 Interface
 *
 * @author renyapeng
 */
public interface SearchDao {

    SearchResult search(SolrQuery query) throws Exception;

}
