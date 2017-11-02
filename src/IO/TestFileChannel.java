package IO;

import java.io.*;
import java.nio.channels.FileChannel;

//Ч������ͨ�������ļ� �ʺϵ����������ļ�����
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
			in = fi.getChannel();//�õ���Ӧ���ļ�ͨ��
	        out = fo.getChannel();//�õ���Ӧ���ļ�ͨ��
	        in.transferTo(0, in.size(), out);//��������ͨ�������Ҵ�inͨ����ȡ��Ȼ��д��outͨ��
	        
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
        System.out.println("������ɣ���ʱ"+usedTime+"ms");
	}
}
