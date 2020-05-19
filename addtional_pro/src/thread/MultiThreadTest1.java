package thread;

class Res{
	String name;
	String sex;
	boolean flag = false;
}
class Input extends Thread{
	private Res r;
	Input (Res r){
		this.r = r;
	}
	public void run(){
		int i=0;
		while (true) {
			synchronized (r) {
				if (r.flag){
					try {
						r.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(i%2==0){
					r.name="andy";
					r.sex="man";
				} else {
					r.name="Íõ·Æ";
					r.sex="Å®";
				} 
				i++;
				r.flag = true;
				r.notifyAll();
			}
		}
	}
}
class Output extends Thread{
	private Res r;
	Output (Res r){
		this.r = r;
	}
	public void run(){
		while(true){
			if (!r.flag){
				try {
					r.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			synchronized (r) {
				System.out.println(r.name+"---------"+r.sex);
			}
			r.flag = false;
			r.notifyAll();
		}
	}
}
public class MultiThreadTest1 {
	public static void main(String[] args) {
		Res r = new Res();
		
		Input in = new Input(r);
		Output out = new Output(r);
		Thread t1 = new Thread(in);
		Thread t2 = new Thread(out);
		//Thread t3 = new Thread(in);
		//Thread t4 = new Thread(out);
		t1.start();
		t2.start();
		//t3.start();
		//t4.start();
	}
}
