package com.yapengren.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author renyapeng
 */
public class ActivemqTest {

    @Test
    public void reciveMessage() throws IOException {
        // 初始化 Spring 容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 阻塞系统，等待接收消息
        System.out.println("系统已经启动成功");
        System.in.read();
    }
}
