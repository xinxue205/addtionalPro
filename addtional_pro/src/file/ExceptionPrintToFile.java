package file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ExceptionPrintToFile {
	public static void main(String[] args) {
		PrintStream ps = null;
		try {
			ps = new PrintStream(new FileOutputStream("D:\\logs\\error.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String str[] = {"",""};
		try{
			System.out.println(str[3]);
		} catch (Exception e) {
			e.printStackTrace(ps);
		}
	}
}
