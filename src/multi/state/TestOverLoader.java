package multi.state;
//重载必须是参数不同，不允许返回值不同、参数相同的同名方法；

public class TestOverLoader {
	
	//public  method(String name){ return "Hello, "+name; } //错误
	
	public String method(String name){
		return "Hello, "+name;
	}
	
	public String method(String time, String name){
		return time+", "+name;
	}
}
