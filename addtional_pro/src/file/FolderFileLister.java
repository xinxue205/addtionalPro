package file;

import java.io.File;

/**
 *��ȡ�ļ������ļ��嵥
 * @Author wxx
 * @Date 2013-12-13 ����5:22:47
 * @Version 1.0  wxx create
 * @CopyRight (c) 2013 �����������ϵͳ���޹�˾ 
 */
public class FolderFileLister {

	public static void main(String[] args) {
		String path = "H:/Work/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_3/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/NewATMVH_New/WebRoot";
		String str[] = getFilesName(path);
		System.out.println("�ļ���·����"+path);
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
	}
	
	/**
	 * ��ȡ·�����ļ��嵥
	 * @param path �ļ���·��
	 * @return �ļ����У���������
	 */
	static String[] getFilesName(String path){
		File f = new File(path);
		String[] str = f.list();
		return str;
	}
}
