/**
 * 
 */
package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-9-5 上午11:18:48
 * @Description
 * @version 1.0 Shawn create
 */
public class xlsxRead {
	public static void main(String args[]) throws IOException {
		String path1 = "H:/file1.xlsx";
		String path2 = "H:/file3.xlsx";
		modifyExcel(path1, path2);
	}
	
	public static void modifyExcel(String oldFilePath,String newFilePath) throws IOException   
    {   
		File oldFile = new File(oldFilePath);
		File newFile = new File(newFilePath);
		if(!oldFile.exists()){
			System.out.println("原文件不存在");
			return;
		}
		if(!newFile.exists()){
			newFile.createNewFile();
		}
		
		try{
			FileInputStream fis = new FileInputStream(oldFile);  
			Workbook wb = WorkbookFactory.create(fis); 
			Sheet st = wb.getSheetAt(0); 
			Row row = st.getRow(0); 
			Cell cell = row.getCell(0);
			String content = cell.getStringCellValue();
			System.out.println(content);
		} catch (Exception e){
			e.printStackTrace();
		}
	 }
}
