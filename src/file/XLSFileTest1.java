package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.*;

public class XLSFileTest1 {
	public static void main(String[] args) {
		File f = new File("d:/tem1.xls");
		
		HSSFWorkbook workBook = null;
		try {
			workBook = new HSSFWorkbook(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HSSFSheet sheet = workBook.getSheetAt(0);
		HSSFRow row0= sheet.getRow(0);
		System.out.println("单元格填充色编号是："+row0.getCell(0).getCellStyle().getFillForegroundColor());
		HSSFCell cell=row0.getCell(1);
		HSSFCellStyle style = workBook.createCellStyle();
		style.setFillForegroundColor((short) 55);
		cell.setCellStyle(style);
		
	}
}
