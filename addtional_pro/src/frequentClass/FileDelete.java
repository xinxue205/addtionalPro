package frequentClass;

import java.io.File;

public class FileDelete {
	public static void main(String[] args) {
		String filePath = "E:\\WorkSpace\\GS_086_WM_ManSys_V01.01\\WebContent\\temp";
		String fileName = "upload_2d881404_13af7849979__8000_00000023.tmp";
		File file = new File(filePath+"\\"+fileName);
		if (file.exists()){
			file.delete();
		} else {
			System.out.println("该文件不存在！");
		}
	}
}
