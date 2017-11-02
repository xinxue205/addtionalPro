/**
 * 
 */
package server.server.rmi.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import server.server.rmi.base.IRmiProcess;
import server.util.DBBaseUtil;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 下午4:37:33
 * @Description
 * @version 1.0 Shawn create
 */
public class ExportAtmvDataImpl implements IRmiProcess {
	private static Logger log = Logger.getLogger(ExportAtmvDataImpl.class);
	//private static final LogUtil log = new LogUtil(ExportAtmvDataImpl.class);
	private DBBaseUtil dbBaseUtil = null; // 数据库基本操作类
	private JdbcTemplate jdbcTemplate = null;
    private String errMessg="";
	public ExportAtmvDataImpl() {
		//dbBaseUtil = new DBBaseUtil();
		//this.jdbcTemplate = dbBaseUtil.getJdbcTemplate() == null ? null : dbBaseUtil.getJdbcTemplate();
	}

	/**
	 * @param mMsgParams
	 *            包含交易码，和其他传来的参数
	 * @return HashMap "cwxx"包含错误信息，get("cwxx")为空时，处理正常
	 */
	public Map<String, Object> process(Map<String, Object> mParams) {
		log.info("交易数据："+mParams);
		mParams.put("cwxx", "交易成功" );
		return mParams;
		/*try {
			String dbSourceStyle = "" + mParams.get("dbSource");
			int idbStyle = 1;
			try {
				idbStyle = Integer.parseInt(dbSourceStyle);
			} catch (NumberFormatException nfe) {
				log.error("格式化数据源参数[" + dbSourceStyle + "]出错!");
			}
			initDbBase(idbStyle);
			if (jdbcTemplate == null || dbBaseUtil == null) {
				log.error("数据库连接失败，任务退出!!");
				mParams.put("cwxx", "数据库连接失败");
				return mParams;
			}

			String sExportSql = mParams.get("sql") + "";
			if (sExportSql.equals("null") || sExportSql.equals("") || !sExportSql.toUpperCase().contains("SELECT ")) {
				log.error("SQL不符合规则,请检查sql为select语句！");
				mParams.put("cwxx", "SQL不符合规则,请检查sql为select语句！");
				return mParams;
			}
			log.info("数据导出开始！导出SQL为:[" + sExportSql + "]");
			
			// 检测总数据量是否超过10000条
			int iCount = getSqlSearchCount( sExportSql );
			
			if (iCount > 10000) {
				log.error("导出数据量" + iCount + "太大，任务退出!!");
				mParams.put("cwxx", "导出数据量" + iCount + "太大,超过最大允许10000");
				return mParams;
			}

			// List<Map<String, Object>> lResult =
			// jdbcTemplate.queryForList(sExportSql);

			// 构建临时文件名
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyMMDD_hhmmss");
			String sExportPath =  ConfigValue.initconfigValue.TempFilePath;//临时文件目录;
			String sFileName = "db" + sf.format(calendar.getTime()) + ".txt.zip";
			// String sFilePath = sExportPath + "db" +
			// sf.format(calendar.getTime()) + ".txt";
			File tempFile = new File( sExportPath + File.separatorChar + sFileName );
			String sFilePath = tempFile.getPath();
			log.info("数据导出到本地：" + sFilePath);

			// 导出数据
			boolean exporFlag = exportSqlData(sExportSql, sFilePath, idbStyle);
			if (!exporFlag) {
				log.error("导出rmi数据失败!sql[" + sExportSql + "]");
				mParams.put("cwxx","导出rmi数据失败!"+ this.errMessg);
				return mParams;
			}
			//sFileName += ".zip";
			//sFilePath += ".zip";
			
			 * // 导出数据 PrintWriter printWriter = null; try { File fDir = new
			 * File(sExportPath); if (!fDir.exists()) fDir.mkdirs();
			 * 
			 * printWriter = new PrintWriter(sFilePath, "UTF-8");
			 * 
			 * //修改导出数据的格式为以竖线分隔的格式 // 写SQL //printWriter.println(sExportSql);
			 *  // // 写表头，即列表名 // Map mRowH = (Map) lResult.get(0); // String
			 * sRowH = ""; // Set sKeyValueH = mRowH.entrySet(); // Iterator itH =
			 * sKeyValueH.iterator(); // while (itH.hasNext()) { // sRowH +=
			 * ((Map.Entry) itH.next()).getKey() + "\t"; // } //
			 * printWriter.println(sRowH);
			 *  // 写值 for (int iIndex = 0; iIndex < lResult.size(); iIndex++) {
			 * Map<String,Object> mRow = (Map<String,Object>)
			 * lResult.get(iIndex); String sRow = ""; Set sKeyValue =
			 * mRow.entrySet(); Iterator it = sKeyValue.iterator(); while
			 * (it.hasNext()) { sRow += ((Map.Entry) it.next()).getValue() +
			 * "|"; } printWriter.println(sRow); } } catch
			 * (FileNotFoundException e) { log.error("创建临时文件出错:" + sFilePath);
			 * mParams.put("cwxx", "创建临时文件出错！"); return mParams; } catch
			 * (UnsupportedEncodingException e) { log.error("创建临时文件出错:" +
			 * e.toString()) ; } finally { if (printWriter != null)
			 * printWriter.close(); }
			 
			// 传送文件
			if( ! ConfigValue.initconfigValue.CurrentZhBranchFlag ){//分行往总行传送文件
				String sSavePath = PropReader.getProperty("/conf/rmi.properties", "vhSavePath");
				String sAtmvhHost = PropReader.getProperty("/conf/rmi.properties", "AtmvhHost");
				String sAtmvhPort = PropReader.getProperty("/conf/rmi.properties", "AtmvhPort");
				
				FileServer fileServer = new FileServer();
				String vhSavePath = new File( sSavePath  + File.separatorChar + sFileName ).getPath();
				log.info("发送文件：" + sFilePath + "到" + sAtmvhHost + ":" + sAtmvhPort + "的" + vhSavePath);
				int iRet = fileServer.giSendFileToLTFServ( sFilePath, vhSavePath, sAtmvhHost, Integer
						.parseInt(sAtmvhPort));
				if (iRet != 0) {
					log.error("发送文件失败:" + sFilePath + "到" + sAtmvhHost + ":" + sAtmvhPort + "的" + sSavePath + sFileName);
					mParams.put("cwxx", "发送文件失败");
				} else
					log.info("发送文件成功！");
				mParams.put("fhxx", vhSavePath );
				return mParams;
			}else{//总行直接返回
				mParams.put("fhxx", sFilePath );
				return mParams;
			}
		} catch (Exception ex) {
			log.error("调用RMI导出数据异常!" + ex.toString());
			mParams.put("cwxx", "调用RMI导出数据异常!" + ex.toString());
			return mParams;

		}*/
	}
	
