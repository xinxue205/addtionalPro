package DBoper.c3p0;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import DBoper.c3p0.DB;
import DBoper.c3p0.DBFactory;

import jxl.Sheet;
import jxl.Workbook;

public class OpertTest {
	private static Log log = LogFactory.getLog(OpertTest.class);
	private Map outMsgMap = new HashMap();
	private Map mContacatLevel = new HashMap();
	private Map mFaultType = new HashMap();
	private static LobHandler lobHandler = new DefaultLobHandler();
	
	public static void main(String[] args) {
		DB.jdbcTemplate.update("update t_role_entity set start_time = ?, status=?"
				+ " where role_entity_name = ?", 
				new Object[]{null, "RUNNING", "wxx-test"});
	}
	
	public static void main2(String[] args) {
		String sql = "update A_TEST01 set QXLX = ? where id=1"; // 这里用问号        
		int map = DB.jdbcTemplate.update(sql, new Object[]{new Date()}, new int[]{java.sql.Types.DATE});
	}
	
	public static void main1(String args[]) throws Exception{
		/*String sql = "insert into T_TEMPLATE (ID, TEMPLATE_NAME, TEMPLATE_TYPE, TEMPLATE_FUNC, TEMPALTE_ARGS, TEMPLATE_XML, TEMPLATE_DESC)"
				+ " values (?, ?, ?, null, null, ?, '作业模型的模板')";

		int tId = 3;
		String name = "dzmodel01";
		int type=1;
		String xml = "";
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><transformation><info><name></name><objectid></objectid><description/><extended_description/><trans_version/><trans_type>Normal</trans_type><trans_status>0</trans_status><directory></directory><parameters></parameters><log><trans-log-table><connection>${DB_CONN}</connection><schema/><table>T_TRANS_LOG</table><size_limit_lines>100000</size_limit_lines><interval>5</interval><timeout_days>10</timeout_days><field><id>ID_BATCH</id><enabled>Y</enabled><name>ID_BATCH</name></field><field><id>CHANNEL_ID</id><enabled>Y</enabled><name>CHANNEL_ID</name></field><field><id>TRANSNAME</id><enabled>Y</enabled><name>TRANSNAME</name></field><field><id>STATUS</id><enabled>Y</enabled><name>STATUS</name></field><field><id>LINES_READ</id><enabled>Y</enabled><name>LINES_READ</name><subject>${DZZWINSERTUPDATE}</subject></field><field><id>LINES_WRITTEN</id><enabled>Y</enabled><name>LINES_WRITTEN</name><subject>${DZZWINSERTUPDATE}</subject></field><field><id>LINES_UPDATED</id><enabled>Y</enabled><name>LINES_UPDATED</name><subject>${DZZWINSERTUPDATE}</subject></field><field><id>LINES_INPUT</id><enabled>Y</enabled><name>LINES_INPUT</name><subject>${DZZWINSERTUPDATE}</subject></field><field><id>LINES_OUTPUT</id><enabled>Y</enabled><name>LINES_OUTPUT</name><subject>${DZZWINSERTUPDATE}</subject></field><field><id>LINES_REJECTED</id><enabled>Y</enabled><name>LINES_REJECTED</name><subject>${DZZWINSERTUPDATE}</subject></field><field><id>ERRORS</id><enabled>Y</enabled><name>ERRORS</name></field><field><id>STARTDATE</id><enabled>Y</enabled><name>STARTDATE</name></field><field><id>ENDDATE</id><enabled>Y</enabled><name>ENDDATE</name></field><field><id>LOGDATE</id><enabled>Y</enabled><name>LOGDATE</name></field><field><id>DEPDATE</id><enabled>Y</enabled><name>DEPDATE</name></field><field><id>REPLAYDATE</id><enabled>Y</enabled><name>REPLAYDATE</name></field><field><id>LOG_FIELD</id><enabled>Y</enabled><name>LOG_FIELD</name></field><field><id>EXECUTING_SERVER</id><enabled>Y</enabled><name>EXECUTING_SERVER</name></field><field><id>EXECUTING_USER</id><enabled>Y</enabled><name>EXECUTING_USER</name></field><field><id>CLIENT</id><enabled>Y</enabled><name>CLIENT</name></field></trans-log-table><perf-log-table><connection/><schema/><table/><interval/><timeout_days/><field><id>ID_BATCH</id><enabled>Y</enabled><name>ID_BATCH</name></field><field><id>SEQ_NR</id><enabled>Y</enabled><name>SEQ_NR</name></field><field><id>LOGDATE</id><enabled>Y</enabled><name>LOGDATE</name></field><field><id>TRANSNAME</id><enabled>Y</enabled><name>TRANSNAME</name></field><field><id>STEPNAME</id><enabled>Y</enabled><name>STEPNAME</name></field><field><id>STEP_COPY</id><enabled>Y</enabled><name>STEP_COPY</name></field><field><id>LINES_READ</id><enabled>Y</enabled><name>LINES_READ</name></field><field><id>LINES_WRITTEN</id><enabled>Y</enabled><name>LINES_WRITTEN</name></field><field><id>LINES_UPDATED</id><enabled>Y</enabled><name>LINES_UPDATED</name></field><field><id>LINES_INPUT</id><enabled>Y</enabled><name>LINES_INPUT</name></field><field><id>LINES_OUTPUT</id><enabled>Y</enabled><name>LINES_OUTPUT</name></field><field><id>LINES_REJECTED</id><enabled>Y</enabled><name>LINES_REJECTED</name></field><field><id>ERRORS</id><enabled>Y</enabled><name>ERRORS</name></field><field><id>INPUT_BUFFER_ROWS</id><enabled>Y</enabled><name>INPUT_BUFFER_ROWS</name></field><field><id>OUTPUT_BUFFER_ROWS</id><enabled>Y</enabled><name>OUTPUT_BUFFER_ROWS</name></field></perf-log-table><channel-log-table><connection/><schema/><table>T_JOB_CHANNEL_LOG</table><timeout_days/><field><id>ID_BATCH</id><enabled>Y</enabled><name>ID_BATCH</name></field><field><id>CHANNEL_ID</id><enabled>Y</enabled><name>CHANNEL_ID</name></field><field><id>LOG_DATE</id><enabled>Y</enabled><name>LOG_DATE</name></field><field><id>LOGGING_OBJECT_TYPE</id><enabled>Y</enabled><name>LOGGING_OBJECT_TYPE</name></field><field><id>OBJECT_NAME</id><enabled>Y</enabled><name>OBJECT_NAME</name></field><field><id>OBJECT_COPY</id><enabled>Y</enabled><name>OBJECT_COPY</name></field><field><id>REPOSITORY_DIRECTORY</id><enabled>Y</enabled><name>REPOSITORY_DIRECTORY</name></field><field><id>FILENAME</id><enabled>Y</enabled><name>FILENAME</name></field><field><id>OBJECT_ID</id><enabled>Y</enabled><name>OBJECT_ID</name></field><field><id>OBJECT_REVISION</id><enabled>Y</enabled><name>OBJECT_REVISION</name></field><field><id>PARENT_CHANNEL_ID</id><enabled>Y</enabled><name>PARENT_CHANNEL_ID</name></field><field><id>ROOT_CHANNEL_ID</id><enabled>Y</enabled><name>ROOT_CHANNEL_ID</name></field></channel-log-table><step-log-table><connection/><schema/><table>T_TRANS_STEP_LOG</table><timeout_days/><field><id>ID_BATCH</id><enabled>Y</enabled><name>ID_BATCH</name></field><field><id>CHANNEL_ID</id><enabled>Y</enabled><name>CHANNEL_ID</name></field><field><id>LOG_DATE</id><enabled>Y</enabled><name>LOG_DATE</name></field><field><id>TRANSNAME</id><enabled>Y</enabled><name>TRANSNAME</name></field><field><id>STEPNAME</id><enabled>Y</enabled><name>STEPNAME</name></field><field><id>STEP_COPY</id><enabled>Y</enabled><name>STEP_COPY</name></field><field><id>LINES_READ</id><enabled>Y</enabled><name>LINES_READ</name></field><field><id>LINES_WRITTEN</id><enabled>Y</enabled><name>LINES_WRITTEN</name></field><field><id>LINES_UPDATED</id><enabled>Y</enabled><name>LINES_UPDATED</name></field><field><id>LINES_INPUT</id><enabled>Y</enabled><name>LINES_INPUT</name></field><field><id>LINES_OUTPUT</id><enabled>Y</enabled><name>LINES_OUTPUT</name></field><field><id>LINES_REJECTED</id><enabled>Y</enabled><name>LINES_REJECTED</name></field><field><id>ERRORS</id><enabled>Y</enabled><name>ERRORS</name></field><field><id>LOG_FIELD</id><enabled>N</enabled><name>LOG_FIELD</name></field></step-log-table><metrics-log-table><connection/><schema/><table>T_TRANS_METRICS_LOG</table><timeout_days/><field><id>ID_BATCH</id><enabled>Y</enabled><name>ID_BATCH</name></field><field><id>CHANNEL_ID</id><enabled>Y</enabled><name>CHANNEL_ID</name></field><field><id>LOG_DATE</id><enabled>Y</enabled><name>LOG_DATE</name></field><field><id>METRICS_DATE</id><enabled>Y</enabled><name>METRICS_DATE</name></field><field><id>METRICS_CODE</id><enabled>Y</enabled><name>METRICS_CODE</name></field><field><id>METRICS_DESCRIPTION</id><enabled>Y</enabled><name>METRICS_DESCRIPTION</name></field><field><id>METRICS_SUBJECT</id><enabled>Y</enabled><name>METRICS_SUBJECT</name></field><field><id>METRICS_TYPE</id><enabled>Y</enabled><name>METRICS_TYPE</name></field><field><id>METRICS_VALUE</id><enabled>Y</enabled><name>METRICS_VALUE</name></field></metrics-log-table></log><maxdate><connection/><table/><field/><offset>0.0</offset><maxdiff>0.0</maxdiff></maxdate><size_rowset>10000</size_rowset><sleep_time_empty>50</sleep_time_empty><sleep_time_full>50</sleep_time_full><unique_connections>N</unique_connections><feedback_shown>Y</feedback_shown><feedback_size>50000</feedback_size><using_thread_priorities>Y</using_thread_priorities><shared_objects_file/><capture_step_performance>N</capture_step_performance><step_performance_capturing_delay>1000</step_performance_capturing_delay><step_performance_capturing_size_limit/><dependencies></dependencies><partitionschemas></partitionschemas><slaveservers></slaveservers><clusterschemas></clusterschemas><created_user/><created_date></created_date><modified_user></modified_user><modified_date></modified_date></info><notepads></notepads><order><hop><from>&#x83b7;&#x53d6;&#x64cd;&#x4f5c;&#x65f6;&#x95f4;</from><to>&#x589e;&#x52a0;&#x8d44;&#x6e90;&#x540d;&#x79f0;</to><enabled>Y</enabled></hop><hop><from>&#x589e;&#x52a0;&#x8d44;&#x6e90;&#x540d;&#x79f0;</from><to>&#x5199;&#x9519;&#x8bef;&#x65e5;&#x5fd7;&#x8868;</to><enabled>Y</enabled></hop><hop><from>&#x589e;&#x52a0;&#x8d44;&#x6e90;&#x540d;&#x79f0;&#x5e38;&#x91cf;</from><to>&#x66f4;&#x65b0;&#x62bd;&#x53d6;&#x65f6;&#x95f4;</to><enabled>Y</enabled></hop><hop><from>&#x5b57;&#x6bb5;&#x9009;&#x62e9; 2</from><to>&#x963b;&#x585e;&#x6570;&#x636e;</to><enabled>Y</enabled></hop><hop><from>&#x963b;&#x585e;&#x6570;&#x636e;</from><to>&#x589e;&#x52a0;&#x8d44;&#x6e90;&#x540d;&#x79f0;&#x5e38;&#x91cf;</to><enabled>Y</enabled></hop><hop><from>&#x5b57;&#x6bb5;&#x9009;&#x62e9;</from><to>&#x963b;&#x585e;&#x6570;&#x636e;</to><enabled>Y</enabled></hop><hop><from>&#x5199;&#x9519;&#x8bef;&#x65e5;&#x5fd7;&#x8868;</from><to>&#x5b57;&#x6bb5;&#x9009;&#x62e9; 2</to><enabled>Y</enabled></hop><hop><from>&#x83b7;&#x53d6;&#x6e90;&#x8868;&#x5f00;&#x59cb;&#x65f6;&#x95f4;</from><to>${DZZWTALBEINPUT}</to><enabled>Y</enabled></hop><hop><from>${DZZWTALBEINPUT}</from><to>&#x83b7;&#x53d6;&#x6279;&#x6b21;</to><enabled>Y</enabled></hop><hop><from>&#x83b7;&#x53d6;&#x6279;&#x6b21;</from><to>${DZZWINSERTUPDATE}</to><enabled>Y</enabled></hop><hop><from>${DZZWINSERTUPDATE}</from><to>&#x5b57;&#x6bb5;&#x9009;&#x62e9;</to><enabled>Y</enabled></hop><hop><from>${DZZWINSERTUPDATE}</from><to>&#x83b7;&#x53d6;&#x64cd;&#x4f5c;&#x65f6;&#x95f4;</to><enabled>Y</enabled></hop></order><step><name>${DZZWINSERTUPDATE}</name><type>InsertUpdate</type><description/><distribute>N</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><connection>${TGR_DB}</connection><commit>1000</commit><update_bypassed>N</update_bypassed><lookup><schema/><table>${TGR_TABLENAME}</table><key><name>${SRC_ID}</name><field>${TGR_ID}</field><condition>&#x3d;</condition><name2/></key><value><name>${TGR_ID}</name><rename>${SRC_ID}</rename><update>N</update></value><value><name>HCK_RKSJ</name><rename>D_SYSDATE</rename><update>N</update></value><value><name>HCK_GXSJ</name><rename>D_SYSDATE</rename><update>Y</update></value>	  --------------loopup for all table columns--------	  #foreach ($UPDATE_COL in $UPDATE_COLS)      <value><name>${UPDATE_COL.TGR_COL}</name><rename>${UPDATE_COL.SRC_COL}</rename><update>${UPDATE_COL.UPD_FLAG}</update></value>	  #end	  ---------------------------------------------------    </lookup><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>161</xloc><yloc>233</yloc><draw>Y</draw></GUI></step><step><name>${DZZWTALBEINPUT}</name><type>DzzwTableInputMeta</type><description/><distribute>N</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><connection>${SRC_DB}</connection><sql>SELECT ${SOURCE_COLUMNS}, &#x3f; AS D_SYSDATE, &#x3f; AS ZLCQ_KSSJ, SYSDATE - 1&#x2f;17280 AS ZLCQ_JSSJ&#xd;&#xa;  FROM ${SRC_TABLENAME} T&#xd;&#xa; WHERE ${MODIFIEDTIME} &#x3e; &#x3f;&#xd;&#xa;   AND ${MODIFIEDTIME} &#x3c;&#x3d; SYSDATE</sql><limit>0</limit><lookup>&#x83b7;&#x53d6;&#x6e90;&#x8868;&#x5f00;&#x59cb;&#x65f6;&#x95f4;</lookup><execute_each_row>Y</execute_each_row><variables_active>Y</variables_active><lazy_conversion_active>N</lazy_conversion_active><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>55</xloc><yloc>132</yloc><draw>Y</draw></GUI></step><step><name>&#x5199;&#x9519;&#x8bef;&#x65e5;&#x5fd7;&#x8868;</name><type>TableOutput</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><connection>${DB_CONN}</connection><schema/><table>B_KETTLE_ERR_LOG</table><commit>500</commit><truncate>N</truncate><ignore_errors>N</ignore_errors><use_batch>Y</use_batch><specify_fields>Y</specify_fields><partitioning_enabled>N</partitioning_enabled><partitioning_field/><partitioning_daily>N</partitioning_daily><partitioning_monthly>Y</partitioning_monthly><tablename_in_field>N</tablename_in_field><tablename_field/><tablename_in_table>Y</tablename_in_table><return_keys>N</return_keys><return_field/><fields><field><column_name>TABLE_NAME</column_name><stream_name>TABLE_NAME</stream_name></field><field><column_name>SYSTEMID</column_name><stream_name>${SRC_ID}</stream_name></field><field><column_name>ERR_MSG</column_name><stream_name>ERR_MSG</stream_name></field><field><column_name>OP_TIME</column_name><stream_name>OPTIME1</stream_name></field><field><column_name>CLPCH</column_name><stream_name>V_PC</stream_name></field></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>279</xloc><yloc>35</yloc><draw>Y</draw></GUI></step><step><name>&#x589e;&#x52a0;&#x8d44;&#x6e90;&#x540d;&#x79f0;</name><type>Constant</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><fields><field><name>TABLE_NAME</name><type>String</type><format/><currency/><decimal/><group/><nullif>${ZJ_ZYDM}</nullif><length>0</length><precision>0</precision><set_empty_string>N</set_empty_string></field></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>173</xloc><yloc>35</yloc><draw>Y</draw></GUI></step><step><name>&#x589e;&#x52a0;&#x8d44;&#x6e90;&#x540d;&#x79f0;&#x5e38;&#x91cf;</name><type>Constant</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><fields><field><name>TABLE_NAME</name><type>String</type><format/><currency/><decimal/><group/><nullif>${ZJ_ZYDM}</nullif><length>0</length><precision>0</precision><set_empty_string>N</set_empty_string></field></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>388</xloc><yloc>136</yloc><draw>Y</draw></GUI></step><step><name>&#x5b57;&#x6bb5;&#x9009;&#x62e9;</name><type>SelectValues</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><fields><field><name>ZLCQ_KSSJ</name><rename/><length>-2</length><precision>-2</precision></field><field><name>ZLCQ_JSSJ</name><rename/><length>-2</length><precision>-2</precision></field><select_unspecified>N</select_unspecified></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>273</xloc><yloc>235</yloc><draw>Y</draw></GUI></step><step><name>&#x5b57;&#x6bb5;&#x9009;&#x62e9; 2</name><type>SelectValues</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><fields><field><name>ZLCQ_KSSJ</name><rename/><length>-2</length><precision>-2</precision></field><field><name>ZLCQ_JSSJ</name><rename/><length>-2</length><precision>-2</precision></field><select_unspecified>N</select_unspecified></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>276</xloc><yloc>141</yloc><draw>Y</draw></GUI></step><step><name>&#x66f4;&#x65b0;&#x62bd;&#x53d6;&#x65f6;&#x95f4;</name><type>Update</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><connection>${DB_CONN}</connection><skip_lookup>N</skip_lookup><commit>100</commit><use_batch>N</use_batch><error_ignored>N</error_ignored><ignore_flag_field/><lookup><schema/><table>T_SJCL_ZDB</table><key><name>TABLE_NAME</name><field>ZYDM</field><condition>&#x3d;</condition><name2/></key><value><name>ZLCQ_KSSJ</name><rename>ZLCQ_KSSJ</rename></value><value><name>ZLCQ_JSSJ</name><rename>ZLCQ_JSSJ</rename></value></lookup><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>388</xloc><yloc>32</yloc><draw>Y</draw></GUI></step><step><name>&#x83b7;&#x53d6;&#x6279;&#x6b21;</name><type>SystemInfo</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><fields><field><name>V_PC</name><type>batch ID</type></field></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>56</xloc><yloc>234</yloc><draw>Y</draw></GUI></step><step><name>&#x83b7;&#x53d6;&#x64cd;&#x4f5c;&#x65f6;&#x95f4;</name><type>SystemInfo</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><fields><field><name>OPTIME1</name><type>system date &#x28;variable&#x29;</type></field></fields><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>173</xloc><yloc>133</yloc><draw>Y</draw></GUI></step><step><name>&#x83b7;&#x53d6;&#x6e90;&#x8868;&#x5f00;&#x59cb;&#x65f6;&#x95f4;</name><type>TableInput</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><connection>${DB_CONN}</connection><sql>SELECT SYSDATE AS D_SYSDATE, ZLCQ_JSSJ AS ZLCQ_KSSJ1, ZLCQ_JSSJ AS ZLCQ_KSSJ2 FROM T_SJCL_ZDB WHERE ZYDM &#x3d; &#x27;${ZJ_ZYDM}&#x27;</sql><limit>0</limit><lookup/><execute_each_row>N</execute_each_row><variables_active>N</variables_active><lazy_conversion_active>N</lazy_conversion_active><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>63</xloc><yloc>35</yloc><draw>Y</draw></GUI></step><step><name>&#x963b;&#x585e;&#x6570;&#x636e;</name><type>BlockingStep</type><description/><distribute>Y</distribute><custom_distribution/><copies>1</copies><partitioning><method>none</method><schema_name/></partitioning><pass_all_rows>N</pass_all_rows><directory>&#x25;&#x25;java.io.tmpdir&#x25;&#x25;</directory><prefix>block</prefix><cache_size>5000</cache_size><compress>Y</compress><cluster_schema/><remotesteps><input></input><output></output></remotesteps><GUI><xloc>388</xloc><yloc>235</yloc><draw>Y</draw></GUI></step><step_error_handling><error><source_step>${DZZWINSERTUPDATE}</source_step><target_step>&#x83b7;&#x53d6;&#x64cd;&#x4f5c;&#x65f6;&#x95f4;</target_step><is_enabled>Y</is_enabled><nr_valuename/><descriptions_valuename>ERR_MSG</descriptions_valuename><fields_valuename/><codes_valuename/><max_errors/><max_pct_errors/><min_pct_rows/></error></step_error_handling><slave-step-copy-partition-distribution></slave-step-copy-partition-distribution><slave_transformation>N</slave_transformation></transformation>";
		DB.update(sql, new Object[]{tId, name, type, xml});*/
		
		/*String sql = "select TEMPLATE_XML from T_TEMPLATE order by id asc";
		String ssss = (String)DB.jdbcTemplate.query(sql, new ResultSetExtractor() { 
			public Object extractData(ResultSet rs) throws SQLException,  
				DataAccessException {  
					rs.next();  
					return lobHandler.getClobAsString(rs, 1);
			}  
		});
		System.out.println(ssss);*/
		
//		List ls = DB.jdbcTemplate.query(sql, args, new RowMapper(){
//
//			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//				return lobHandler.getClobAsString(rs, 1);
//			}
//			
//		});
//		for(Object s : ls){
//			System.out.println(s);
//		}
		
//		updateJobStatus("11", "", 0, 0, 1111L, 111L);
//		String sQuerySql = "select * from R_JOB where ID_JOB=1";
//		Map map = DB.queryForMap(sQuerySql);
//		System.out.println(map.get("NAME"));
		
		String[] sqls = new String[100000];
		long j = 526007;
		for (int i = 0; i < sqls.length; i++) {
			j++;
			sqls[i] = "insert into test_from VALUES ( "+ j +", '111', '111', '111', '111', false)";
			new Thread().start();
		}
		DB.batchUpdate(sqls);
		
		//IWapContext.init();
		/*OpertTest t = new OpertTest();
		String[] aCodeList  = getDevCodeList("0=010000000");
		if(aCodeList==null || aCodeList.length==0){
    		System.out.println("-1|您没有权限添加任何设备");
    	}
		List l = t.readExcel("h:/ftp/111.xls",aCodeList);
		System.out.println(l.size());*/
	}
	
