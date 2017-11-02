/**
 * 
 */
package synch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxinxue
 * @time 2015-7-13 下午3:47:25
 * @copyright hnisi
 */
public class Producer {
	public static List<String> queue = new ArrayList<String>();
	
	public static void main(String[] args) throws InterruptedException {
		
		new Thread(){
		    public void run(){
		    	for (int i = 0; i < 500; i++) {
		    		if(Producer.queue.size()>0){
//		    			synchronized (queue) {
		    				Producer.queue.remove(0);
		    				System.out.println("消费一次"+Producer.queue);
//						}
		    		}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    }
		}.start();
		
		QueueProducer qp = new QueueProducer(queue);
		for (int i = 0; i < 100; i++) {
			qp.add(""+i);
		}
	}
}

class QueueProducer{
	List<String> queue;
	QueueProducer(List<String> queue){
		this.queue = queue;
	}
	void add(String str) throws InterruptedException {
		synchronized (queue) {
			queue.add(str);
			queue.add(str);
			System.out.println("生产一次"+Producer.queue);
			Thread.sleep(5000);
		}
	}
}
