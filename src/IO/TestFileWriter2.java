package IO;

import java.io.*;

//�����ݴ�e:/csp.log����������д��e:/csp1.log
public class TestFileWriter2 {
	public static void main(String[] args) {
		String strTemp1;
		String strTemp2;
		try {
			BufferedReader br = new BufferedReader(new FileReader("e:/csp2.log"));//�����ļ�
			BufferedWriter bw = new BufferedWriter(new FileWriter("e:/csp2.log_c"));//д���ļ�
			
			while ((strTemp1 = br.readLine()) != null) {
				if("".equals(strTemp1)||strTemp1.startsWith("DB21034E")||strTemp1.startsWith("valid")||strTemp1.startsWith("SQL0007N")||strTemp1.startsWith("SQLSTATE")||strTemp1.startsWith("DB20000I")||strTemp1.startsWith("DROP")){
				} else {
					strTemp2 = br.readLine();
					if (strTemp2!= null && strTemp2.startsWith("DB21034E")){
						bw.write(strTemp1+";");
						bw.newLine();
					}
				}
			}
			bw.flush();
			bw.close();
			br.close();
		} catch (FileNotFoundException e1){
			System.out.println("file no found");
			System.exit(-1);
		} catch (IOException e2){
			System.out.println("Wrong copy operation");
			System.exit(-1);
		}
		System.out.println("Copy finisheds");
	}
}
