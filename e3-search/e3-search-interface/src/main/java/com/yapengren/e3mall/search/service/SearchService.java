package com.yapengren.e3mall.search.service;

import com.yapengren.e3mall.search.pojo.SearchResult;

/**
 * 索引查询 Service
 *
 * @author renyapeng
 */
public interface SearchService {

    SearchResult search(String keyword, int page, int rows) throws Exception;

}
