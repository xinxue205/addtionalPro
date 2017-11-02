package file;

import java.io.*;

/**
 * ��ȡ�ļ����������ļ����ܴ�С(�޷���ȡ���ļ������ļ���С)
 * @version 1.0 2012/11/29
 * @author wuxx
 * @param path  �ļ���·��
 */
public class FolderInfoGetter {
	private String path;
	
	public long getSize(){
		File f = new File(path);
		File[] inf = f.listFiles();
		long l = 0;
		for(int i=0; i<inf.length; i++){
			l+=inf[i].length();
		}
		return l;
	}
	
	public int getFilesCount(){
		File f = new File(path);
		File[] inf = f.listFiles();
		return inf.length;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getPath() {
		return path;
	}
}
