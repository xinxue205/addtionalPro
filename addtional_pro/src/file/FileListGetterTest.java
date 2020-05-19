package file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取文件目录下的更新清单
 * @author Administrator
 *
 */
public class FileListGetterTest {
	public static void main(String[] args) {
		//要检查的文件夹绝对路径
		File folder = new File("D:\\work\\workspace-sdimain\\sdi-main\\sdi-designer\\lib");//
		//清单中使用的路径
		String path ="";//"WEB-INF/mvcs/innermanage/zzsb/";
		
		String files[]=folder.list();
		for (int i = 0; i < files.length; i++) {
			File file = folder.listFiles()[i];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int modifyDate = Integer.parseInt(sdf.format((new Date(file.lastModified()))));
			int currDate = 20130618;
			//int currDate = Integer.parseInt(sdf.format(new Date()));
			if(modifyDate>currDate){
				System.out.println(path+file.getName());
			}
		}
	}
}
