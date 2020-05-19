package IO;

import java.io.*;
/**
 * 将文件内容读取后在控制台打印出来
 * @author Administrator
 *
 */
public class TestFileInputStream1 {
	public static void main(String[] args) {
		int b = 0;
		FileInputStream in =null;
		try {in = new FileInputStream("f:/java/TestPrintStream1.dat");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(-1);
		}
		
		try{
			long num = 0;
			while((b = in.read()) != -1) {
				System.out.print((char)b+" ");
				num ++;
			}
			in.close();
			System.out.println();
			System.out.println("Total read "+num+" byte");
			
		} catch (IOException e1) {
			System.out.println("Wrong read");
			System.exit(-1);
		}
		
	}
}
