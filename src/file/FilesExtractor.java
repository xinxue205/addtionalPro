package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 文件提取类
 * <br>描述：提取文件到新目录（执行前需删除根目录）
 * @author wxx
 * @param listFile--清单地址
 * @param projectPath--项目地址
 */
public class FilesExtractor {
	public static void main(String[] args) {
		//项目工程路径
		String listFilePath = "H:/WorkProjects/YC_EmpSys/src/file/fileList.txt";  //清单文件路径
		String projectRootPath="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/"; //项目路径
		String extractRoot = "H:/extraPath/";
		boolean createPathFlag = false;
		
		File listFile = new File(listFilePath);
		if (!listFile.exists()) {
			System.out.println("清单文件不存在");
			return;
		}
		
		List<String> filesList = getFilesPathList(listFilePath);
		
		if(filesList.size()==0){
			System.out.println("清单中未填写有效文件");
			return;
		}
		
		int i = 0;
		for (int j = 0; j < filesList.size(); j++) {
			String filePath = (String) filesList.get(j);
			String filePathTail = filePath.substring(26, filePath.length());
			String middlePath = "NewATMVH_New/WebRoot/";
			
			//report工程
			if(filePath.startsWith("/web/monitor/report.war/")){
				filePathTail = filePath.substring(24, filePath.length());
				middlePath = "report/WebRoot/";
			
			//app工程
			} else if (filePath.startsWith("/app/ATMVH_APP/classes/")){
				filePathTail = filePath.substring(23, filePath.length());
				middlePath = "ATMVH_APP/bin/";
			}
			
			String oldFilePath = projectRootPath+middlePath+filePathTail;
			File oldFile = new File(oldFilePath);
			if (!oldFile.exists()) {
				System.out.println(oldFilePath+"---文件不存在");
				i++;
				continue;
			}
			
			String fileName = filePathTail.substring(filePathTail.lastIndexOf("/")+1, filePathTail.length());
			String extractFilePath = createPathFlag ? extractRoot+middlePath+filePathTail : extractRoot+middlePath+fileName;
			File extractFile = new File(extractFilePath);
			
			System.out.println("开始提取文件："+middlePath+filePathTail);
			try {
				if (!extractFile.exists()) {
					File newFilePath = new File(extractFilePath.substring(0,extractFilePath.lastIndexOf("/")));
					newFilePath.mkdirs();
					extractFile.createNewFile();
				}
				
				int b = 0;
				FileInputStream in = new FileInputStream(oldFile);
				FileOutputStream out = new FileOutputStream(extractFile);
			
				while ((b = in.read())!= -1){
					out.write(b);
				}
				in.close();
				out.close();
				
			} catch (Exception e1) {
				System.out.println(oldFilePath+"---文件提取异常");
				i++;
				e1.printStackTrace();
				// TODO: handle exception
			}
			
		}
		System.out.println("----------------------------"); 
		System.out.println("提取完成，共"+filesList.size()+"个文件，有问题文件"+i+"个"); 
	}
	
	static List<String> getFilesPathList(String listFilePath){
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(listFilePath));
			String s = "";
			while ((s=br.readLine())!=null) {
				if ("".equals(s)){
					continue;
				}
				list.add(s.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
