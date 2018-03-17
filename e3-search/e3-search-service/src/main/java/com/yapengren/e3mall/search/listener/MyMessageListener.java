package com.yapengren.e3mall.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息的接收者
 *
 * @author renyapeng
 */
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // 接收消息
        TextMessage textMessage = (TextMessage) message;
        // 打印消息内容
        try {
            System.out.println(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
