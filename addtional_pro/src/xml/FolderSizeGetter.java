package xml;

import java.io.*;

/**
 * ��ȡ�ļ����������ļ����ܴ�С(�޷���ȡ���ļ������ļ���С)
 * @version 1.0 2012/11/29
 * @author wuxx
 * @param path  �ļ���·��
 */
public class FolderSizeGetter {
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


	public void setPath(String path) {
		this.path = path;
	}


	public String getPath() {
		return path;
	}
}
