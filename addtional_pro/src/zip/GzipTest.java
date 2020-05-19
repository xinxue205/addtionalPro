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
//ʹ��GZIPOutputStream��װOutputStream����ʹ�����ѹ�����ԣ���������test.txt.gzѹ����
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.log.gz")));
        System.out.println("��ʼдѹ���ļ�...");
        int c;
        //ÿ��ѭ����ȡһ���ַ������ݣ���дһ���ַ������ݵ�ѹ�����С�
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
        //����Ϊ��λ����ȡѹ���ļ��������
        while ((c = in2.read()) != -1) {
        	out.write(String.valueOf((char) c).getBytes("UTF-8"));
        }
        in2.close();
        out.close();
	}
}
