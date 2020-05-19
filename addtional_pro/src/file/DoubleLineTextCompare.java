package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DoubleLineTextCompare {
	public static void main(String[] args) throws Exception {
		String filePath = "D:\\download\\20190327\\tt";
		String currLineMatch = "开始检测空闲连接";
		String lastLineMatch = "当前活动连接数";
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
				System.out.println("2行日志匹配，行号："+i + " ，当前行内容：" + currline);
				matchCount++;
			}
			lastLine = currline;
		}
		br.close();
		System.out.println(filePath+ " 文件对比完，匹配数：" + matchCount);
	}
}
