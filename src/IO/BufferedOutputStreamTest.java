package IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BufferedOutputStreamTest {
	public static void main(String[] args) throws IOException {
		long startNo = 900000;
		int totalCount = 100000;
		int batchNo = 4;
		int eachBatch = totalCount/batchNo;
		
		for (int i = 0; i < batchNo; i++) {
			StringBuffer datas = new StringBuffer();
			for (int j = 0; j < eachBatch; j++) {
				startNo++;
				String data = startNo +", 1, 11, 111, 111\n";
				datas.append(data);
			}
			long beginTime = System.currentTimeMillis();
			String str = datas.toString();
			File file = new File("D:\\2.txt");
	        FileWriter fw = new FileWriter(file,true); //设置成true就是追加
	        fw.write(str);
	        fw.close();
			long usedTime = System.currentTimeMillis()-beginTime;
			System.out.println("执行完成，用时"+usedTime+"ms");
		}
		
		
	}
}
