package file;

import java.io.File;
import java.util.Date;

public class FileInfor {
	public static void main(String[] args) {
		File f = new File("e:\\2018年“羊城工匠杯”人工智能产业职工职业技能大赛参赛作品申报书(1)(1)-产业化前景分析-吴新学.docx");
		System.out.println(new Date(f.lastModified()));
		
	}
}
