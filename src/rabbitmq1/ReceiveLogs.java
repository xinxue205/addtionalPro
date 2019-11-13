package rabbitmq1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import rabbitmq.Producer;

public class ReceiveLogs {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitmq.Producer.HOST);
        connectionFactory.setUsername(rabbitmq.Producer.USERNAME);
        connectionFactory.setPassword(rabbitmq.Producer.PASSWORD);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        
      //3.��������    ������ģʽ�¶��������Ͱ󶨶����Զ������У�ֻҪ������������ǰ��ɣ����ѵ�ʱ��ֱ��ȡ����
//        channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);
        //4.�󶨶��е�������,ָ��·��keyΪupdate
//        channel.queueBind(Producer.QUEUE_NAME, EmitLog.EXCHANGE_NAME, EmitLog.ROUTE_KEY);
//        channel.queueBind(Producer.QUEUE_NAME, EmitLog.EXCHANGE_NAME, "delete");
//        channel.queueBind(Producer.QUEUE_NAME, EmitLog.EXCHANGE_NAME, "add");
        //ͬһʱ�̷�����ֻ�ᷢ��һ����Ϣ��������
        channel.basicQos(1);

        //5.������е�������
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //5.��������,�ֶ��������״̬
        channel.basicConsume(Producer.QUEUE_NAME, false, queueingConsumer);
        //6.��ȡ��Ϣ
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[������1] received message : '" + message + "'");
            //����10����
            Thread.sleep(10);
            //����ȷ��״̬
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
//        channel.exchangeDeclare(EmitLog.EXCHANGE_NAME, EmitLog.EXCHANGE_TYPE);
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind(queueName, EmitLog.EXCHANGE_NAME, EmitLog.EXCHANGE_TYPE);
//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
//                    byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println(" [x] Received '" + message + "'");
//            }
//        };
//        channel.basicConsume(queueName, true, consumer);
    }
}