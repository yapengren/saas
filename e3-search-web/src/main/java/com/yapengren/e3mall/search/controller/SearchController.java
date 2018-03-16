package com.yapengren.e3mall.search.controller;

import com.yapengren.e3mall.search.pojo.SearchResult;
import com.yapengren.e3mall.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 索引查询 Controller
 *
 * @author renyapeng
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${search.result.rows}")
    private Integer searchRows;

    @RequestMapping(value = "/search")
    public String search(String keyword, @RequestParam(defaultValue = "1") int page, Model model) throws Exception {
        // 解决乱码问题
        if (StringUtils.isNotBlank(keyword)) {
            String string = new String(keyword.getBytes("iso8859-1"), "utf-8");
            keyword = string;
        }
        // 执行查询
        SearchResult searchResult = searchService.search(keyword, page, searchRows);
        // 查询条件回显
        model.addAttribute("query", keyword);
        model.addAttribute("page", page);
        // 把查询结果传递给 jsp
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("recourdCount", searchResult.getRecourdCount());
        model.addAttribute("itemList", searchResult.getItemList());
        // 返回逻辑视图
        return "search";
    }
}
