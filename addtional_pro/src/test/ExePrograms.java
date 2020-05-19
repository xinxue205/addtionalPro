package test;
/*
 * 一键启动单个、多个程序
 */

public class ExePrograms {
	public static void main(String[] args) {
		System.out.println("一键启动程序(RTX,飞秋，金山词霸，Eclipse)");
		
		new Program("RTX",1000).start();
		//new Program("RTX",1000).start(); //启动rtx
		//new Program("feiq2",1000).start();	//启动飞秋
		//new Program("XDict",1000).start();	//启动金山词霸
		//new Program("eclipse",1000).start();	//启动eclipse
	}
}

class Program extends Thread{
	long time;
	String name;
	public Program(String name, long time) {
		this.time=time;
		this.name=name;
	}
	
	public void run() {
		try {
			sleep(time);
			Runtime.getRuntime().exec(name); //启动程序;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
