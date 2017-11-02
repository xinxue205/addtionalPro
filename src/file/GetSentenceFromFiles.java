package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetSentenceFromFiles {
	public static void main(String[] args) throws IOException {
		File folder = new File("E:/temp/");//
		String files[]=folder.list();
		for (int i = 0; i < files.length; i++) {
			File file = folder.listFiles()[i];
			BufferedReader freader = null;
			freader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String strTemp="";
			while ((strTemp = freader.readLine()) != null) {
				String test="";
				if (strTemp.indexOf("select ")!=-1){ 
					test="select ";
				}
				if (strTemp.indexOf("Select ")!=-1){
					test="Select ";
				}
				if (strTemp.indexOf("SELECT ")!=-1){
					test="SELECT ";
				}
				if ((strTemp.indexOf("select ")!=-1||(strTemp.indexOf("Select ")!=-1)||(strTemp.indexOf("SELECT ")!=-1))){
					String strTemp_temp=strTemp.substring(strTemp.indexOf(test));
					strTemp_temp.replaceAll("/&/#13;/&/#10;/&/#9;","");
					strTemp_temp.replaceAll("/&/#13;/&/#10;","");
					System.out.println(strTemp_temp);
				}
			}
		}
	}
}
