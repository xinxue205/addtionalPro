package file;

import java.io.File;
import java.io.FileInputStream;
import jxl.Sheet;
import jxl.Workbook;
/**
 * 文件对比类
 * <br>描述： 比较FILELIST文件清单中指定的文件是否正确存在
 * @author wxx
 * @param listFile--清单地址
 * @param projectPath--项目地址
 */
public class ExlFileListComparer {
	public static void main(String[] args) {
		//项目工程路径
		String listFileAll = "H:/ftp/1filelist.xls";  //清单文件路径
		//String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/1filelist.xls";  //清单文件路径
		//String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/fileList_201403.txt";  //清单文件路径
		String rootPath = "H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/";
		int exlNum = 0; //第几个表格,第一个是0
		
		try {
			File listFile = new File(listFileAll);
			if (!listFile.exists()) {
				System.out.println("清单文件不存在");
				return;
			}
			
			Workbook workbook  = Workbook.getWorkbook(new FileInputStream(listFileAll));
			Sheet sheet = workbook.getSheet(exlNum); // 第1个sheet
			
			int maxRowNo = sheet.getRows();
			if(maxRowNo<=3){
				System.out.println("清单文件的第"+(exlNum+1)+"表格未填写文件");
				return;
			}
			
			String[] files = new String[maxRowNo-3];			
			for (int i = 3,j=0; i < maxRowNo; i++,j++) {
				files[j] = sheet.getCell(19, i).getContents().toString().trim();
			}

			int j=0;
			for (int i = 0; i < files.length; i++) {
				String filePath = files[i].trim();
				if(!"".equals(filePath)){
					String projectPath = "NewATMVH_New/WebRoot/";
					String tailPath = filePath.substring(26, filePath.length());
					
					//report工程
					if(filePath.startsWith("/web/monitor/report.war/")){
						tailPath = filePath.substring(24, filePath.length());
						projectPath="report/WebRoot/";
					
					//APP工程
					} else if (filePath.startsWith("/app/ATMVH_APP/classes/")){
						tailPath = filePath.substring(23, filePath.length());
						projectPath="ATMVH_APP/bin/";
					}
					
					String aimPath = rootPath+projectPath+tailPath;
					File f = new File(aimPath);
					if ( !f.exists()){
						System.out.println("第"+(i+4)+"行："+projectPath+"目录  "+tailPath+"文件不存在");
						j++;
					}
				} else {
					System.out.println("第"+(i+4)+"行文件路径为空，需修正");
					j++;
				}
			}
			
			System.out.println("------------------------"); 
			System.out.println("对比完成，共"+files.length+"个文件，有问题文件"+j+"个"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("读取异常");
		}
		
	}
}
