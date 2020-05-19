package exception;

public class SubThreadException {
	public static void main(String[] args) {
		try{
			int a = 1, b=-1;
			test( a,   b);
		}catch(Throwable e){
			e.printStackTrace();
			throw new RuntimeException("子方法异常了");
		}
	}
	
	static void test(int a, final int b){
		if(a == -1){
			throw new RuntimeException("参数a异常了");
		}
		new Thread(){
			public void run() {
				TestClass t = new TestClass();
				t.subMethod(b);
			};
		}.start();
	}
	
	void subMethod(){
		
	}
}

class TestClass{
	void subMethod(int b){
		if(b == -1){
			throw new RuntimeException("参数b异常了");
		}
		System.out.println("sub thread finished");
	}
}
