package test;
import java.io.IOException;

/*
 * ���ļ�
 */

public class OpenFile {
	public static void main(String[] args) {
		String fileName="123123.t3";  //�ļ���
		String folderPath="d:";	//�ļ�����·����·������\��
		
		Starter st = new Starter(fileName,folderPath);
		st.open();//���ļ�
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
