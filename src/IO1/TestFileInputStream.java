package testIO;

import java.io.*;
//��f:/java/TestInput.txt�����ݴ�ӡ����
public class TestFileInputStream {
	public static void main(String[] args) {
		int b = 0;
		long num= 0;
		FileInputStream in = null;
		try {
			in = new FileInputStream("d:/tem.xlsx");
			while((b = in.read()) != -1){
				System.out.print((char)b);
				num ++;
			}
			in.close();
		} catch (FileNotFoundException e1){
			System.out.println("file not found");
		} catch (IOException e2) {
			System.out.println("Wrong read operation");
		}
		System.out.println("Total read "+num+" byte");
	}
}
