package testIO;

import java.io.*;
//Ч������0-60000���ַ����뵽TestPrintStream1.dat�ļ�
public class TestPrintStream {
	public static void main(String[] args) {
		PrintStream ps = null;
		try {
			FileOutputStream fos = new FileOutputStream("f:/java/TestPrintStream1.dat");
			ps = new PrintStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ps!=null){
			System.setOut(ps);
		}
		int ln = 0;
		for(char c=0; c<=60000; c++){
			System.out.print(c+" ");
			if(ln++ >=100){
				System.out.println();
				ln = 0;
			}
		}
		ps.close();
	}
}
