package kafka;

import kafka.consumer.ConsumerIterator;  
import kafka.consumer.KafkaStream;  
  
public class ConsumerDemoRun implements Runnable {  
  private KafkaStream aStream;  
  private int aThread;  
  
  public ConsumerDemoRun(KafkaStream stream, int thread) {  
    aStream = stream; // set stream from main read  
    aThread = thread; // set thread from main read  
  }  
  
  public void run() {  
  
    ConsumerIterator<byte[], byte[]> iterator = aStream.iterator(); // used to  
                                                                    // check  
                                                                    // throughout  
                                                                    // the list  
                                                                    // continiously  
  
    while (iterator.hasNext()) {  
      System.out.println("Thread " + aThread + ": " + new String(iterator.next().message()));  
    }  
    System.out.println("Shutting down Thread: " + aThread);  
  
  }  
} 
