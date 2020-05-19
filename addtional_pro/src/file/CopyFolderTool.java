/**
 * 
 */
package file;

import java.io.File;

/**
 * @author wxx
 * @date 2015-3-20 ����10:48:12
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx��˾ 
 */
public class CopyFolderTool {
	public static void main(String[] args) {
		String sFinalFolder = "��Ŀ���_�������ڹ�/";
		String sFolderFrom = "E:/WorkSpace_TEST1/"+sFinalFolder;  //�嵥�ļ�·��
		String sFolderTo = "E:/WorkSpace_Test/"+sFinalFolder;  //�嵥�ļ�·��
		
		File fFrom = new File(sFolderFrom);
		File fTo = new File(sFolderTo);
		fTo.deleteOnExit();
		fTo.mkdirs();
		if (!fFrom.isDirectory()||!fTo.isDirectory()) {
			System.out.println("Ŀ¼�쳣");
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
