package zip;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipTest {

	public static void main1(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test.log"), "UTF-8"));
//使用GZIPOutputStream包装OutputStream流，使其具体压缩特性，最后会生成test.txt.gz压缩包
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.log.gz")));
        System.out.println("开始写压缩文件...");
        int c;
        //每次循环读取一个字符的数据，并写一个字符的数据到压缩包中。
        while ((c = in.read()) != -1) {
            out.write(String.valueOf((char) c).getBytes("UTF-8"));
        }
        in.close();
        out.close();
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
                new GZIPInputStream(new FileInputStream("135373-440518664885946345-USER-20190827JZ.gz")), "UTF-8"));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("test1.log")));
		 int c;
        //以行为单位来读取压缩文件里的内容
        while ((c = in2.read()) != -1) {
        	out.write(String.valueOf((char) c).getBytes("UTF-8"));
        }
        in2.close();
        out.close();
	}
}
