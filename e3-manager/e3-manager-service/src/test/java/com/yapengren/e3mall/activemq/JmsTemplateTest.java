package com.yapengren.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author renyapeng
 */
public class JmsTemplateTest {

    /**
     * spring 整合 ActiveMQ 生产者
     * @throws Exception
     */
    @Test
    public void testSendMessage() throws Exception {
        // 初始化 Spring 容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // 从容器中获得 JmsTemplate 对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 从容器中获得一个 Destnation 对象
        Destination destination = (Destination) applicationContext.getBean("queueDestination");
        // 使用 JmsTemplate 发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello spring activemq");
            }
        });
    }
}
