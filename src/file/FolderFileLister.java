package file;

import java.io.File;

/**
 *获取文件夹下文件清单
 * @Author wxx
 * @Date 2013-12-13 下午5:22:47
 * @Version 1.0  wxx create
 * @CopyRight (c) 2013 广州南天电脑系统有限公司 
 */
public class FolderFileLister {

	public static void main(String[] args) {
		String path = "H:/Work/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_3/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/NewATMVH_New/WebRoot";
		String str[] = getFilesName(path);
		System.out.println("文件夹路径："+path);
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
	}
	
	/**
	 * 获取路径下文件清单
	 * @param path 文件夹路径
	 * @return 文件（夹）名的数组
	 */
	static String[] getFilesName(String path){
		File f = new File(path);
		String[] str = f.list();
		return str;
	}
}