	public static void main3(String args[]) {
		String querySql = "SELECT ID_JOB, ERRCOUNT, RUNCOUNT FROM R_JOB_EXTENAL WHERE ID_JOB=?";
		Map map = DB.queryForMap(querySql, new String[]{"121"});
		System.out.println(map.get("ID_JOB"));
		System.out.println(map.get("ID_JOB"));
//		BigDecimal runCount = map.get("RUNCOUNT")==null ? new BigDecimal(0) : (BigDecimal) map.get("RUNCOUNT");
//		BigDecimal errorCount = map.get("ERRCOUNT")==null ? new BigDecimal(0) : (BigDecimal) map.get("ERRCOUNT");
//		if(currState == 2){
//			runCount = new BigDecimal(runCount.longValue() + 1);
//		} else if (currState == 4){
//			errorCount = new BigDecimal(errorCount.longValue() + 1);
//		} 
//		
//		String updateSql = "UPDATE R_JOB_EXTENAL SET STATE=?, LASTRESULT=?, LASTRUNTIME=?, NEXTRUNTIME=?, RUNCOUNT=?, ERRCOUNT=? WHERE ID_JOB=?";
//		DB.update(updateSql,  new Object[]{currState, lastState, new Date(lastTime), new Date(lastTime), runCount, errorCount, jobID});
	}
	
