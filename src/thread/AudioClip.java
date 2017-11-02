/**
 * 
 */
package thread;

/**
 * @author Administrator
 * @date 2014-11-28 上午11:18:01
 * @version 1.0  Administrator create
 * @CopyRight (c) 2014 xxxx公司 
 */
public class AudioClip { 
	private Thread thread = null; 
	public synchronized void load(String url) {  
		if (thread != null) {   
			throw new IllegalStateException();  
		}  
		thread = new AudioClipLoader(url);  
		thread.start(); 
	} 
	
	public void shutdown() {  
		Thread snapshot;  
		synchronized (this) {   
			snapshot = thread;   
			thread = null;   
			this.notifyAll();  
		}  
		if (snapshot != null) {   
			snapshot.interrupt();  
			System.out.println("结束进程");
		}
	} 
	
	private class AudioClipLoader extends Thread {  
		private String url;  
		public AudioClipLoader(String url) {   
			this.url = url;  
		}  
		
		@Override  
		public void run() {   
			while (true) {    
				Thread me = Thread.currentThread();    
				if (me != thread) {     break;    }    
				System.out.println("正在加载" + url + " ...");    
				
				try {     
					Thread.sleep(90 * 1000 * 60);    
				} catch (Exception ex) {     
					/*synchronized (AudioClip.this) {
						me = Thread.currentThread();      
						if (me != thread) {// close by AudioClip       
							System.out.println("Shutdown by AudioClip!");       
							break;      
						}      // network connection exception      
						throw new RuntimeException(ex);     
					}*/    
				}   
			}  
		} 
	} 
	
	public static void main(String[] args) throws Exception {  
		AudioClip audioClip = new AudioClip();  
		audioClip.load("http://java.sun.com/");  
		Thread.sleep(3000);  
		audioClip.shutdown(); 
	}
}
