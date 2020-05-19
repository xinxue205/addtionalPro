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
        
      //3.声明队列    交换器模式下队列声明和绑定都可以独立进行，只要在消费者连接前完成，消费的时候直接取就行
//        channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);
        //4.绑定队列到交换器,指定路由key为update
//        channel.queueBind(Producer.QUEUE_NAME, EmitLog.EXCHANGE_NAME, EmitLog.ROUTE_KEY);
//        channel.queueBind(Producer.QUEUE_NAME, EmitLog.EXCHANGE_NAME, "delete");
//        channel.queueBind(Producer.QUEUE_NAME, EmitLog.EXCHANGE_NAME, "add");
        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);

        //5.定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //5.监听队列,手动返回完成状态
        channel.basicConsume(Producer.QUEUE_NAME, false, queueingConsumer);
        //6.获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[消费者1] received message : '" + message + "'");
            //休眠10毫秒
            Thread.sleep(10);
            //返回确认状态
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