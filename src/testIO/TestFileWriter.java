package testIO;

import java.io.*;

//�����ݴ�f:/java/TestReader.txt������д��f:/java/TestWriter.txt
public class TestFileWriter {
	public static void main(String[] args) {
		FileReader fr = null;
		try {
			fr = new FileReader("d:/tem.xlsx");
			//fw = new FileWriter("d:/javaw/check_report.php.pdf");
			while (( fr.read())!= -1){
				//fw.write(b);
				System.out.print(fr.toString());
			}
			fr.close();
			//fw.close();
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
