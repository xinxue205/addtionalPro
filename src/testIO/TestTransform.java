package testIO;

import java.io.*;
//Ч������������������������ٴ�ӡ����
public class TestTransform {
	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = null;
		
		try {
			s = br.readLine();
			while (s != null) {
				if(s.equalsIgnoreCase("exit")) {break; }
				System.out.println(s);
				s = br.readLine();
			}
			br.close();
		} catch (IOException e){
			e.printStackTrace(); System.exit(-1);
		}
	}
}
