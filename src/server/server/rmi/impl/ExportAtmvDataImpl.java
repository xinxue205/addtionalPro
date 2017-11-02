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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 ����4:37:33
 * @Description
 * @version 1.0 Shawn create
 */
public class ExportAtmvDataImpl implements IRmiProcess {
	private static Logger log = Logger.getLogger(ExportAtmvDataImpl.class);
	//private static final LogUtil log = new LogUtil(ExportAtmvDataImpl.class);
	private DBBaseUtil dbBaseUtil = null; // ���ݿ����������
	private JdbcTemplate jdbcTemplate = null;
    private String errMessg="";
	public ExportAtmvDataImpl() {
		//dbBaseUtil = new DBBaseUtil();
		//this.jdbcTemplate = dbBaseUtil.getJdbcTemplate() == null ? null : dbBaseUtil.getJdbcTemplate();
	}

	/**
	 * @param mMsgParams
	 *            ���������룬�����������Ĳ���
	 * @return HashMap "cwxx"����������Ϣ��get("cwxx")Ϊ��ʱ����������
	 */
	public Map<String, Object> process(Map<String, Object> mParams) {
		log.info("�������ݣ�"+mParams);
		mParams.put("cwxx", "���׳ɹ�" );
		return mParams;
		/*try {
			String dbSourceStyle = "" + mParams.get("dbSource");
			int idbStyle = 1;
			try {
				idbStyle = Integer.parseInt(dbSourceStyle);
			} catch (NumberFormatException nfe) {
				log.error("��ʽ������Դ����[" + dbSourceStyle + "]����!");
			}
			initDbBase(idbStyle);
			if (jdbcTemplate == null || dbBaseUtil == null) {
				log.error("���ݿ�����ʧ�ܣ������˳�!!");
				mParams.put("cwxx", "���ݿ�����ʧ��");
				return mParams;
			}

			String sExportSql = mParams.get("sql") + "";
			if (sExportSql.equals("null") || sExportSql.equals("") || !sExportSql.toUpperCase().contains("SELECT ")) {
				log.error("SQL�����Ϲ���,����sqlΪselect��䣡");
				mParams.put("cwxx", "SQL�����Ϲ���,����sqlΪselect��䣡");
				return mParams;
			}
			log.info("���ݵ�����ʼ������SQLΪ:[" + sExportSql + "]");
			
			// ������������Ƿ񳬹�10000��
			int iCount = getSqlSearchCount( sExportSql );
			
			if (iCount > 10000) {
				log.error("����������" + iCount + "̫�������˳�!!");
				mParams.put("cwxx", "����������" + iCount + "̫��,�����������10000");
				return mParams;
			}

			// List<Map<String, Object>> lResult =
			// jdbcTemplate.queryForList(sExportSql);

			// ������ʱ�ļ���
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyMMDD_hhmmss");
			String sExportPath =  ConfigValue.initconfigValue.TempFilePath;//��ʱ�ļ�Ŀ¼;
			String sFileName = "db" + sf.format(calendar.getTime()) + ".txt.zip";
			// String sFilePath = sExportPath + "db" +
			// sf.format(calendar.getTime()) + ".txt";
			File tempFile = new File( sExportPath + File.separatorChar + sFileName );
			String sFilePath = tempFile.getPath();
			log.info("���ݵ��������أ�" + sFilePath);

			// ��������
			boolean exporFlag = exportSqlData(sExportSql, sFilePath, idbStyle);
			if (!exporFlag) {
				log.error("����rmi����ʧ��!sql[" + sExportSql + "]");
				mParams.put("cwxx","����rmi����ʧ��!"+ this.errMessg);
				return mParams;
			}
			//sFileName += ".zip";
			//sFilePath += ".zip";
			
			 * // �������� PrintWriter printWriter = null; try { File fDir = new
			 * File(sExportPath); if (!fDir.exists()) fDir.mkdirs();
			 * 
			 * printWriter = new PrintWriter(sFilePath, "UTF-8");
			 * 
			 * //�޸ĵ������ݵĸ�ʽΪ�����߷ָ��ĸ�ʽ // дSQL //printWriter.println(sExportSql);
			 *  // // д��ͷ�����б��� // Map mRowH = (Map) lResult.get(0); // String
			 * sRowH = ""; // Set sKeyValueH = mRowH.entrySet(); // Iterator itH =
			 * sKeyValueH.iterator(); // while (itH.hasNext()) { // sRowH +=
			 * ((Map.Entry) itH.next()).getKey() + "\t"; // } //
			 * printWriter.println(sRowH);
			 *  // дֵ for (int iIndex = 0; iIndex < lResult.size(); iIndex++) {
			 * Map<String,Object> mRow = (Map<String,Object>)
			 * lResult.get(iIndex); String sRow = ""; Set sKeyValue =
			 * mRow.entrySet(); Iterator it = sKeyValue.iterator(); while
			 * (it.hasNext()) { sRow += ((Map.Entry) it.next()).getValue() +
			 * "|"; } printWriter.println(sRow); } } catch
			 * (FileNotFoundException e) { log.error("������ʱ�ļ�����:" + sFilePath);
			 * mParams.put("cwxx", "������ʱ�ļ�����"); return mParams; } catch
			 * (UnsupportedEncodingException e) { log.error("������ʱ�ļ�����:" +
			 * e.toString()) ; } finally { if (printWriter != null)
			 * printWriter.close(); }
			 
			// �����ļ�
			if( ! ConfigValue.initconfigValue.CurrentZhBranchFlag ){//���������д����ļ�
				String sSavePath = PropReader.getProperty("/conf/rmi.properties", "vhSavePath");
				String sAtmvhHost = PropReader.getProperty("/conf/rmi.properties", "AtmvhHost");
				String sAtmvhPort = PropReader.getProperty("/conf/rmi.properties", "AtmvhPort");
				
				FileServer fileServer = new FileServer();
				String vhSavePath = new File( sSavePath  + File.separatorChar + sFileName ).getPath();
				log.info("�����ļ���" + sFilePath + "��" + sAtmvhHost + ":" + sAtmvhPort + "��" + vhSavePath);
				int iRet = fileServer.giSendFileToLTFServ( sFilePath, vhSavePath, sAtmvhHost, Integer
						.parseInt(sAtmvhPort));
				if (iRet != 0) {
					log.error("�����ļ�ʧ��:" + sFilePath + "��" + sAtmvhHost + ":" + sAtmvhPort + "��" + sSavePath + sFileName);
					mParams.put("cwxx", "�����ļ�ʧ��");
				} else
					log.info("�����ļ��ɹ���");
				mParams.put("fhxx", vhSavePath );
				return mParams;
			}else{//����ֱ�ӷ���
				mParams.put("fhxx", sFilePath );
				return mParams;
			}
		} catch (Exception ex) {
			log.error("����RMI���������쳣!" + ex.toString());
			mParams.put("cwxx", "����RMI���������쳣!" + ex.toString());
			return mParams;

		}*/
	}
	
	/**
	 * ���ݿ����ʱѡ������Դ
	 * @param idbStyle
	 */
	private void initDbBase( int idbStyle){
		dbBaseUtil = new DBBaseUtil( idbStyle);
		this.jdbcTemplate = dbBaseUtil.getJdbcTemplate() == null ? null : dbBaseUtil.getJdbcTemplate();
	}
	
	/**
	 * ���ݵ�������
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
			log.error("RMI���õ������ݳ����쳣!" + ioe.toString() );
			errMessg = "RMI���õ������ݳ����쳣!" + ioe.toString();
			return false;
		}
	}
	
	/**
	 * ��ȡҪ������Sql�Ľ����¼����
	 * 
	 * @param sExportSql
	 *            ������sql���
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
			log.error("������Sql�Ľ����¼���������쳣!" + e.getMessage());
			return 1;
		}
	}
}
