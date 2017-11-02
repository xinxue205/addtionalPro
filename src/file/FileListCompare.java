/**
 * 
 */
package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取在2个清单文件中都存在的记录
 * @author wxx
 * @date 2014-6-12 上午11:42:13
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 广州南天电脑系统有限公司
 */
public class FileListCompare {
	
	public static void main(String[] args) throws Exception {
		String fileList1 = "H:/ftp/list1.txt";//上线前补丁
		String fileList2 = "H:/ftp/list2.txt";//上线
		String fileList3 = "H:/ftp/list3.txt";//上线后补丁
		
		List list1 = getFileList(fileList1);
		List list2 = getFileList(fileList2);
		List list3 = getFileList(fileList3);
		
		List list = compareFileList(list1,list2);
		System.out.println("-------------1-2文件重复清单：");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		/*list = compareFileList(list2,list3);
		System.out.println("-------------2-3文件重复清单：");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		list = compareFileList(list1,list3);
		System.out.println("-------------1-3文件重复清单：");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}*/
	}
	
	/**
	 * @param fileList1
	 * @param fileList2
	 * @throws Exception 
	 */
	private static List  compareFileList(List list1, List list2) throws Exception {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		for (int i = 0; i < list1.size(); i++) {
			String file1 = (String) list1.get(i);
			for (int j = 0; j < list2.size(); j++) {
				String file2 = (String) list2.get(j);
				if(file1.equals(file2)){
					list.add(file1);
					break;
				}
			}
		}
		return list;
	}

	public static List getFileList(String listFile1) throws Exception{
		BufferedReader brr = new BufferedReader(new FileReader(listFile1));
		List list = new ArrayList();
		String ss="";
		while((ss=brr.readLine())!=null){
			if(!"".equals(ss)){
				list.add(ss);
			}
		}
		return list;
	}
}
