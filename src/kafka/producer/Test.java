package kafka.producer;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;

/**
 * ��ϸ���Բο���https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+Producer+Example
 * @author Fung
 *
 */
public class Test {
	public static void main(String[] args) {
		Random rnd = new Random();
		int events=5;

		// ������������
		Properties props = new Properties();
		props.put("metadata.broker.list","localhost:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// key.serializer.classĬ��Ϊserializer.class
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// ��ѡ���ã���������ã���ʹ��Ĭ�ϵ�partitioner
//		props.put("partitioner.class", "com.catt.kafka.demo.PartitionerDemo");
		// ����acknowledgement���ƣ�������fire and forget�����ܻ��������ݶ�ʧ
		// ֵΪ0,1,-1,���Բο�
		// http://kafka.apache.org/08/configuration.html
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);

		// ����producer
		Producer<String, String> producer = new Producer<String, String>(config);
		// ������������Ϣ
		long start=System.currentTimeMillis();
		for (long i = 0; i < events; i++) {
			long runtime = new Date().getTime();
			String ip = "192.168.2." + i;//rnd.nextInt(255);
			String msg = runtime + ",www.example.com," + ip;
			//���topic�����ڣ�����Զ�������Ĭ��replication-factorΪ1��partitionsΪ0
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(
					"page_visits", ip, msg);
			producer.send(data);
		}
		System.out.println("��ʱ:" + (System.currentTimeMillis() - start));
		// �ر�producer
		producer.close();
	}
}
