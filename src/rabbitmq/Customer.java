package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//https://www.cnblogs.com/Alva-mu/p/9535396.html
public class Customer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //�������ӹ���
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitmq.Producer.HOST);
        connectionFactory.setUsername(rabbitmq.Producer.USERNAME);
        connectionFactory.setPassword(rabbitmq.Producer.PASSWORD);
        //����һ������
        Connection connection = connectionFactory.newConnection();
        //����һ��ͨ��
        Channel channel = connection.createChannel();
        //��������
        //queueDeclare��һ��������ʾ�������ơ�
        // �ڶ�������Ϊ�Ƿ�־û���true��ʾ�ǣ����н��ڷ���������ʱ���棩��
        // ����������Ϊ�Ƿ��Ƕ�ռ���У������߿���ʹ�õ�˽�ж��У��Ͽ����Զ�ɾ������
        // ���ĸ�����Ϊ�����������߿ͻ������ӶϿ�ʱ�Ƿ��Զ�ɾ�����С����������Ϊ���е���������
        channel.queueDeclare(Producer.QUEUE_NAME,false,false,false,null);
        System.out.println("Customer Waiting Received messages");
        Consumer consumer = new DefaultConsumer(channel){
            /**
             * envelope��Ҫ��������������Ϣ�����罻������·��key�ȣ�body����Ϣʵ�塣
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //�Զ��ظ�����Ӧ�� -- RabbitMQ�е���Ϣȷ�ϻ���
        channel.basicConsume(Producer.QUEUE_NAME,true, consumer);
    }
}