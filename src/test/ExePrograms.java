package test;
/*
 * һ�������������������
 */

public class ExePrograms {
	public static void main(String[] args) {
		System.out.println("һ����������(RTX,�����ɽ�ʰԣ�Eclipse)");
		
		new Program("RTX",1000).start();
		//new Program("RTX",1000).start(); //����rtx
		//new Program("feiq2",1000).start();	//��������
		//new Program("XDict",1000).start();	//������ɽ�ʰ�
		//new Program("eclipse",1000).start();	//����eclipse
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
			Runtime.getRuntime().exec(name); //��������;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
