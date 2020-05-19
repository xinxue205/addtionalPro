package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/**
 * 文件对比类
 * <br>描述： 比较FILELIST文件清单中指定的文件是否正确存在
 * @author wxx
 * @param listFile--清单地址
 * @param projectPath--项目地址
 */
public class FileListComparer {
	public static void main(String[] args) {
		//项目工程路径
		String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/fileList.txt";  //清单文件路径
		//String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/fileList_201403.txt";  //清单文件路径
		String projectPathWeb="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/NewATMVH_New/WebRoot/"; //项目路径
		String projectPathAPP="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/ATMVH_APP/bin/"; //项目路径
		String projectPathReport="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/report/WebRoot/"; //项目路径
		
		try {
			//清单文件路径
			File listFile = new File(listFileAll);
			if (!listFile.exists()) {
				System.out.println("清单文件不存在");
				return;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(listFileAll));
			String s = null;
			String projectPath = projectPathWeb;
			int i = 0,j = 0,k=0;
			String projectName = "NewATMVH_New";
			while ((s=br.readLine())!=null) {
				j++;    //行数
				if (s.trim().equals("")||s.startsWith("#")){
					if (s.startsWith("#ATMVH_APP")||s.startsWith("#report")){
						projectPath=s.startsWith("#ATMVH_APP")?projectPathAPP:projectPathReport;
						projectName = s.startsWith("#ATMVH_APP")?"ATMVH_APP":"report";
					}
					continue;
				}
				k++;   //文件数
				File f = new File(projectPath+s);
				if ( !f.exists()||s.endsWith(" ")||s.endsWith(".")){
					System.out.println("第"+j+"行--"+projectName+"工程--"+s+"---文件不存在");
					i++;    //问题文件数
				}
				//System.out.println(s);
				s="";
			}
			System.out.println("对比完成，共"+k+"个文件，有问题文件"+i+"个"); 
			System.out.println("------------------------"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("读取异常");
		}
		
	}
}
