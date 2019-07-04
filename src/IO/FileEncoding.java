package IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileEncoding {
	static String filePath = "";

	public static void main(String[] args) throws Exception {
		File file=new File(filePath);
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));

		String line=null;
        while((line=br.readLine())!=null){
           System.out.println(line.trim());
        }
	}
	
	public static void main1(String[] args) throws Exception {
		//ָ�������ʽ utf-8
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream("utf_8.txt"),"UTF-8");
        osw.write("�������");
        osw.close();
 
        //ָ��gbk�����ʽ
        OutputStreamWriter osw2=new OutputStreamWriter(new FileOutputStream("gbk.txt"),"GBK");
        osw2.write("�������");
        osw2.close();
	}
}
