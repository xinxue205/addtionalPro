package file;

import java.io.*;

/**
 * 获取文件夹下所有文件的总大小(无法获取子文件夹下文件大小)
 * @version 1.0 2012/11/29
 * @author wuxx
 * @param path  文件夹路径
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
