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
 * @Date 2013-11-28 ����10:17:36
 * @Version 1.0  zhangchunhua.co create
 * @CopyRight (c) 2013 �����������ϵͳ���޹�˾ 
 * @Description 
 */
public class InformixTest {
	private static Logger logUtil = Logger.getLogger(JdbcFactory.class); // ��־��¼��
	
	public static void main(String[] args) {
		String sQueryDevSQL4 = "select serno from wd_casestart where serno not in(select serno from wd_currdeal_case )";
		String sSernoInfo4 = getDeleteInfo(sQueryDevSQL4, "serno");
		if(!"".equals(sSernoInfo4)){
			logUtil.info("��ǰ�����账������!");
			//String sUpdateWdCurrDateSQL4 = " UPDATE wd_casestart SET stepdate5='"+sCurrDate+"',steptime5='"+sCurrTime+"' WHERE serno in ("+sSernoInfo4+") ";
			//String sMoverWdSQL4 = "INSERT INTO wl_casestart SELECT * FROM wd_casestart WHERE serno in ("+sSernoInfo4+") ";
			String sDeleteWdSQL4 = "DELETE FROM wd_casestart WHERE serno in ("+sSernoInfo4+") ";
	        updateWdInfo("", "", sDeleteWdSQL4, "");
		}
		long lDeal1Step2Time = System.currentTimeMillis();
	}
	
	private static String getDeleteInfo(String sQuerySQL,String sFindCon){
		//logUtil.info("��ѯ��ǰ�������������ݵ�SQLΪ:"+sQuerySQL);
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
			logUtil.error("��ȡ�����������쳣:",e);
		}
		if(!"".equals(sReturnStr.toString())){
			sReturnStr.append("''");
		}
		logUtil.info("��ȡ������������������Ϊ:["+lQueryList.size()+"]");
	    return sReturnStr.toString();
	}
	
	private static void updateWdInfo(String sUpdateWdCurrDateSQL,String sMoverWdSQL,String sDeleteWdSQL,String sDeleteCurrDealCaseSQL ){
	    logUtil.info("��ʼ���¹�������!");
		try{
		   logUtil.info("���µ�ǰ������������ʱ��SQLΪ:"+sUpdateWdCurrDateSQL);
		   if(!"".equals(sUpdateWdCurrDateSQL)){
			   JdbcFactory.update(sUpdateWdCurrDateSQL);
		   }
		   logUtil.info("ִ������ת��SQLΪ:"+sMoverWdSQL);
		   if(!"".equals(sMoverWdSQL)){
			   JdbcFactory.update(sMoverWdSQL);
		   }
		   logUtil.info("ɾ����ǰ����������SQLΪ:"+sDeleteWdSQL);
		   if(!"".equals(sDeleteWdSQL)){
			   JdbcFactory.update(sDeleteWdSQL);
		   }
		   logUtil.info("ɾ��������ת��SQLΪ:"+sDeleteCurrDealCaseSQL);
		   if(!"".equals(sDeleteCurrDealCaseSQL)){
			   JdbcFactory.update(sDeleteCurrDealCaseSQL);
		   }
		}catch (Exception e) {
				logUtil.error("����ǰ����豸�ڹ����������쳣:",e);
		}
		logUtil.info("���¹������ݽ���!");
	}
	
}
