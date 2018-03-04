package com.yapengren.e3mall.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yapengren.e3mall.mapper.TbItemMapper;
import com.yapengren.e3mall.pojo.TbItem;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PageHelperTest {

    @Test
    public void testPageHeader() {
        // 初始化 spring 容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        // 从容器中获得 Mapper 代理对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        // 执行 sql 语句之前设置分页信息使用 PageHeader 的startPage 方法
        PageHelper.startPage(1, 10);
        // 执行查询
        List<TbItem> list = itemMapper.selectAll();
        // 取分页信息，pageInfo
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>();
        System.out.println(tbItemPageInfo.getTotal());
        System.out.println(tbItemPageInfo.getPages());
        System.out.println(list.size());
    }
}
