package frequentClass;

import java.io.File;
//��1��2,3����������d:/A�µ������ļ����ļ���
public class BatchRename {
	public static void main(String[] args) {
		File f = new File("E:/download/���/Oracle");
		File[] fl = f.listFiles();
		int j=0;
		for (int i=0; i<fl.length; i++){
			j ++;
			File nf = new File("d:/A/"+j);
			System.out.println(nf.getName());
		}
	}
}