	//获取用户管理的设备号
		public static String[] getDevCodeList(String branchAndLevel){
			String sQuerySql = "select dev_code from dev_bmsg";
			String sQueryConditionSql = "";
			List codeList = new ArrayList();
			if (!"0=010000000".equals(branchAndLevel)){
				sQueryConditionSql = " where dev_branch"+branchAndLevel.substring(0,1)+"="+branchAndLevel.substring(2);
			}
			
			log.info("准备执行查询辖下设备数据,SQL:"+sQuerySql+sQueryConditionSql);
			try{
				codeList = DBFactory.queryForList(sQuerySql+sQueryConditionSql);
			}catch(Exception e){
				log.error("处理发生异常：", e);
				return null;
			}
	    	log.info("执行查询完成,记录数:"+codeList.size());
	    	
			if(codeList.size()==0){
				return null;
			} 

			String[] aReturn = new String[codeList.size()];
			for (int i = 0; i < aReturn.length; i++) {
				Map map = (Map) codeList.get(i);
				aReturn[i] = (String) map.get("dev_code");
			}
			return aReturn;
		}
	
	public List readExcel(String filePath, String[] aCodeList) {
		List lList = new ArrayList();
		//读取Excel
		Workbook workbook = null;
		////System.out.println("filePath==>"+filePath);
		try {
			workbook = Workbook.getWorkbook(new FileInputStream(filePath));
			Sheet sheet = workbook.getSheet(0); // 第1个sheet
			//Cell c = null;// 单元格
			//int rows = sheet.getRows();// 总行数
			//System.out.println("输出总行数："+rows+"\t输出总列数："+cols);
			int maxRowNo = sheet.getRows() + 1;
			for (int i = 4; i < maxRowNo - 1; i++) {
				String sDevCode = sheet.getCell(0, i).getContents().toString().trim();
				String sContactLevelName = sheet.getCell(1, i).getContents().toString().trim();
				String sFaultTypeName = sheet.getCell(2, i).getContents().toString().trim();
				String sContactMobile = sheet.getCell(3, i).getContents().toString().trim();
				String sContactName = sheet.getCell(4, i).getContents().toString().trim();
				if("".equals(sDevCode)&&"".equals(sContactLevelName)&&"".equals(sFaultTypeName)&&"".equals(sContactMobile)){
					log.info("第" + (i+1) + "行数据全为空，忽略该行数据；");
					continue;
				}
				
				if ("".equals(sDevCode)){
					log.info("第" + (i+1) + "行设备号为空，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "设备号为空，忽略该行数据；");
					continue;
				}
				
				if ("".equals(sContactLevelName)){
					log.info("第" + (i+1) + "行联系人级别为空，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "联系人级别为空，忽略该行数据；");
					continue;
				}
				
				if ("".equals(sFaultTypeName)){
					log.info("第" + (i+1) + "行故障类别为空，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "故障类别为空，忽略该行数据；");
					continue;
				}
				
				if ("".equals(sContactMobile)){
					log.info("第" + (i+1) + "行手机号码为空，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "手机号码为空，忽略该行数据；");
					continue;
				}
				
				if(mContacatLevel.get(sContactLevelName)==null){
					log.info("第" + (i+1) + "行联系人级别为非法值，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "联系人级别为非法值，忽略该行数据； ");
					continue;
				}
				
				if(mFaultType.get(sFaultTypeName)==null){
					log.info("第" + (i+1) + "行故障类别为非法值，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "故障类别为非法值，忽略该行数据；");
					continue;
				}
				
				boolean devBelongMe = false;
				for (int j = 0; j < aCodeList.length; j++) {
					if(aCodeList[j].equals(sDevCode)){
						devBelongMe = true;
						break;
					}
				}
				if(!devBelongMe){
					log.info("第" + (i+1) + "行设备号对应设备不属于当前操作人管理范围，忽略该行数据；");
					outMsgMap.put(Integer.toString(i+1), "设备号对应设备不属于当前操作人管理范围，忽略该行数据；");
					continue;
				}
				
				String sFaultType = (String) mFaultType.get(sFaultTypeName);
				String sContactLevel = (String) mContacatLevel.get(sContactLevelName);
				
				Map mMap = new HashMap();
				mMap.put("rowNo", i+1);
				mMap.put("devCode", sDevCode);
				mMap.put("contactLevel", sContactLevel);
				mMap.put("faultType", sFaultType);
				mMap.put("contactLevelName", sContactLevelName);
				mMap.put("faultTypeName", sFaultTypeName);
				mMap.put("contactMobile", sContactMobile);
				mMap.put("contactName", sContactName);
				
				lList.add(mMap);
			}
			return lList;
		} catch (Exception e) {
			log.error("读取Excel时发生异常:" + e);
			return null;
		}
	}
	
	public OpertTest() {
		String[] aContacatLevelKey = {"第一联系人","第二联系人","网点主任","二级分行","一级分行","三级维护商","二级维护商","一级维护商"};
		String[] aFaultTypeKey = {"全部","设备故障","缺满钞","日常维护"};
		for (int i = 0; i < aContacatLevelKey.length; i++) {
			mContacatLevel.put(aContacatLevelKey[i], Integer.toString(i+1));
		}
		
		for (int i = 0; i < aFaultTypeKey.length; i++) {
			mFaultType.put(aFaultTypeKey[i], Integer.toString(i));
		}
		
	}
}
