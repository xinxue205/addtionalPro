package annie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class OrderCollector {
	
	static String sourceFile = "C:\\Users\\admin\\Documents\\WeChat Files\\wxid_9es2oe81viq251\\FileStorage\\File\\2021-01\\单号20210117(1).xls";
	static String targetDir = "C:\\Users\\admin\\Documents\\WeChat Files\\wxid_9es2oe81viq251\\FileStorage\\File\\2021-01\\";
	static int sourceBeginLine = 2;
	static String[] targetHeader = {"收件人姓名", "收件人电话", "快递单号", "单品名称", "快递公司"};
	static Map<String, Integer> map = new HashMap<String, Integer>();
	static List<String[]>[] allData = new ArrayList[9];
	
	public static void main(String[] args) throws Exception {
		init();
		readData(sourceFile); 
		exportData();
		System.out.println("此次处理完毕！");
	}

	private static void exportData() throws Exception {
		for (Entry<String, Integer> e : map.entrySet()) {
			List<String[]> data = allData[e.getValue()];
			if(data.size()==0) {
				System.out.println(e.getKey()+" 快递无订单");
				continue;
			}
			XSSFWorkbook workbook = new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet();
            XSSFRow row = sheet.createRow(0); 
            XSSFCell cell = row.createCell(0);
        	cell.setCellValue("收件人姓名");
        	cell = row.createCell(1);
            cell.setCellValue("收件人电话");
            cell = row.createCell(2);
            cell.setCellValue("快递单号");
            cell = row.createCell(3);
            cell.setCellValue("商品名称");
            int startRowNo = 1;
			for (int i = 0; i < data.size(); i++) {
				String[] currData = data.get(i);
				row = sheet.createRow(startRowNo+i); 
				cell = row.createCell(0);
	        	cell.setCellValue(currData[0]);
	        	cell = row.createCell(1);
	        	cell.setCellValue(currData[1]);
	        	cell = row.createCell(2);
	        	cell.setCellValue(currData[2]);
	        	cell = row.createCell(3);
	        	cell.setCellValue(currData[3]);
			}
			
			//写入文件
            FileOutputStream out = null; 
            try {
            	new File(targetDir).mkdirs();
                out = new FileOutputStream(targetDir+File.separator+e.getKey()+".xls"); 
                workbook.write(out); 
            } catch (IOException e1) { 
                e1.printStackTrace(); 
            } finally { 
                try { 
                    out.close(); 
                } catch (IOException e1) { 
                    e1.printStackTrace(); 
                } 
            }
    		System.out.println(e.getKey()+" 快递处理完（共"+ data.size() +"条数据）！");
		}
	}
	
	private static void init() {
		System.out.println("注意：1.原数据表格在文件的第一");
		map.put("顺丰", 0);
		map.put("韵达", 1);
		map.put("中通", 2);
		map.put("圆通", 3);
		map.put("申通", 4);
		map.put("京东", 5);
		map.put("邮政", 6);
		map.put("百世", 7);
		map.put("其它", 8);
		allData[0] = new ArrayList<String[]>();
		allData[1] = new ArrayList<String[]>();
		allData[2] = new ArrayList<String[]>();
		allData[3] = new ArrayList<String[]>();
		allData[4] = new ArrayList<String[]>();
		allData[5] = new ArrayList<String[]>();
		allData[6] = new ArrayList<String[]>();
		allData[7] = new ArrayList<String[]>();
		allData[8] = new ArrayList<String[]>();
	}

	private static void readData(String sourceFile) throws Exception {
		FileInputStream fis = new FileInputStream(sourceFile);  
		Workbook wb = WorkbookFactory.create(fis); 
		Sheet sheet = wb.getSheetAt(0); 
		System.out.println("当前处理的表格名："+sheet.getSheetName());
		int rowNumbers = sheet.getLastRowNum() + 1;
		List list = new ArrayList();
		for (int row = 0; row < rowNumbers; row++) {
			if(row < sourceBeginLine-1) {
				continue;
			}
			Row r = sheet.getRow(row);
			if(Cell.CELL_TYPE_BLANK == r.getCell(0).getCellType()) {
				continue;
			}
			
			String[] data = new String[5];
			
			for (int col = 0; col < targetHeader.length; col++) {
				Cell cell = r.getCell(col);
				
				String str1 = col==0 ? "" : "\t";
				String val = "";
				if(cell == null) {
					val = "其它";
				} else {
					if( cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						val = NumberToTextConverter.toText(cell.getNumericCellValue());					
					} else {
						val = cell.getStringCellValue();
					}
				}
				str1 += val;
				
				data[col] = val;
			}
			String kdm = data[4];
			List kdxx = allData[map.get(kdm) == null ? 7 : map.get(kdm)];
			kdxx.add(data);
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
