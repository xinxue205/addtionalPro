package GUI;
interface Window{
	public void open();
	public void close();
}

abstract class WindowAdapterr implements Window{
	public void open(){};
	public void close(){
		System.out.println("Close the window!!!");			
	};
}

public class MyWindow extends WindowAdapterr{
	public static void main(String args[]){
		Window w = new MyWindow();
		w.close();
		w.open();
	}
}	