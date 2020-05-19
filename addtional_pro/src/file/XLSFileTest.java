package file;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class XLSFileTest {
	public static void main(String[] args) {
		FileOutputStream fileOut = null;
		
		String colsTitles[]={"��������","�豸����","�豸�ͺ�","����","�豸Ͷ����������","Ͷ����������","ǩ��ʱ��"};
		String fileTitle="�豸�������뼰��������";
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		sheet.setDefaultColumnWidth(15);
		
		HSSFCellStyle style= workBook.createCellStyle();
		HSSFFont font = workBook.createFont();
		font.setFontHeight((short) 230);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		style.setFillForegroundColor((short) 55);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFCellStyle style1= workBook.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
		
		int rowIndex = 0;
		HSSFRow row0 = sheet.createRow(rowIndex++);
		row0.setHeight((short) 400);
		HSSFCell cell1=row0.createCell(0);
		cell1.setCellValue(fileTitle);
		cell1.setCellStyle(style);
		
		HSSFRow row1 = sheet.createRow(rowIndex++);
		row1.setHeight((short) 400);
		for (int i=0;i<colsTitles.length;i++){
			HSSFCell cellt=row1.createCell(i);
			String colsTitle=colsTitles[i];
			cellt.setCellValue(colsTitle);
			cellt.setCellStyle(style);
		}
		
		HSSFRow row2 = sheet.createRow(rowIndex++);
		HSSFCell cellt=row2.createCell(0);
		cellt.setCellValue("����");
		cellt.setCellStyle(style1);
		//д���ļ�
		try {
			fileOut = new FileOutputStream("d:/tem.xls");
			workBook.write(fileOut);
		} catch (IOException ex) {
		} finally {
			System.out.println("finish!");
			try {
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException ex) {
			}
		}
	}
}
