package queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import util.ActiveMQUtil;

import javax.jms.*;

public class TestProducer {
    //服务地址，端口默认61616
    private static final String url = "tcp://127.0.0.1:61616";
    //这次发送的消息名称
    private static final String topicName = "queue_style";

    public static void main(String [] args) throws JMSException {
        //0. 先判断端口是否启动了  Active MQ 服务器
        ActiveMQUtil.checkServer();
        //1.创建ConnectionFactory,绑定地址
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        //2.创建Connection
        Connection connection = factory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标 (队列类型)
        Destination destination = session.createQueue(topicName);
        //6.创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        for(int i = 0; i < 100; i++){
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("message queue: " + i);
            //8.发送消息
            producer.send(textMessage);

            System.out.println("send: " + textMessage.getText());
        }
        //7. 关闭连接
        connection.close();
    }
}
