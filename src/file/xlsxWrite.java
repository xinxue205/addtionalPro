/**
 * 
 */
package file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import server.util.DBBaseUtil;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-9-5 上午11:18:48
 * @Description
 * @version 1.0 Shawn create
 */
public class xlsxWrite {
	private static DBBaseUtil dbBaseUtil = null; // 数据库基本操作类
	private static JdbcTemplate jdbcTemplate = null;
	static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat dateFormater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static DecimalFormat df = new DecimalFormat("#0.00");
	static String apArray[] = {"00","01","02","03","04","05","06","07","08"};
	static String today =dateFormater.format(new Date());// "2014-09-10";
	static String deadTime = "170000";
	static String  now = dateFormater1.format(new Date());
	
	final static int startRowNo = 199;
	public static void main(String args[]) throws Exception {
		dbBaseUtil = new DBBaseUtil();
		jdbcTemplate = dbBaseUtil == null ? null : dbBaseUtil.getJdbcTemplate();
		String path1 = "H:/ATM可疑交易统计报表v1.1.xlsx";
		String path2 = "H:/ATM可疑交易统计报表v1.1_"+today+".xlsx";
		createReport(path1, path2);
	}
	
	/**
	 *  获取详细数据
	 * @param transType 类别 01-取款冲正；02-存款超时
	 * @param ap ap编号 00-全行
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param days 天数
	 * @return
	 */
	private static String[][] getResultArray(String transType ,String ap, String beginDate, String endDate, int days) {
		String resultArray[][] = new String[288][3];
		String dataSqlS = "select b.time_sec,a.trans_count,a.suc_count from time_section b left join (select spare_str1,sum(trans_count)/"+days+" trans_count,sum(suc_count)/"+days+" suc_count " +
					"from trans_monitor_doubtaccount_tmp where type_code='"+transType+"' and report_date>='"+beginDate+"' and report_date<='"+endDate+"' group by spare_str1) a on a.spare_str1=b.time_sec order by b.serial";
		if(!"00".equals(ap)){
			dataSqlS = "select b.time_sec,a.trans_count,a.suc_count from time_section b left join (select spare_str1,sum(trans_count)/"+days+" trans_count,sum(suc_count)/"+days+" suc_count " +
					"from trans_monitor_doubtaccount_tmp where type_code='"+transType+"' and in_sys_code='"+ap+"' and  report_date>='"+beginDate+"' and report_date<='"+endDate+"' group by spare_str1) a on a.spare_str1=b.time_sec order by b.serial";
		}
		List lYesterdayData = jdbcTemplate.queryForList(dataSqlS);
		for (int i = 0; i < resultArray.length; i++) {
			Map map = (Map) lYesterdayData.get(i);
			String time_sec = (String) map.get("time_sec");
			BigDecimal trans_count = (BigDecimal) map.get("trans_count");
			BigDecimal suc_count = (BigDecimal) map.get("suc_count");
			resultArray[i][0] = time_sec;
			resultArray[i][1] = suc_count==null?"0":suc_count.intValue()+"";
			resultArray[i][2] = (trans_count==null||trans_count.intValue()==0)?"0":df.format(suc_count.intValue()*100D/trans_count.intValue());
		}
		return resultArray;
	}
	
	/**
	 *  获取汇总数据
	 * @param transType 类别 01-取款冲正；02-存款超时
	 * @param ap ap编号 00-全行
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param days 天数
	 * @return
	 */
	private static String getSummary(String transType , String beginDate, String endDate, int days) {
		String dataSqlS = "select sum(trans_count)/"+days+" trans_count,sum(suc_count)/"+days+" suc_count from trans_monitor_doubtaccount_tmp where type_code='"+transType+"' and report_date>='"+beginDate+"' and report_date<='"+endDate+"' and  spare_str1<='"+deadTime+"'";
		Map map = jdbcTemplate.queryForMap(dataSqlS);
		BigDecimal trans_count = (BigDecimal)map.get("trans_count");
		BigDecimal suc_count = (BigDecimal)map.get("suc_count");
		String returnS = (suc_count==null||suc_count.doubleValue()==0)?"0|0":suc_count.intValue()+"|"+df.format(suc_count.intValue()*100D/trans_count.intValue());
		return returnS;
	}

