package frequentClass;

import java.io.File;
//按1，2,3。。。命名d:/A下的所有文件或文件夹
public class BatchRename {
	public static void main(String[] args) {
		File f = new File("E:/download/编程/Oracle");
		File[] fl = f.listFiles();
		int j=0;
		for (int i=0; i<fl.length; i++){
			j ++;
			File nf = new File("d:/A/"+j);
			System.out.println(nf.getName());
		}
	}
}
