package file;

import java.io.File;
/**
 * �ļ���ȡ��
 * <br>��������ȡ�ļ�����Ŀ¼��ִ��ǰ��ɾ��H:/extraPath/��Ŀ¼�������ļ����ļ��У�
 * @author wxx
 * @param listFile--�嵥��ַ
 * @param projectPath--��Ŀ��ַ
 */
public class FilesRenameTool {
	public static void main(String[] args) {
		//��Ŀ����·��
		String listFilePath = "D:\\BaiduNetdiskDownload\\������\\2019-�ϰ���";  //�嵥�ļ�·��
		File f = new File(listFilePath);
		if (!f.isDirectory()) {
			System.out.println("Ŀ¼�쳣");
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
