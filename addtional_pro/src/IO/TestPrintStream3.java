package IO;

import java.io.*;
import java.util.Date;
//效果：把在命令行输入的内容，转到日志文件
public class TestPrintStream3 {
	public static void main(String[] args) {
		String s = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			FileWriter fw = new FileWriter("f:/java/logfile.log", true);
			PrintWriter log = new PrintWriter(fw);
			while ((s=br.readLine())!= null){
				if (s.equalsIgnoreCase("exit")){ break;}
				System.out.println(s);
				log.println("==============");
				log.println(s);
				log.flush();
			}
			log.println("===="+new Date()+"====");
			log.flush();
			log.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
