package multi.state;
//���ر����ǲ�����ͬ����������ֵ��ͬ��������ͬ��ͬ��������

public class TestOverLoader {
	
	//public  method(String name){ return "Hello, "+name; } //����
	
	public String method(String name){
		return "Hello, "+name;
	}
	
	public String method(String time, String name){
		return time+", "+name;
	}
}
