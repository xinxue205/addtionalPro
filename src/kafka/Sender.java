package kafka;


import java.util.Properties;  

import kafka.javaapi.producer.Producer;  
import kafka.producer.KeyedMessage;  
import kafka.producer.ProducerConfig;  
  
public class Sender {  
  
  public static void main(String[] args) {  
    Properties prop = new Properties();  
    prop.put("metadata.broker.list", "192.168.14.84:9092");  
    prop.put("serializer.class", "kafka.serializer.StringEncoder");  
    ProducerConfig producerConfig = new ProducerConfig(prop);  
    Producer<String, String> producer = new Producer<String, String>(producerConfig);  
    String topic = "hellotest";  
    KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, "str1");  
    producer.send(message);  
    producer.close();  
  }  
}
