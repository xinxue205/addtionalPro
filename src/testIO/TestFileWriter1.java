package testIO;

import java.io.*;
//把f:/java/TestInput.txt中的内容读出后写入f:/java/TestOutput.txt
public class TestFileWriter1 {
	public static void main(String[] args) {
		int b = 0;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			try {
				in = new FileInputStream("d:/java/check_report.php.pdf");
			} catch (FileNotFoundException e2) { 
				System.out.println("Original File not found"); System.exit(-1);
			}
			try {
				out = new FileOutputStream("d:/javaw/check_report.php.pdf");
			} catch (FileNotFoundException e3) { 
				System.out.println("Aim File not found"); System.exit(-1);
			}
			while ((b = in.read())!= -1){
				out.write(b);
			}
			in.close();
			out.close();
		} catch (IOException e1) {
			System.out.println("Wrong copy operation"); System.exit(-1);
			// TODO: handle exception
		}
		System.out.println("Copy finished");
	}
}
