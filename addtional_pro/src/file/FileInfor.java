package file;

import java.io.File;
import java.util.Date;

public class FileInfor {
	public static void main(String[] args) {
		File f = new File("e:\\2018�ꡰ��ǹ��������˹����ܲ�ҵְ��ְҵ���ܴ���������Ʒ�걨��(1)(1)-��ҵ��ǰ������-����ѧ.docx");
		System.out.println(new Date(f.lastModified()));
		
	}
}
