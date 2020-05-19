/**
 * 
 */
package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取在清单文件中记录的文件都在指定目录存在
 * @author wxx
 * @date 2014-6-12 上午11:42:13
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 广州南天电脑系统有限公司
 */
public class FileListCompare1 {
	
	public static void main(String[] args) throws Exception {
		String fileList1 = "H:/ftp/201406上线包/pre.txt";//上线前补丁
		String filePath1= "H:/ftp/201406上线包/pre/";//上线前补丁
		System.out.println("-------比较上线前补丁文件");
		checkNoExistFile(fileList1,filePath1,false);
		
		String fileList2 = "H:/ftp/201406上线包/aft.txt";//上线前补丁
		String filePath2= "H:/ftp/201406上线包/aft/";//上线前补丁
		System.out.println("-------比较上线前补丁文件");
		checkNoExistFile(fileList2,filePath2,false);
		
	}
	
	/**
	 * 验证文件是否存在
	 * @param fileList
	 * @param filePath
	 * @param withPath 是否带路径
	 * @throws Exception
	 */
	public static void checkNoExistFile(String fileList,String filePath,boolean withPath) throws Exception{
		List list1 = getFileList(fileList,withPath);
		List list = checkFileExist(list1,filePath);
		if (list==null || list.size()==0){
			System.out.println("文件都存在");
		} else {
			System.out.println("验证完成，有问题文件个数："+list.size());
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}
	
	/**
	 * 
	 * @param list1
	 * @param filePath 文件父路径
	 * @return 不存在的清单
	 * @throws Exception
	 */
	private static List  checkFileExist(List list1, String filePath) throws Exception {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		for (int i = 0; i < list1.size(); i++) {
			String fileName = (String) list1.get(i);
			File file = new File(filePath+fileName);
			if(!file.exists()){
				list.add(fileName);
			}
		}
		return list;
	}

	/**
	 * 获取文件清单
	 * @param listFile1
	 * @param withPath 是否带路径
	 * @return
	 * @throws Exception
	 */
	public static List getFileList(String listFile1,boolean withPath) throws Exception{
		BufferedReader brr = new BufferedReader(new FileReader(listFile1));
		List list = new ArrayList();
		String ss="";
		while((ss=brr.readLine())!=null){
			if(!"".equals(ss)){
				String strT =  ss; 
				if(!withPath){
					String fileName = strT.substring(strT.lastIndexOf("/")+1);
					strT = fileName;
				}
				list.add(strT);
			}
		}
		return list;
	}
}
