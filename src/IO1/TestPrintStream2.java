package testIO;

import java.io.*;
//把在命令行输入文件名的文件内容打印出来
public class TestPrintStream2 {
	public static void main(String[] args) {
		String filename = args[0];
		if(filename!=null){
			list(filename,System.out);
		}
	}

	private static void list(String f, PrintStream fs) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s = null;
			while ((s=br.readLine())!=null) {
				fs.println(s);
			}
			br.close();
		} catch (IOException e){
			fs.println("Can't read file");
		}
	}
}
