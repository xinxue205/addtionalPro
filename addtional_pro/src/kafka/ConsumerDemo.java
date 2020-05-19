package kafka;

import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Properties;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;  
  
import kafka.consumer.ConsumerConfig;  
import kafka.consumer.KafkaStream;  
import kafka.javaapi.consumer.ConsumerConnector;  
  
public class ConsumerDemo {  
  
  private final ConsumerConnector consumer;  
  private final String topic;  
  private ExecutorService executor;  
  
  public ConsumerDemo(String zookeeper, String groupid, String aTopic) {  
    consumer = kafka.consumer.Consumer.createJavaConsumerConnector(ConsumerProps(zookeeper, groupid));  
    this.topic = aTopic;  
  }  
  
  public void run(int threads) {  
    Map<String, Integer> topicMap = new HashMap<String, Integer>();  
    topicMap.put(topic, new Integer(threads));  
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicMap);  
    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);  
  
    executor = Executors.newFixedThreadPool(threads);  
  
    int numThread = 0;  
  
    for (final KafkaStream stream : streams) {  
      executor.submit(new ConsumerDemoRun(stream, numThread));  
      numThread++;  
    }  
  }  
  
  private static ConsumerConfig ConsumerProps(String zookeeper, String groupid) {  
  
    Properties properties = new Properties(); // config properties file  
  
    properties.put("zookeeper.connect", zookeeper);  
    properties.put("group.id", groupid);  
    properties.put("zookeeper.session.timeout.ms", "400");  
    properties.put("zookeeper.sync.time.ms", "200");  
    properties.put("auto.commit.interval.ms", "1000");  
    properties.put("auto.offset.reset", "smallest");  
//    properties.put("auto.offset.reset", "earliest");
  
    return new ConsumerConfig(properties);  
  }  
  
  public void shutdown() {  
    if (consumer != null)  
      consumer.shutdown();  
    if (executor != null)  
      executor.shutdown();  
  
    try {  
      if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {  
        System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");  
      }  
    } catch (InterruptedException e) {  
      System.out.println("Interrupted during shutdown, exiting uncleanly");  
    }  
  }  
  
  public static void main(String[] args) {  
    String zookeeper = "192.168.14.84:2181";  
    String groupid = "group1";  
    String topic = "hellotest";  
    int threads = 4;  
  
    ConsumerDemo test = new ConsumerDemo(zookeeper, groupid, topic);  
    test.run(threads);  
  
    try {  
      Thread.sleep(10000);  
    } catch (InterruptedException ie) {  
    }  
  
    test.shutdown();  
  }  
}
