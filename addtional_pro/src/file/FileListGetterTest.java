package file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ��ȡ�ļ�Ŀ¼�µĸ����嵥
 * @author Administrator
 *
 */
public class FileListGetterTest {
	public static void main(String[] args) {
		//Ҫ�����ļ��о���·��
		String pathname = "C:\\Users\\admin\\Desktop\\�½��ļ���";
		File folder = new File(pathname);//
		//�嵥��ʹ�õ�·��
//		String path ="";//"WEB-INF/mvcs/innermanage/zzsb/";
//		
//		String files[]=folder.list();
//		for (int i = 0; i < files.length; i++) {
//			File file = folder.listFiles()[i];
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			int modifyDate = Integer.parseInt(sdf.format((new Date(file.lastModified()))));
//			int currDate = 20130618;
//			//int currDate = Integer.parseInt(sdf.format(new Date()));
//			if(modifyDate>currDate){
//				System.out.println(path+file.getName());
//			}
//		}
		List list = new ArrayList();
		listSubDirFiles(list, folder, pathname.substring(0, pathname.lastIndexOf(File.separator)));
		System.out.println(list);
	}
	
	public static void listSubDirFiles(List list , File folder, String baseDir) {
		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				listSubDirFiles(list, file, baseDir);
			} else {
				String filePath = file.getAbsolutePath();
				String subString = filePath.substring(baseDir.length()+1);
				list.add(subString);
			}
		}
	}
}
