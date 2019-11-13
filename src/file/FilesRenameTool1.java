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
 * 文件提取类
 * <br>描述：提取文件到新目录（执行前先删除H:/extraPath/根目录下所有文件和文件夹）
 * @author wxx
 * @param listFile--清单地址
 * @param projectPath--项目地址
 */
public class FilesRenameTool1 {
	public static void main(String[] args) {
		//项目工程路径
		String listFilePath = "F:/study/c&c++/c#(WinsApp)Develop/src";  //清单文件路径
		File f = new File(listFilePath);
		if (!f.isDirectory()) {
			System.out.println("目录异常");
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