	public static void createReport(String oldFilePath,String newFilePath) throws ParseException {   
        try { 
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(oldFilePath)); 
            String yesterday = dateFormater.format(new Date(dateFormater.parse(today).getTime()-24*3600*1000));
            String weekDay = dateFormater.format(new Date(dateFormater.parse(today).getTime()-7*24*3600*1000));
            String monthDay = dateFormater.format(new Date(dateFormater.parse(today).getTime()-30*24*3600*1000L));
            
            //获取数据
            String qkczTodaySumS = getSummary("01",today,today,1);	//汇总数据 取款冲正
            String qkczYesdaySumS = getSummary("01",yesterday,yesterday,1);
            String qkczWeekDaySumS = getSummary("01",weekDay,weekDay,1);
            String qkczWeekAvgSumS = getSummary("01",weekDay,yesterday,7);
            String qkczMonthSumS = getSummary("01",monthDay,yesterday,30);
            String ckcsTodaySumS = getSummary("02",today,today,1);	//存款超时
            String ckcsYesdaySumS = getSummary("02",yesterday,yesterday,1);
            String ckcsWeekDaySumS = getSummary("02",weekDay,weekDay,1);
            String ckcsWeekAvgSumS = getSummary("02",weekDay,yesterday,7);
            String ckcsMonthSumS = getSummary("02",monthDay,yesterday,30);
            String tmpSumDataA[][] = {{qkczTodaySumS,qkczYesdaySumS},{qkczTodaySumS,qkczWeekDaySumS},{qkczTodaySumS,qkczWeekAvgSumS},{qkczTodaySumS,qkczMonthSumS},
            			{ckcsTodaySumS,ckcsYesdaySumS},{ckcsTodaySumS,ckcsWeekDaySumS},{ckcsTodaySumS,ckcsWeekAvgSumS},{ckcsTodaySumS,ckcsMonthSumS}};
            String sumDataA[][] = new String[8][6];
            for (int i1 = 0; i1 < sumDataA.length; i1++) {
            	int currCount = Integer.parseInt(tmpSumDataA[i1][0].split("\\|")[0]);
            	double currRate = Double.parseDouble(tmpSumDataA[i1][0].split("\\|")[1]);
            	int compCount = Integer.parseInt(tmpSumDataA[i1][1].split("\\|")[0]);
            	double compRate = Double.parseDouble(tmpSumDataA[i1][1].split("\\|")[1]);
            	sumDataA[i1][0]= currCount+"";
            	sumDataA[i1][1]= currRate+"%";
            	sumDataA[i1][2]= currCount>compCount?"增加":"减少";
            	sumDataA[i1][3]= Math.abs(currCount-compCount)+"";
            	sumDataA[i1][4]= currRate>compRate?"增长":"降低";
            	sumDataA[i1][5]=  df.format(Math.abs(currRate-compRate))+"%";
			}
            
            String[][][] qkczTodayDataA = new String[9][288][3];	//表格数据取款冲正 9-ap数; 288-时段数；3-时段/可疑笔数/占比
            String[][][] qkczYesterdayA = new String[9][288][3];
            String[][][] qkczWeekAvgA = new String[9][288][3];
            String[][][] qkczWeekDayA = new String[9][288][3];
            String[][][] qkczMonthArryA = new String[9][288][3];
            String[][][] ckcsTodayDataA = new String[9][288][3]; 	//存款超时
            String[][][] ckcsYesterdayA = new String[9][288][3];
            String[][][] ckcsWeekAvgA = new String[9][288][3];
            String[][][] ckcsWeekDayA = new String[9][288][3];
            String[][][] ckcsMonthArryA = new String[9][288][3];
            for (int i = 0; i < qkczTodayDataA.length; i++) {
            	qkczTodayDataA[i] = getResultArray("01",apArray[i],today,today,1);
            	qkczYesterdayA[i] =  getResultArray("01",apArray[i],yesterday,yesterday,1);
            	qkczWeekAvgA[i] =  getResultArray("01",apArray[i],weekDay,yesterday,7);
            	qkczWeekDayA[i] =  getResultArray("01",apArray[i],weekDay,weekDay,1);
            	qkczMonthArryA[i] =  getResultArray("01",apArray[i],monthDay,yesterday,30);
            	ckcsTodayDataA[i] = getResultArray("02",apArray[i],today,today,1);
            	ckcsYesterdayA[i] =  getResultArray("02",apArray[i],yesterday,yesterday,1);
            	ckcsWeekAvgA[i] =  getResultArray("02",apArray[i],weekDay,yesterday,7);
            	ckcsWeekDayA[i] =  getResultArray("02",apArray[i],weekDay,weekDay,1);
            	ckcsMonthArryA[i] =  getResultArray("02",apArray[i],monthDay,yesterday,30);
            }
            //1-sheet;2-当前/对比数据；3-ap；4-时段；5-值
            String[][][][][] tmpDataA = {{qkczTodayDataA,qkczYesterdayA},{qkczTodayDataA,qkczWeekAvgA},{qkczTodayDataA,qkczWeekDayA},{qkczTodayDataA,qkczMonthArryA},
            		{ckcsTodayDataA,ckcsYesterdayA},{ckcsTodayDataA,ckcsWeekAvgA},{ckcsTodayDataA,ckcsWeekDayA},{ckcsTodayDataA,ckcsMonthArryA}};
            String sheetData[][][] = new String[8][288][45];//8-sheet数；288-时段数；37-时段/9ap*（当天笔数/对比笔数/当天占比/对比占比）
            for (int i = 0; i < sheetData.length; i++) {
            	for (int j = 0; j < sheetData[i].length; j++) {
            		sheetData[i][j][0]=tmpDataA[i][0][0][j][0];//时段
            		sheetData[i][j][1]=tmpDataA[i][0][0][j][1];//全行当天笔数
            		sheetData[i][j][2]=tmpDataA[i][1][0][j][1];//全行对比笔数
            		sheetData[i][j][3]=tmpDataA[i][0][0][j][2];//全行当天占比 
            		sheetData[i][j][4]=tmpDataA[i][1][0][j][2];//全行对比占比
            		sheetData[i][j][5]=tmpDataA[i][0][1][j][1];//ap01当天笔数
            		sheetData[i][j][6]=tmpDataA[i][1][1][j][1];//ap01对比笔数
            		sheetData[i][j][7]=tmpDataA[i][0][1][j][2];//ap01当天占比 
            		sheetData[i][j][8]=tmpDataA[i][1][1][j][2];//ap01对比占比
            		sheetData[i][j][9]=tmpDataA[i][0][2][j][1];
            		sheetData[i][j][10]=tmpDataA[i][1][2][j][1];
            		sheetData[i][j][11]=tmpDataA[i][0][2][j][2];
            		sheetData[i][j][12]=tmpDataA[i][1][2][j][2];
            		sheetData[i][j][13]=tmpDataA[i][0][3][j][1];
            		sheetData[i][j][14]=tmpDataA[i][1][3][j][1];
            		sheetData[i][j][15]=tmpDataA[i][0][3][j][2];
            		sheetData[i][j][16]=tmpDataA[i][1][3][j][2];
            		sheetData[i][j][17]=tmpDataA[i][0][4][j][1];
            		sheetData[i][j][18]=tmpDataA[i][1][4][j][1];
            		sheetData[i][j][19]=tmpDataA[i][0][4][j][2];
            		sheetData[i][j][20]=tmpDataA[i][1][4][j][2];
            		sheetData[i][j][21]=tmpDataA[i][0][5][j][1];
            		sheetData[i][j][22]=tmpDataA[i][1][5][j][1];
            		sheetData[i][j][23]=tmpDataA[i][0][5][j][2];
            		sheetData[i][j][24]=tmpDataA[i][1][5][j][2];
            		sheetData[i][j][25]=tmpDataA[i][0][6][j][1];
            		sheetData[i][j][26]=tmpDataA[i][1][6][j][1];
            		sheetData[i][j][27]=tmpDataA[i][0][6][j][2];
            		sheetData[i][j][28]=tmpDataA[i][1][6][j][2];
            		sheetData[i][j][29]=tmpDataA[i][0][7][j][1];
            		sheetData[i][j][30]=tmpDataA[i][1][7][j][1];
            		sheetData[i][j][31]=tmpDataA[i][0][7][j][2];
            		sheetData[i][j][32]=tmpDataA[i][1][7][j][2];
            		sheetData[i][j][33]=tmpDataA[i][0][8][j][1];
            		sheetData[i][j][34]=tmpDataA[i][1][8][j][1];
            		sheetData[i][j][35]=tmpDataA[i][0][8][j][2];
            		sheetData[i][j][36]=tmpDataA[i][1][8][j][2];
				}
			}
            
            //填写表格
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;
            for (int i = 0; i < sheetData.length; i++) {
            	sheet = workbook.getSheetAt(i); 
            	row = sheet.getRow(1); 
            	cell = row.getCell(0);
            	cell.setCellValue("统计时间："+now);
            	row = sheet.getRow(2); 
            	cell = row.getCell(2);
                cell.setCellValue(deadTime.substring(0,2)+":"+deadTime.substring(2,4));
                cell = row.getCell(4);
                cell.setCellValue(sumDataA[i][0]);
                cell = row.getCell(6);
                cell.setCellValue(sumDataA[i][1]);
                cell = row.getCell(8);
                cell.setCellValue(sumDataA[i][2]);
                cell = row.getCell(9);
                cell.setCellValue(sumDataA[i][3]);
                cell = row.getCell(11);
                cell.setCellValue(sumDataA[i][4]);
                cell = row.getCell(12);
                cell.setCellValue(sumDataA[i][5]);
                for (int j = 0; j <288; j++) {
                	 row = sheet.getRow(startRowNo+j); 
                	 for (int j1 = 0,k=1; j1 < 45; j1++) {
                		 if(j1%5==0){
                			 continue;
                		 }
                		 cell = row.getCell(j1);
                		 cell.setCellValue(Double.parseDouble(sheetData[i][j][k]));
                		 k++;
                	 }
                }
			}
            
            //写入文件
            FileOutputStream out = null; 
            try { 
                out = new FileOutputStream(newFilePath); 
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
}
