/**
 * 
 */
package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 *
 * @Author&Copyright xxxx公司
 * @version 上午10:33:49 Administrator 
 */
public class AddContentToTxt {
	public static void main(String args[]) {
		//addString("tttt","E:\\to.txt");
		//Logger.getLogger(AddContentToTxt.class).info("添加成功");
		addFromFile("E:\\from.txt","E:\\to.txt");
		Logger.getLogger(AddContentToTxt.class).info("添加成功");
	}
	
	static void addString(String content, String filePath){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath), true));
			bw.newLine();				
			bw.write(content);				
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
	
	static void addFromFile(String fromFilePath, String toFilePath){
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(fromFilePath), "gbk");
			BufferedReader br = new BufferedReader(is);
			File f = new File(toFilePath);
			String s;			
			while ((s = br.readLine()) != null) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
				bw.newLine();				
				bw.write(s);				
				bw.close();
			}
			System.out.println("成功存入~！");		
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
}
