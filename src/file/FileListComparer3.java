package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/**
 * 文件对比类
 * <br>描述： 比较FILELIST文件清单中指定的文件是否正确存在
 * @author wxx
 * @param listFile--清单地址
 * @param projectPath--项目地址
 */
public class FileListComparer3 {
	public static void main(String[] args) {
		//项目工程路径
		String listFileAll = "E:/WorkSpace_TEST/YC_EmpSys/src/file/fileList.txt";  //清单文件路径
		try {
			//清单文件路径
			File listFile = new File(listFileAll);
			if (!listFile.exists()) {
				System.out.println("清单文件不存在");
				return;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(listFileAll));
			String s = "";
			int j=1,i=0,k=0;
			while ((s=br.readLine())!=null) {
				
				if (s.trim().equals("")||s.startsWith("#")){
					continue;
				}
				
				k++;
				File f = new File(s);
				if ( !f.exists()){
					System.out.println("第"+j+"行----"+s+"---文件不存在");
					i++;    //问题文件数
				}
				j++;
			}
			System.out.println("对比完成，共"+k+"个文件，有问题文件"+i+"个"); 
			System.out.println("------------------------"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("读取异常");
		}
		
	}
}
