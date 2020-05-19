package rabbitmq1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabbitmq.Producer;

public class EmitLog {
	public static final boolean DURABLE = false;
	public static final String ROUTE_KEY = "ROUTE_KEY";
	public static final String EXCHANGE_TYPE = "fanout";
	public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Producer.HOST);
        connectionFactory.setUsername(Producer.USERNAME);
        connectionFactory.setPassword(Producer.PASSWORD);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, DURABLE);
        for (int i = 100; i < 200; i++) {
        	String message = getMessage(i);
        	channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, null, message.getBytes("UTF-8"));
        	System.out.println(" [x] Sent '" + message + "'");
		}
        channel.close();
        connection.close();
    }

    private static String getMessage(int i) {
//        if (strings.length < 1)
            return "info: Hello World - "+i;
//        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0)
            return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}