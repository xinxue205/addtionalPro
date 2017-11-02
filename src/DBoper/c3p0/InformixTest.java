package DBoper.c3p0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.informix.jdbc.IfxDriver;

/**
 *
 * @Author zhangchunhua.co
 * @Date 2013-11-28 上午10:17:36
 * @Version 1.0  zhangchunhua.co create
 * @CopyRight (c) 2013 广州南天电脑系统有限公司 
 * @Description 
 */
public class InformixTest {
	private static Logger logUtil = Logger.getLogger(JdbcFactory.class); // 日志记录器
	
	public static void main(String[] args) {
		String sQueryDevSQL4 = "select serno from wd_casestart where serno not in(select serno from wd_currdeal_case )";
		String sSernoInfo4 = getDeleteInfo(sQueryDevSQL4, "serno");
		if(!"".equals(sSernoInfo4)){
			logUtil.info("当前存在需处理数据!");
			//String sUpdateWdCurrDateSQL4 = " UPDATE wd_casestart SET stepdate5='"+sCurrDate+"',steptime5='"+sCurrTime+"' WHERE serno in ("+sSernoInfo4+") ";
			//String sMoverWdSQL4 = "INSERT INTO wl_casestart SELECT * FROM wd_casestart WHERE serno in ("+sSernoInfo4+") ";
			String sDeleteWdSQL4 = "DELETE FROM wd_casestart WHERE serno in ("+sSernoInfo4+") ";
	        updateWdInfo("", "", sDeleteWdSQL4, "");
		}
		long lDeal1Step2Time = System.currentTimeMillis();
	}
	
	private static String getDeleteInfo(String sQuerySQL,String sFindCon){
		//logUtil.info("查询当前满足条件的数据的SQL为:"+sQuerySQL);
		StringBuffer sReturnStr = new StringBuffer("");
		List lQueryList = new ArrayList<Map<String,Object>>();
		try{
			lQueryList = JdbcFactory.queryForList(sQuerySQL);
			for (int i = 0; i < lQueryList.size(); i++) {
				Map<String,Object> mResultMap = (Map<String, Object>) lQueryList.get(i);
				String sFindResult = mResultMap.get(sFindCon)==null?"":mResultMap.get(sFindCon).toString().trim();
				sReturnStr.append("'"+sFindResult+"',");
			}
		}catch (Exception e) {
			logUtil.error("获取待处理数据异常:",e);
		}
		if(!"".equals(sReturnStr.toString())){
			sReturnStr.append("''");
		}
		logUtil.info("获取满足条件的数据条数为:["+lQueryList.size()+"]");
	    return sReturnStr.toString();
	}
	
	private static void updateWdInfo(String sUpdateWdCurrDateSQL,String sMoverWdSQL,String sDeleteWdSQL,String sDeleteCurrDealCaseSQL ){
	    logUtil.info("开始更新工单数据!");
		try{
		   logUtil.info("更新当前工单表工单结束时间SQL为:"+sUpdateWdCurrDateSQL);
		   if(!"".equals(sUpdateWdCurrDateSQL)){
			   JdbcFactory.update(sUpdateWdCurrDateSQL);
		   }
		   logUtil.info("执行数据转移SQL为:"+sMoverWdSQL);
		   if(!"".equals(sMoverWdSQL)){
			   JdbcFactory.update(sMoverWdSQL);
		   }
		   logUtil.info("删除当前工单表数据SQL为:"+sDeleteWdSQL);
		   if(!"".equals(sDeleteWdSQL)){
			   JdbcFactory.update(sDeleteWdSQL);
		   }
		   logUtil.info("删除工单流转表SQL为:"+sDeleteCurrDealCaseSQL);
		   if(!"".equals(sDeleteCurrDealCaseSQL)){
			   JdbcFactory.update(sDeleteCurrDealCaseSQL);
		   }
		}catch (Exception e) {
				logUtil.error("处理当前库存设备在工单表数据异常:",e);
		}
		logUtil.info("更新工单数据结束!");
	}
	
}
