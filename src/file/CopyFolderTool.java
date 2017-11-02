/**
 * 
 */
package file;

import java.io.File;

/**
 * @author wxx
 * @date 2015-3-20 下午10:48:12
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx公司 
 */
public class CopyFolderTool {
	public static void main(String[] args) {
		String sFinalFolder = "项目编号_新网银内管/";
		String sFolderFrom = "E:/WorkSpace_TEST1/"+sFinalFolder;  //清单文件路径
		String sFolderTo = "E:/WorkSpace_Test/"+sFinalFolder;  //清单文件路径
		
		File fFrom = new File(sFolderFrom);
		File fTo = new File(sFolderTo);
		fTo.deleteOnExit();
		fTo.mkdirs();
		if (!fFrom.isDirectory()||!fTo.isDirectory()) {
			System.out.println("目录异常");
			System.exit(-1);
		} else {
			createFolders(fFrom, sFolderTo);
		}
	}
	
	static void createFolders(File fParentFolds, String sParentFoldsTo){
		File files[] = fParentFolds.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(!files[i].isDirectory()){
				continue;
			}
			String sFileName = files[i].getName();
			String sToFolder = sParentFoldsTo +"/"+ sFileName;
			new File(sToFolder).mkdir();
			
			createFolders(files[i], sToFolder);
		}
	}
}
