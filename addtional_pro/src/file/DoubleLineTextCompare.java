package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DoubleLineTextCompare {
	public static void main(String[] args) throws Exception {
		String filePath = "D:\\download\\20190327\\tt";
		String currLineMatch = "��ʼ����������";
		String lastLineMatch = "��ǰ�������";
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
		BufferedReader br = new BufferedReader(isr);
//		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String currline = "";
		String lastLine = "";
		int i = 0 ;
		int matchCount = 0;
		while ((currline=br.readLine())!=null) {
			i++;
			if ("".equals(currline)){
				lastLine = currline;
				continue;
			}
			if(currline.indexOf(currLineMatch)>0 && lastLine.indexOf(lastLineMatch)>0 ){
				System.out.println("2����־ƥ�䣬�кţ�"+i + " ����ǰ�����ݣ�" + currline);
				matchCount++;
			}
			lastLine = currline;
		}
		br.close();
		System.out.println(filePath+ " �ļ��Ա��꣬ƥ������" + matchCount);
	}
}
