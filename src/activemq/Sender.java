package activemq;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;


/**
 * @author Administrator
 * @date 2010-11-6 ����6:31:50
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */

public class Sender {
    private static final int SEND_NUMBER = 5;

    public static void main(String[] args) {
        // ConnectionFactory �����ӹ�����JMS ������������
        ConnectionFactory connectionFactory;
        // Connection ��JMS �ͻ��˵�JMS Provider ������
        Connection connection = null;
        // Session�� һ�����ͻ������Ϣ���߳�
        Session session;
        // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
        Destination destination;
        // MessageProducer����Ϣ������
        MessageProducer producer;
        // TextMessage message;
        // ����ConnectionFactoryʵ�����󣬴˴�����ActiveMq��ʵ��jar
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
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destination = session.createQueue("FirstQueue");
            // �õ���Ϣ�����ߡ������ߡ�
            producer = session.createProducer(destination);
            // ���ò��־û����˴�ѧϰ��ʵ�ʸ�����Ŀ����
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // ������Ϣ���˴�д������Ŀ���ǲ��������߷�����ȡ
            sendMessage(session, producer);
            session.commit();
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

    public static void sendMessage(Session session, MessageProducer producer) throws Exception {
            BytesMessage message = session.createBytesMessage();
            producer.send(message);
    }
}