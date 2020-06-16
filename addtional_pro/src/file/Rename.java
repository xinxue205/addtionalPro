package file;

import java.io.File;
//按1，2,3。。。命名d:/A下的所有文件或文件夹
public class Rename {
	public static void main1(String[] args) {
		File f = new File("E:/download/编程/Oracle");
		File[] fl = f.listFiles();
		int j=0;
		for (int i=0; i<fl.length; i++){
			j ++;
			File nf = new File("d:/A/"+j);
			System.out.println(nf.getName());
		}
	}
	
	public static void main(String[] args) {
		File f = new File("D:\\photo");
		File[] fl = f.listFiles();
		for (int i=0; i<fl.length; i++){
			File nf = fl[i];
			if(!nf.isDirectory()) {
				String name = nf.getPath();
				nf.renameTo(new File(name+".jpg"));
				System.out.println(name+" --- "+name+".jpg");
			}
		}
	}
}
