package testIO;

import java.io.*;
//Ч������TestBufferWriter.txt������100�����������f:/java/TestBufferReader.txt�����ݴ�ӡ����
public class TestBufferStream1 {
	public static void main(String[] args) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("f:/java/TestBufferWriter.txt"));
			BufferedReader br = new BufferedReader(new FileReader("f:/java/TestBufferReader.txt"));
			String s = null;
			for (int i=1; i<100;i++) {
				bw.write(String.valueOf(Math.random()));
				bw.newLine();
			}
			bw.flush();
			bw.close();
			while((s=br.readLine()) != null) {
				System.out.println(s);
			}
			br.close();
		} catch(IOException e1){e1.printStackTrace(); System.exit(-1);}
	}
}
