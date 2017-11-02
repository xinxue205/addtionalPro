package test;
import java.io.IOException;

/*
 * 打开文件
 */

public class OpenFile {
	public static void main(String[] args) {
		String fileName="123123.t3";  //文件名
		String folderPath="d:";	//文件所在路径（路径符加\）
		
		Starter st = new Starter(fileName,folderPath);
		st.open();//打开文件
	}
}

class Starter{
	String fileName;
	String folderPath;
	public Starter(String fileName, String folderPath) {
		this.fileName=fileName;
		this.folderPath=folderPath;
	}
	public void open(){
		String path=folderPath+"\\"+fileName;
		String command= "cmd.exe /c start "+path; 
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
