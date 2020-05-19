package zip;

import net.jpountz.lz4.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by beini on 2017/10/30.
 * lz4:https://github.com/lz4/lz4-java
 */
public class LZ4Util2 {
	static String file ="135355-68710371105700513-worker117.lz4";
	
	public static void main(String[] args) throws Exception {
		
//		LZ4compress("D:\\test\\worker45\\logs\\25\\131-25-worker45.log", file);
		
		LZ4Uncompress(file, "test.log");
	}

	/**
	 * LZ4compress
	 * 
	 * @param filename 要压缩
	 * @param lz4file 压缩后
	 * @throws Exception 
	 */
	public static void LZ4compress(String filename, String lz4file) throws Exception{
	    byte[] buf = new byte[2048];
	        String outFilename = lz4file;
	        LZ4BlockOutputStream out = new LZ4BlockOutputStream(new FileOutputStream(outFilename), 32*1024*1024);
	        FileInputStream in = new FileInputStream(filename);
	        int len;
	        while((len = in.read(buf)) > 0){
	            out.write(buf, 0, len);
	        }
	        in.close();
	        out.close();
	}
	
	/**
	 * LZ4Uncompress
	 * 
	 * @param lz4file 要解压
	 * @param filename 解压后
	 * @throws Exception 
	 */
	public static void LZ4Uncompress(String lz4file, String filename) throws Exception{
	        LZ4BlockInputStream in = new LZ4BlockInputStream(new FileInputStream(lz4file));
	        FileOutputStream out = new FileOutputStream(filename);
	        int len;
	        byte[] buf = new byte[2048];
	        while((len = in.read(buf)) > 0){
	            out.write(buf, 0, len);
	        }
	        in.close();
	        out.close();
	}
}