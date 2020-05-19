package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//https://www.cnblogs.com/Alva-mu/p/9535396.html
public class Producer {
    public static final String HOST = "192.168.11.78";
    public static final String PASSWORD = "sdi@123";
    public static final String USERNAME = "sdi";
	
	public static final String QUEUE_NAME ="queue.test1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //�������ӹ���
        ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(HOST);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        //����һ������
        Connection connection = connectionFactory.newConnection();
        //����һ��ͨ��
        Channel channel = connection.createChannel();
        //��������
        //queueDeclare��һ��������ʾ�������ơ�
        // �ڶ�������Ϊ�Ƿ�־û���true��ʾ�ǣ����н��ڷ���������ʱ���棩��
        // ����������Ϊ�Ƿ��Ƕ�ռ���У������߿���ʹ�õ�˽�ж��У��Ͽ����Զ�ɾ������
        // ���ĸ�����Ϊ�����������߿ͻ������ӶϿ�ʱ�Ƿ��Զ�ɾ�����С����������Ϊ���е���������
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello rabbit";
        //������Ϣ������
        //basicPublish��һ������Ϊ���������ơ�
        // �ڶ�������Ϊ����ӳ���·��key��
        // ����������Ϊ��Ϣ���������ԡ�
        // ���ĸ�����Ϊ������Ϣ������
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes("UTF-8"));
        System.out.println("Producer Send +'" + msg + "'");
        channel.close();
        connection.close();
    }
}