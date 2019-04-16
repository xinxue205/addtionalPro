package testIO;

import java.io.*;

//Ч������TestBufferWriter.txt������100�����������f:/java/TestBufferReader.txt�����ݴ�ӡ����
public class TestBufferStream {
	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("H:/extraPath/1/eclipse3.7.2.rar"));
			OutputStream os = new BufferedOutputStream(new FileOutputStream("H:/extraPath/2/eclipse3.7.2.rar"));
			byte[] buffer = new byte[2048]; 
			int i = 0;
			while ( (i = is.read(buffer)) != -1) {
				os.write(buffer,0, i);
			}
			
			is.close();
			os.flush();
			os.close();
		} catch(Exception e1){
			e1.printStackTrace(); 
			System.exit(-1);
		}
		long usedTime = System.currentTimeMillis()-beginTime;
        System.out.println("������ɣ���ʱ"+usedTime+"ms");
	}
}
