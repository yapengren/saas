package com.yapengren.e3mall.search.service.impl;

import com.yapengren.e3mall.search.dao.SearchDao;
import com.yapengren.e3mall.search.pojo.SearchResult;
import com.yapengren.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 索引查询 Service
 *
 * @author renyapeng
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws Exception {
        // 创建一个 SolrQuery 对象
        SolrQuery query = new SolrQuery();
        // 根据参数创建查询条件
        query.set("q", keyword);
        query.set("start", (page - 1) * rows);
        query.set("rows", rows);
        query.set("df", "item_keywords");
        query.set("hl", true);
        query.set("hl.fl", "item_title");
        query.set("hl.simple.pre", "<em style='color:red>'>");
        query.set("hl.simple.post", "</em>");
        // 调用 Dao 执行查询，得到查询结果
        SearchResult searchResult = searchDao.search(query);
        // 需要根据总记录数计算查询结果的总页数
        long recourdCount = searchResult.getRecourdCount();
        long pageCount = recourdCount / rows;
        if (recourdCount % rows > 0) {
            pageCount++;
        }
        searchResult.setTotalPages(pageCount);
        // 返回 SearchResult
        return searchResult;
    }
}
