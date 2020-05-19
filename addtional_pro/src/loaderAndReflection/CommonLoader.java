package loaderAndReflection;

import java.io.*;
//将文件进行转码（加密），并在原文件名后加"_t"后保存在原目录下。
public class CommonLoader {
	public static void main(String[] args) throws Exception {
		String srcPath = args[0];
		FileInputStream fis = new FileInputStream(srcPath);
		String srcDir = srcPath.substring(0,srcPath.lastIndexOf("\\"));
		String srcFileNameAll = srcPath.substring(srcPath.lastIndexOf("\\")+1);
		String destFileName = srcFileNameAll.substring(0, srcFileNameAll.lastIndexOf("."));
		String destFileNameExt = srcFileNameAll.substring(srcFileNameAll.lastIndexOf(".")+1);
		String destFileNameAll = destFileName+"_t."+destFileNameExt;
		String destFilePath = srcDir + "\\" + destFileNameAll;
		System.out.println(destFilePath);
		FileOutputStream fos = new FileOutputStream(destFilePath);
		cypher(fis,fos);
		fis.close();
		fos.close();
	}
	
	private static void cypher(InputStream ips, OutputStream ops) throws IOException {
		int b = -1;
		while ((b=ips.read())!=-1){
			ops.write(b ^ 0xff);
		}
	}
	
	public void sayHello() {
		System.out.println("hello wxx");
	}
}
