package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * 文件对比类
 * <br>描述： 比较文件清单1中所有的文件是否都记录在文件清单2中
 * @author wxx
 * @param projectFilePath--项目地址
 */
public class FileListComparer2 {
	public static void main(String[] args) {
		
		String listFile2=FileListComparer2.class.getResource("").getPath()+"list2.txt"; //比较文件
		String totalFile=FileListComparer2.class.getResource("").getPath()+"list1.txt"; //总文件

		String totalFilesList[] = getTotalList(totalFile);
		comparerList(listFile2,totalFilesList);
		
		//项目3
		/*String projectPath2="E:/WorkSpace_Dev3/innermanage_csp"; //项目路径（以/结尾）
		String listFile2="E:/WorkSpace_Dev3/innermanage_csp/deployment/patch/innermanage_csp_patch_20130705-20130710.txt"; //清单文件位置
		System.out.println("项目3"+flc.comparerList(projectPath2,listFile2));*/
	}
	
	/**
	 * 
	 * @param listFile1
	 * @param totalFilesList
	 */
	public static void comparerList(String listFile1, String[] totalFilesList){
		try {
			BufferedReader br = new BufferedReader(new FileReader(listFile1));
			String s = null;
			int i = 0,j = 0,k=0;
			while ((s=br.readLine())!=null) {
				j++;    //行数
				if (s.trim().equals("")||s.startsWith("#")){
					continue;
				}
				
				boolean isExist = false; 
				for (int l = 0; l < totalFilesList.length; l++) {
					String temp = totalFilesList[l];
					if(s.equals(temp)){
						isExist = true;
						break;
					}
				}
				
				if (isExist==false){
					System.out.println("第"+j+"行--"+s+"--无匹配文件");
					i++;    //问题文件数
				}
				k++;   //文件数
				s="";
			}
			System.out.println(listFile1+"对比完成，共"+k+"个文件，有问题文件"+i+"个");
			System.out.println("-------------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("读取异常"); 
		}
		
	}
	
	static String[] getTotalList(String listFile2){
		String ss = "";
		String returnList = "";
		try {
			BufferedReader brr = new BufferedReader(new FileReader(listFile2));
			while((ss=brr.readLine())!=null){
				if (ss.trim().equals("")||ss.startsWith("#")){
					continue;
				}
				returnList += ss+"/&";
			}
			returnList = returnList.substring(0, returnList.length()-2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList.split("/&");
	}
}
