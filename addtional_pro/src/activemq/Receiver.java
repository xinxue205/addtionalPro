package activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author Administrator
 * @date 2010-11-6 ����6:35:11
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */


public class Receiver {
    public static void main(String[] args) {
        // ConnectionFactory �����ӹ�����JMS ������������
        ConnectionFactory connectionFactory;
        // Connection ��JMS �ͻ��˵�JMS Provider ������
        Connection connection = null;
        // Session�� һ�����ͻ������Ϣ���߳�
        Session session;
        // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
        Destination destination;
        // �����ߣ���Ϣ������
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
            // ����ӹ����õ����Ӷ���
            connection = connectionFactory.createConnection();
            // ����
            connection.start();
            // ��ȡ��������
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);
            while (true) {
                //���ý����߽�����Ϣ��ʱ�䣬Ϊ�˱��ڲ��ԣ�����˭��Ϊ100s
                TextMessage message = (TextMessage) consumer.receive(100000);
                if (null != message) {
                    System.out.println("�յ���Ϣ" + message.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
}