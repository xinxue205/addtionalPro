package IO;

import java.io.*;
import java.nio.channels.FileChannel;

//效果：用通道传输文件 适合单个大容量文件传输
public class TestFileChannel {
	public static void main(String[] args) {
		String fromFile = "H:/extraPath/1/eclipse3.7.2.rar";
		String toFile = "H:/extraPath/2/eclipse3.7.2.rar";
		FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        long beginTime = System.currentTimeMillis();
        try {
			fi = new FileInputStream(fromFile);
			fo = new FileOutputStream(toFile);
			in = fi.getChannel();//得到对应的文件通道
	        out = fo.getChannel();//得到对应的文件通道
	        in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
	        
	        fi.close();
	        fo.flush();
	        fo.close();
	        in.close();
	        out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long usedTime = System.currentTimeMillis()-beginTime;
        System.out.println("复制完成，用时"+usedTime+"ms");
	}
}
