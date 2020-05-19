package listener.test1;

public class TestDemo  {
	private DemoSource ds;

	public TestDemo() {
	}

	public static void main(String args[])
	{
		DemoSource ds=new DemoSource("ds1");
		ds.addDemoListener(new ListenerAdapter(){
			String name = "open";
			
			
			public void demoOpenEvent(DemoSource ds){
				System.out.println("jiantingqi1 chufa le");
			}
			
		});
		
		ds.addDemoListener(new ListenerAdapter(){
			String name = "close";
			
			public void demoCloseEvent(DemoSource ds){
				System.out.println("jiantingqi2 chufa le");
			}
			
		});
		
		ds.demoOpenEvent(ds);
		ds.demoCloseEvent(ds);
	}

}