	/**
	 * 数据库操作时选择数据源
	 * @param idbStyle
	 */
	private void initDbBase( int idbStyle){
		dbBaseUtil = new DBBaseUtil( idbStyle);
		this.jdbcTemplate = dbBaseUtil.getJdbcTemplate() == null ? null : dbBaseUtil.getJdbcTemplate();
	}
	
	/**
	 * 数据导出调用
	 * @param sQuerySql
	 * @param sFileFullPath
	 * @param bVPDbSourse
	 * @return
	 */
	private boolean exportSqlData(String sQuerySql , String sFileFullPath ,int  bVPDbSourse){
		try{
			boolean exportFlag = false;
		/*DBExportUtil dbExportUtil = new DBExportUtil(sQuerySql, sFileFullPath, true);
		dbExportUtil.setVPDbSourse( bVPDbSourse );
		dbExportUtil.setConvertDateFlag(true);
		exportFlag =  dbExportUtil.unloadDbDataToFile();
		if( !exportFlag){
			errMessg = dbExportUtil.getErrMessg();
		}*/
		return exportFlag;
		}catch(Exception ioe){
			log.error("RMI调用导出数据出现异常!" + ioe.toString() );
			errMessg = "RMI调用导出数据出现异常!" + ioe.toString();
			return false;
		}
	}
	
	/**
	 * 获取要导出的Sql的结果记录条数
	 * 
	 * @param sExportSql
	 *            导出的sql语句
	 * @return
	 */
	private int getSqlSearchCount(String sExportSql) {
		try {
			int iCount = 0;
			String sSqlTmp = sExportSql.toLowerCase();

			int iFromStart = sSqlTmp.indexOf("from");
			String sHaveKnowCountSql = sSqlTmp.substring(0, iFromStart);
			int iFirstStart = -1;
			if ((iFirstStart = sHaveKnowCountSql.indexOf("first")) != -1) {

				String firstAfterStr = sHaveKnowCountSql.substring(iFirstStart + 5);
				firstAfterStr = firstAfterStr.trim();
				String[] firstArray = firstAfterStr.split("\\s+");
				if (firstArray != null && firstArray.length != 0)
					iCount = Integer.parseInt(firstArray[0]);
				
				sSqlTmp = "select count(*) " + sSqlTmp.substring(sSqlTmp.indexOf("from"));
				int iCountTemp = 1; //jdbcTemplate.queryForInt(sSqlTmp);
				if( iCount > iCountTemp )
					iCount = iCountTemp;

			} else if ((iFirstStart = sHaveKnowCountSql.indexOf("count")) != -1) {

				iCount = 1;

			} else {

				sSqlTmp = "select count(*) " + sSqlTmp.substring(sSqlTmp.indexOf("from"));
				iCount = 1; //jdbcTemplate.queryForInt(sSqlTmp);
			}
			return iCount;
		} catch (Exception e) {
			log.error("导出的Sql的结果记录条数出现异常!" + e.getMessage());
			return 1;
		}
	}
}
