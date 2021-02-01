package file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 获取文件目录下的更新清单
 * @author Administrator
 *
 */
public class FileListGetterTest {
	public static void main(String[] args) {
		//要检查的文件夹绝对路径
		String pathname = "C:\\Users\\admin\\Desktop\\新建文件夹";
		File folder = new File(pathname);//
		//清单中使用的路径
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
