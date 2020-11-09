package annie;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OrderCollector {
	static String[] targetHeader = {"订单号", "收件人姓名", "收件人电话", "快递单号", "单品名称"};
	
	public static void main(String[] args) throws Exception {
		String s = getConf();
		JSONObject jsonObject = JSONObject.fromObject(s);
		String sourceFileName = (String) jsonObject.get("sourceFileName");
		String sourceSheetOrder = (String) jsonObject.get("sourceSheetOrder");
		String sourceBeginLines = (String) jsonObject.get("sourceBeginLines");
		JSONArray sourceFieldNosA = (JSONArray)jsonObject.get("sourceFieldNos");
		String[] sourceFieldNosS = new String[sourceFieldNosA.size()];
		for (int i = 0; i < sourceFieldNosS.length; i++) {
			sourceFieldNosS[i] = (String) sourceFieldNosA.get(i);
		}
		
		List  list  = getData(sourceFileName, sourceSheetOrder, sourceBeginLines, sourceFieldNosS); 
//		System.out.println(list);
		//transData(sourceFileName, targetFileName, sourceSheetName, targetSheetName, sourceBeginLines, targetBeginLines, ja);
	}

	private static List getData(String sourceFileName, String sourceSheetOrder, String sourceBeginLines, String[] sourceFieldNos) throws Exception {
		FileInputStream fis = new FileInputStream(sourceFileName);  
		Workbook wb = WorkbookFactory.create(fis); 
		Sheet sheet = wb.getSheetAt(Integer.parseInt(sourceSheetOrder)-1); 
		int rowNumbers = sheet.getLastRowNum() + 1;
		List list = new ArrayList();
		for (int row = 0; row < rowNumbers; row++) {
			if(row < Integer.parseInt(sourceBeginLines)-1) {
				continue;
			}
			Row r = sheet.getRow(row);
			String[] str = new String[sourceFieldNos.length];
			boolean hasValue = true;
			for (int col = 0; col < sourceFieldNos.length; col++) {
				Cell cell = r.getCell(Integer.parseInt(sourceFieldNos[col])-1);
				if(Cell.CELL_TYPE_BLANK == cell.getCellType()) {
					hasValue = false;
					break;
				}
				String str1 = col==0 ? "" : "\t";
				if( cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					str1 += NumberToTextConverter.toText(cell.getNumericCellValue());					
				} else {
					
					str1 += cell.getStringCellValue();
				}
				System.out.print(str1);
			}
			if(hasValue) {
				list.add(str);
			}
			System.out.println();
		}
		return list;
	}

	private static void writeData(String targetFileName, String targetSheetName, String targetBeginLines, List data) throws Exception {
//		JSONArray ja = (JSONArray)jsonObject.get("data");
//		System.out.println(((JSONObject)ja.get(1)).get("name"));
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(targetFileName)); 
        
      //填写表格
        XSSFSheet sheet = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        for (int i = 0; i < data.size(); i++) {
        	sheet = workbook.getSheetAt(i); 
        	row = sheet.getRow(1); 
        	cell = row.getCell(0);
        	cell.setCellValue("统计时间：");
        	row = sheet.getRow(2); 
        	cell = row.getCell(2);
            cell.setCellValue("");
            cell = row.getCell(4);
            cell.setCellValue("");
            cell = row.getCell(6);
            cell.setCellValue("");
            cell = row.getCell(8);
            cell.setCellValue("");
            cell = row.getCell(9);
            cell.setCellValue("");
            cell = row.getCell(11);
            cell.setCellValue("");
            cell = row.getCell(12);
            cell.setCellValue("");
            for (int j = 0; j <288; j++) {
            	 row = sheet.getRow(1); 
            	 for (int j1 = 0,k=1; j1 < 45; j1++) {
            		 if(j1%5==0){
            			 continue;
            		 }
            		 cell = row.getCell(j1);
            		 cell.setCellValue(Double.parseDouble("1"));
            		 k++;
            	 }
            }
		}
        
        //写入文件
        FileOutputStream out = null; 
        try { 
            out = new FileOutputStream(targetFileName); 
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

	}

	private static String getConf() throws Exception {
		String jsonStr = "";
            Reader reader = new InputStreamReader(OrderCollector.class.getResourceAsStream("conf.json"),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
	}
}
