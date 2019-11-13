package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
/**
 * �ļ���ȡ��
 * <br>��������ȡ�ļ�����Ŀ¼��ִ��ǰ��ɾ��H:/extraPath/��Ŀ¼�������ļ����ļ��У�
 * @author wxx
 * @param listFile--�嵥��ַ
 * @param projectPath--��Ŀ��ַ
 */
public class FilesRenameTool1 {
	public static void main(String[] args) {
		//��Ŀ����·��
		String listFilePath = "F:/study/c&c++/c#(WinsApp)Develop/src";  //�嵥�ļ�·��
		File f = new File(listFilePath);
		if (!f.isDirectory()) {
			System.out.println("Ŀ¼�쳣");
			System.exit(-1);
		} else {
			File files[] = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				files[i].renameTo(new File("F:/study/c&c++/c#(WinsApp)Develop/0"+fileName));
			}
		}
	}
}
