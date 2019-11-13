package file;

import java.io.File;
/**
 * 文件提取类
 * <br>描述：提取文件到新目录（执行前先删除H:/extraPath/根目录下所有文件和文件夹）
 * @author wxx
 * @param listFile--清单地址
 * @param projectPath--项目地址
 */
public class FilesRenameTool {
	public static void main(String[] args) {
		//项目工程路径
		String listFilePath = "D:\\BaiduNetdiskDownload\\顾子明\\2019-上半年";  //清单文件路径
		File f = new File(listFilePath);
		if (!f.isDirectory()) {
			System.out.println("目录异常");
			System.exit(-1);
		} else {
			File files[] = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) continue;
				String fileName = files[i].getName();
				System.out.println(fileName);
				
				String date = fileName.substring(0, 5);
				if(!Character.isDigit(date.charAt(4))) {
					System.out.println(fileName+ "  " + fileName.substring(0,3) + "  " + fileName.substring(3));
					fileName = fileName.substring(0,3)+"0"+fileName.substring(3);
					files[i].renameTo(new File(listFilePath+"\\"+fileName));
				}
				
//				String month = fileName.substring(0,fileName.indexOf("."));
//				int monthi = Integer.parseInt(month);
//				if (monthi < 10) {
//					fileName = "0"+fileName;
//					files[i].renameTo(new File(listFilePath+"\\"+fileName));
//				}
				
//				fileName = fileName.substring(5);
//				files[i].renameTo(new File(listFilePath+"\\"+fileName));
			}
		}
	}
}
