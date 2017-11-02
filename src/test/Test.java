package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
class Clazz{
	int a =2;
}
public class Test{
	public static void main(String args[]) throws IOException {
//		String str = "";
//		System.out.println(str.split(",").length);
		test1(0-2,1);
//		Clazz a = new Clazz();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss"); 
//		test002(a);
	}
	
	public static void test1(int a, int b){
		System.out.println(a);
	}
	
	public static void test002(Clazz a){
		a.a=3;
	}
	
	public void test001(){
		StringBuffer str1 = new StringBuffer("111");
		test1(str1);
		System.out.println(str1);
		
		String str2 = "222";
		test2(str2);
		System.out.println(str2);
	}
	
	/**
	 * @param str2
	 */
	private static void test2(String str2) {
		// TODO Auto-generated method stub
		str2+="bbb";
	}

	public static void test1(StringBuffer str){
		str.append("aaa");
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
		
		String fileToBeRead = "C:\\exp.xls"; // excel位置 
        int coloum = 1; // 比如你要获取第1列 
        try { 
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream( 
                    fileToBeRead)); 
            HSSFSheet sheet = workbook.getSheet("Sheet1"); 
  
            for (int i = 0; i <= sheet.getLastRowNum(); i++) { 
                HSSFRow row = sheet.getRow((short) i); 
                if (null == row) { 
                    continue; 
                } else { 
                    HSSFCell cell = row.getCell((short) coloum); 
                    if (null == cell) { 
                        continue; 
                    } else { 
                        System.out.println(cell.getNumericCellValue()); 
                        int temp = (int) cell.getNumericCellValue(); 
                        cell.setCellValue(temp + 1); 
                    } 
                } 
            } 
            FileOutputStream out = null; 
            try { 
                out = new FileOutputStream(fileToBeRead); 
                workbook.write(out); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } finally { 
                try { 
                    out.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
	 }
        
	public static void printTime(String s) throws Exception  {
		for (int i = 12; i <=24; i++) {
			for (int j = 0; j < 56; j=j+5) {
				String sec = j<10?"0"+j:""+j;
				System.out.println(i+sec+"00");
			}
		}
		for (int i = 0; i <=11; i++) {
			String hours = i<10?"0"+i:""+i;
			for (int j = 0; j < 56; j=j+5) {
				String sec = j<10?"0"+j:""+j;
				System.out.println(hours+sec+"00");
			}
		}
	}
	
	public void testPath(){
		System.out.println(Test.class.getResource("/").getPath());
		System.out.println(Test.class.getResource("").getPath());
		System.out.println(this.getClass().getResource("").getPath());
		System.out.println(Test.class.getClassLoader().getResource(""));
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(System.getProperty("user.dir")  );
		System.out.println(System.getProperty("java.class.path"));
	}
	
	private static List sortDetailList(List detailList) {
		// TODO Auto-generated method stub
		List returnL = new ArrayList();
		int j = -1;
		
		if (j!=-1) detailList.remove(j);
		returnL.addAll(detailList);
		return returnL;
	}
	
}

