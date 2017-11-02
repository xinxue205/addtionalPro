package IO;

import java.io.*;
import java.util.Date;
//Ч����������������������ݣ�ת����־�ļ�
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
