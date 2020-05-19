import java.util.Timer;
import java.util.TimerTask;



public class TestFinal1 {
	public static void main(String[] args) throws Exception {
		Test4 t1 = new Test4();
		Demo d1 = new Demo(1,"name1");
		t1.addTask(d1);
		Thread.sleep(5000);
		d1 = new Demo(2,"name2");
		t1.addTask(d1);
	}
}

class Test4{

	/**
	 * @param string
	 */
	public void addTask(final Demo d) {
		long period = 3000;
	    long delay = 1000;
	    Timer timer = new Timer();
	    
	    switch (d.id) {
	    case 1:
	    	final String trans = d.name+1;
		    timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(trans);
				}
			} , delay, period);
//	      task.setTaskEntity(trans);
	    	break;
	    case 2:
	    	final String trans1 = d.name+2;
		    timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(trans1);
				}
			} , delay, period);
//	      task.setTaskEntity(trans);
	    	break;
		}
		
	}
}

class Demo{
	int id;
	String name;
	
	Demo(int id, String name){
		this.id = id;
		this.name = name;
	}
}
