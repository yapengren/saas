package com.yapengren.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author renyapeng
 */
public class ActivemqTest {

    /**
     * queue 生产者
     *
     * @throws JMSException
     */
    @Test
    public void queueProducer() throws JMSException {
        // 创建一个 ConnectionFactory 对象，使用 ActivemqConnectionFactory 类创建对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // 使用 ConnectionFactory 创建一个 Connection 对象
        Connection connection = connectionFactory.createConnection();
        // 开启连接，调用 Connection 的 start 方法
        connection.start();
        // 使用 Connection 对象创建一个 Session 对象
        // 第一个参数：是否开启参数，一般不开启事务
        // 第二个参数：如果第一个参数是 true，第二个参数无意义。如果不开启事务，消息的应答模式。自动应答和手动应答。一般使用自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 使用 Session 创建一个 Destnation，两种形式 queue、topic，应该创建一个 Queue 对象
        Queue queue = session.createQueue("test-queue");
        // 使用 Session 对象创建一个消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 创建一个 TextMessage 对象
        TextMessage textMessage = session.createTextMessage("hello activemq");
        // 发送消息
        producer.send(textMessage);
        // 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * queue 消费者
     */
    @Test
    public void queueConsumer() throws Exception {
        // 创建一个 ConnectionFactory 对象，使用 ActivemqConnectionFactory 类创建对象
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // 使用 ConnectionFactory 创建一个 Connection 对象
        Connection connection = connectionFactory.createConnection();
        // 开启连接，调用 Connection 的 start 方法
        connection.start();
        // 使用 Connection 对象创建一个 Session 对象
        // 第一个参数：是否开启参数，一般不开启事务
        // 第二个参数：如果第一个参数是 true，第二个参数无意义。如果不开启事务，消息的应答模式。自动应答和手动应答。一般使用自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 使用 Session 创建一个 Detnation，两种形式 queue、topic，应该创建一个 Queue 对象
        Queue queue = session.createQueue("test-queue");
        // 使用 Session 对象创建一个消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 设置消息的监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 在监听器中接收消息并打印
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(">>>系统已经启动<<<");

        // 系统需要阻塞
        System.in.read();

        // 系统关闭，关闭资源
        System.out.println(">>>系统关闭<<<");
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * topic 生产者
     */
    @Test
    public void topicProducer() throws JMSException {
        // 创建一个 ConnectionFactory 对象
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // 使用 ConnectionFactory 创建一个 Connection 对象
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 使用 Connection 对象创建一个 Session 对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 使用 Session 创建一个 Destnation 对象，应该创建一个 Topic 对象
        Topic topic = session.createTopic("test-topic");
        // 创建一个消息的生产者对象
        MessageProducer producer = session.createProducer(topic);
        // 创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello activemq topic");
        // 发送消息
        producer.send(textMessage);
        // 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * topic 消费者
     */
    @Test
    public void topicConsumer() throws JMSException, IOException {
        // 创建一个 ConnectionFactory 对象
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // 使用 ConnectionFactory 创建一个 Connection 对象
        Connection connection = connectionFactory.createConnection();
        // 设置一个 clientId，持久化订阅
        // connection.setClientID("client1");
        // 开启连接
        connection.start();
        // 使用 Connection 对象创建一个 Session 对象
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 使用 Session 创建一个 Destnation 对象，应该创建一个 Topic 对象
        Topic topic = session.createTopic("test-topic");
        // 创建一个消息的消费者对象
        // MessageConsumer consumer = session.createConsumer(topic);
        // 创建一个持久化的订阅者
        TopicSubscriber consumer = session.createDurableSubscriber(topic, "subscriber1");
        // 设置消息的监听器对象
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 在监听器中接收消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    // 手动应答服务器
                    message.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("消费者已经启动");
        // 系统阻塞，等待发送消息
        System.in.read();
        // 系统结束关闭资源
        consumer.close();
        session.close();
        connection.close();
        System.out.println("消费者已经关闭");
    }

    /**
     * 死信队列的消费者
     */
    @Test
    public void dlQueueConsumer() throws Exception {
        // 1、创建一个ConnectionFactory对象。使用ActivemqConnectionFactory类创建对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // 2、使用ConnectionFactory创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        // 3、开启连接，调用Connection的start方法
        connection.start();
        // 4、使用Connection对象创建一个Session对象
        //第一个参数：是否开启事务，一般不开启事务。
        //第二个参数：如果第一个参数是true，第二个参数无意义。如果不开启事务，消息的应答模式。自动应答和手动应答。一般使用自动应答。
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 5、使用Session创建一个Destnation，两种形式queue、topic，应该创建一个Queue对象。
        Queue queue = session.createQueue("ActiveMQ.DLQ");
        // 6、使用Session对象创建一个消息的消费者。
        MessageConsumer consumer = session.createConsumer(queue);
        // 7、设置消息的监听器。
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 8、在监听器中接收消息并打印
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    //int i = 1/0;
                    //手动应答服务器
                    message.acknowledge();
                } catch (Exception e) {
                    e.printStackTrace();
                    //请求重发消息
                    try {
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });
        System.out.println("系统已经启动。。。。");
        // 9、系统需要阻塞。
        System.in.read();
        // 10、系统关闭，关闭资源
        System.out.println("系统关闭。。。。");
        consumer.close();
        session.close();
        connection.close();

    }
}
