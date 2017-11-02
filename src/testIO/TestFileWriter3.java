package testIO;

import java.io.*;

//将内容从e:/csp.log读出后处理再写入e:/csp1.log
public class TestFileWriter3 {
	public static void main(String[] args) {
		String strTemp1;
		try {
			BufferedReader br = new BufferedReader(new FileReader("e:/csp.sql"));//读的文件
			BufferedWriter bw = new BufferedWriter(new FileWriter("e:/csp1.sql"));//写的文件
			
			while ((strTemp1 = br.readLine()) != null) {
				if(strTemp1.startsWith("DROP TABLE")){
					bw.write(strTemp1);
					bw.newLine();
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
