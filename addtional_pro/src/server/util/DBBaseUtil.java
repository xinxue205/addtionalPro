/**
 * 
 */
package server.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 ����4:49:14
 * @Description ���ݿ⹫����������
 * @version 1.0 Shawn create
 */
public class DBBaseUtil {
	private final LogUtil logUtil = new LogUtil(DBBaseUtil.class); // ��־��¼��
	private DataSource dataSource = null;
	private JdbcTemplate jdbcTemplate = null;
	private List<String> alFieldName = new ArrayList<String>();
	
	private int iVPDbSourse = 0 ;
	
	public DBBaseUtil(){
		this.initDbBaseUtil() ;
	}
	
	public DBBaseUtil(int iVPDbSourse){
		this.iVPDbSourse = iVPDbSourse ;
		this.initDbBaseUtil() ;
	}

	public DBBaseUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * ��������ѡ����������Դ
	 */
	private void initDbBaseUtil() {

//		try {
//			JdbcDaoSupport jdbcDaoSupport = null;
//			switch (this.iVPDbSourse) {
//			case 1:
//				jdbcDaoSupport = (VHDBBaseUtil) IWapContext.getContext().getBean("VHDBBaseUtil");
//				break;
//			case 2:
//				jdbcDaoSupport = (OldVHDBBaseUtil) IWapContext.getContext().getBean("OldVHDBBaseUtil");
//				break;
//			case 3:
//				jdbcDaoSupport = (PHBusiDBBaseUtil) IWapContext.getContext().getBean("PHBusiDBBaseUtil");
//				break;
//			case 4:
//				jdbcDaoSupport = (PHDepDBBaseUtil) IWapContext.getContext().getBean("PHDepDBBaseUtil");
//				break;
//			default:
//				jdbcDaoSupport = (VHDBBaseUtil) IWapContext.getContext().getBean("VHDBBaseUtil");
//			}
//
//			this.jdbcTemplate = jdbcDaoSupport == null ? null : jdbcDaoSupport.getJdbcTemplate();
//			this.dataSource = jdbcDaoSupport == null ? null : jdbcDaoSupport.getDataSource();
//		} catch (Exception e) {
//			logUtil.error("��ʼ�����ݿ�����ӷ����쳣:" + e.toString());
//		}
		try {
//			switch (this.iVPDbSourse) {
//			case 1:
//				this.dataSource = (DataSource) IWapContext.getContext().getBean("dataSource");
//				break;
//			case 2:
//				this.dataSource = (DataSource) IWapContext.getContext().getBean("dataSource4");
//				break;
//			case 3:
//				this.dataSource = (DataSource) IWapContext.getContext().getBean("dataSource3");
//				break;
//			case 4:
//				this.dataSource = (DataSource) IWapContext.getContext().getBean("dataSource2");
//				break;
//			default:
//				this.dataSource = (DataSource) IWapContext.getContext().getBean("dataSource");
//			}
			this.dataSource = JdbcFactory.getDataSource();
			this.jdbcTemplate = new JdbcTemplate(this.dataSource);
			//�������ȴ���ʱ��20�� ��ֹ�б��������Ϸ���ʧ��
			this.jdbcTemplate.execute("SET LOCK MODE TO WAIT 20") ;
 
		} catch (Exception e) {
			logUtil.error("��ʼ�����ݿ�����ӷ����쳣:" + e.toString());
		}
	}
	
	public JdbcTemplate getJdbcTemplate(){
		return this.jdbcTemplate ;
	}
	
	public DataSource getDataSource(){
		return this.dataSource ;
	}

	/**
	 * ���ݲ�ѯ����ȡ��ѯ�����Ӧ������
	 * 
	 * @param sQuerySql
	 *            ��ѯ���
	 * @return ��ѯ�������
	 */
	public Map<String, String> queryForColumn(String sQuerySql) {

		alFieldName = new ArrayList<String>(); 
		
		Connection connection = DataSourceUtils.getConnection(this.dataSource);
		Map<String, String> mFieldType = new HashMap<String, String>();
		try {
			// �õ�һ���鲻���������sql��䣬�����õ�metadata��
			//gj++ ��Ϊ֧�ִ�group by order by �����ݵ���
			sQuerySql = getCountSQLStr( sQuerySql );
			logUtil.debug( "��ѯmetadata��SQL["+ sQuerySql +"]" );
		/*	if (sQuerySql.toLowerCase().indexOf("where") != -1)
				sQuerySql += " and ";
			else
				sQuerySql += " where";
			sQuerySql += " 1=2";*/
			logUtil.debug("metaDataSql:" + sQuerySql);
			PreparedStatement prepareStatement = connection
					.prepareStatement(sQuerySql);
			ResultSet rs = prepareStatement.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// �����ֶ�����ȡ��Ӧ���ֶ�����
			for (int iIndex = 1; iIndex <= rsMetaData.getColumnCount(); iIndex++) {
				String sColumnName = rsMetaData.getColumnName(iIndex);
				int iColumnType = rsMetaData.getColumnType(iIndex);
				mFieldType.put(sColumnName, String.valueOf(iColumnType));
				alFieldName.add(sColumnName);
			}
			rs.close();
			rs = null;
			prepareStatement.close();
			return mFieldType;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (!connection.isClosed()) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				logUtil.error("�ر����ݿ����ӷ����쳣:" + e.toString());
			}
		}
	}
	
	/**
	 * ���ݲ�ѯ����ȡ��ѯ�����Ӧ������
	 * 
	 * @param sQuerySql
	 *            ��ѯ���
	 * @return ��ѯ�������
	 */
	public int[] batchInsert(List<String> alInsertSql) {

		Connection connection = DataSourceUtils.getConnection(this.dataSource);
		try {
			// �õ�һ���鲻���������sql��䣬�����õ�metadata��
			Statement prepareStatement = connection.createStatement();
			for (int iIndex = 0; iIndex < alInsertSql.size(); iIndex++) {
				prepareStatement.addBatch(alInsertSql.get(iIndex).toString());
			}
			int[] iArrResult = prepareStatement.executeBatch();
			connection.commit();
			prepareStatement.close();
			return iArrResult;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (!connection.isClosed()) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				logUtil.error("�ر����ݿ����ӷ����쳣:" + e.toString());
			}
		}
	}

	/**
	 * ������ʱ���ݱ�
	 * 
	 * @return ��Ϊʧ��
	 */
	public int createTempTable(String sCreateSql) {
		try {
			// ������ݿ����ӳ��Ƿ���Ч
			if (jdbcTemplate == null) {
				logUtil.warn("jdbcTemplate is null ���ݿ�����ʧ��");
				return -1;
			}
			logUtil.debug("�������:" + sCreateSql);
			jdbcTemplate.execute(sCreateSql);
		} catch (DataAccessException sqlExce) {
			logUtil.error("�������ݱ�ʧ��!! �쳣��Ϣ:["+sqlExce.toString()+"]");
			return -1;
		}
		return 1;
	}

	/**
	 * ������ʱ���ݱ� ���������е����� �������SQL, �����ͱ�Ĵ�����䲻�ܷ���һ���ַ�����
	 * 
	 * @return ��Ϊʧ��
	 */
	public int createTempTable(String[] sCreateSqlArray) {
		if (sCreateSqlArray == null) {
			logUtil.error("������䲻��Ϊ��!!");
			return -1;
		}
		try {
			// ������ݿ����ӳ��Ƿ���Ч
			if (jdbcTemplate == null) {
				logUtil.warn("jdbcTemplate is null ���ݿ�����ʧ��");
				return -1;
			}
			String sCreateSql = "";
			for (int iIndex = 0; iIndex < sCreateSqlArray.length; iIndex++) {
				sCreateSql = sCreateSqlArray[iIndex];
				logUtil.debug("�������:" + sCreateSql);
				jdbcTemplate.execute(sCreateSql);
			}
		} catch (DataAccessException sqlExce) {
			logUtil.error("�������ݱ�����ʧ��!![" + sqlExce.toString() + "]");
			return -1;
		}
		return 1;
	}

	/**
	 * ����ʱ���е����ݵ��뵽��Ӧ����
	 * 
	 * @return ��Ϊʧ��
	 */
	public int importDataToTable(String sTableName, String sTempTable) {
		String sImportSql = "insert into " + sTableName + " select * from "
				+ sTempTable;
		try {
			int iResult = this.jdbcTemplate.update(sImportSql);
			if (iResult < 0) {
				logUtil.error("����ʱ����µ����ݿ�ʧ��!");
			}
			return iResult;
		} catch (DataAccessException e) {
			logUtil.error("����ʱ����µ����ݿ�ʧ��!" + e.getMessage());
			return -1;
		}
	}

	/**
	 * ɾ����ʱ��
	 * 
	 * @return ��Ϊʧ��
	 */
	public int dropTempTable(String sTempTable) throws DataAccessException {
		String sDropTable = "drop table " + sTempTable;
		try {
			// ������ݿ����ӳ��Ƿ���Ч
			if (jdbcTemplate == null) {
				logUtil.warn("jdbcTemplate is null ���ݿ�����ʧ��");
				return -1;
			}
			jdbcTemplate.execute(sDropTable);
		} catch (DataAccessException sqlExce) {
			logUtil.error("ɾ�����ݿ��ʧ��!!" + sqlExce.toString());
			return -1;
		}
		return 1;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @param dataSource
	 *            ���õ�����Դ
	 * @return ���������
	 */
	public DataSourceTransactionManager getTranscationManage() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(this.dataSource);
		return transactionManager;
	}

	/**
	 * ����һ������
	 * 
	 * @param transactionManager
	 *            ���������
	 * @param ipropagationBehavior
	 *            ����Ĵ�����Ϊ(DefaultTransactionDefinition�ж�����)
	 * @return ����״̬��Ϣ
	 */
	public TransactionStatus transcationBegin(
			DataSourceTransactionManager transactionManager,
			int ipropagationBehavior) throws TransactionException {
		DefaultTransactionDefinition transDef = new DefaultTransactionDefinition();
		transDef.setPropagationBehavior(ipropagationBehavior);
		TransactionStatus tranStatus = transactionManager
				.getTransaction(transDef);
		return tranStatus;
	}

	/**
	 * �ύһ������
	 * 
	 * @param transactionManager
	 *            ���������
	 * @param tranStatus
	 *            ����״̬��Ϣ
	 * @return ִ�н�� ��Ϊʧ��
	 */
	public int transcationCommit(
			DataSourceTransactionManager transactionManager,
			TransactionStatus tranStatus) {
		try {
			transactionManager.commit(tranStatus);
		} catch (TransactionException transExcepiton) {
			logUtil.error(" �����ύ����! �쳣��Ϣ:["+transExcepiton.toString()+"]");
			return -1;
		}
		return 1;
	}

	/**
	 * �ع�һ������
	 * 
	 * @param transactionManager
	 *            ���������
	 * @param tranStatus
	 *            ����״̬��Ϣ
	 * @return ִ�н�� ��Ϊʧ��
	 */
	public int transcationrollback(
			DataSourceTransactionManager transactionManager,
			TransactionStatus tranStatus) {
		try {
			transactionManager.rollback(tranStatus);
		} catch (TransactionException transExcepiton) {
			logUtil.error(" ����ع�����! �쳣��Ϣ:["+transExcepiton.toString()+"]");
			return -1;
		}
		return 1;
	}

	/**
	 * ��ȡ�ֶ���
	 * 
	 * @return �ֶ���
	 */
	public List<String> getFieldName() {
		return this.alFieldName;
	}
	
	/**
	 * ���ݲ�ѯ����ȡ��ѯ�����Ӧ������
	 * 
	 * @param sQuerySql
	 *            ��ѯ���
	 * @return ��ѯ�������
	 */
	public List<String>  getTableprimaryKey (String sTableName ) {
		
		Connection connection = DataSourceUtils.getConnection(this.dataSource);
		List<String> tableKeyList = new ArrayList<String>();

		try {
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet rs = dbMetaData.getIndexInfo(null, null, sTableName, false, false);
			boolean findUniqueIndex = false;
			String sIndexName = "";
			int iIndexlity = 0;
			if( rs == null  ){
				logUtil.warn("û���ҵ�" + sTableName + "������Ϣ!");
				return tableKeyList;
			}
			while (rs.next()) {
				//System.out.println("����:"+rs.getString("index_name"));
				if (sIndexName == null || sIndexName.isEmpty()) {
					sIndexName = rs.getString("index_name");
					findUniqueIndex = !rs.getBoolean("non_unique");
				}

				if (sIndexName.equals(rs.getString("index_name"))) {
					tableKeyList.add(rs.getString("column_name"));
				} else if (findUniqueIndex)
					break;
				else {

					if (!rs.getBoolean("non_unique")) {
						if (0 == rs.getInt("CARDINALITY")) {
							tableKeyList.clear();
							tableKeyList.add(rs.getString("column_name"));
						} else {

							if (rs.getInt("CARDINALITY") > iIndexlity) {
								iIndexlity = rs.getInt("CARDINALITY");
								tableKeyList.add(rs.getString("column_name"));
							} else
								break;
						}
					}
				}
			}

			rs.close();
			rs = null;

			return tableKeyList;
		} catch (SQLException e) {
			logUtil.error("��ȡ��[" + sTableName + "]����������! �쳣��Ϣ:["+e.toString()+"]");
			throw new RuntimeException(e);
		} finally {
			try {
				if (!connection.isClosed()) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				logUtil.error("�ر����ݿ����ӷ����쳣:" + e.toString());
			}
		}
	}
	
	/**
	 * ����sql����ȷ�����õ�ֻҪ���ݽ�����͵����
	 * 
	 * @return ������sql
	 */
	private String getCountSQLStr(String sqlStr) {
		sqlStr = sqlStr.toLowerCase();
		int idxWhere = sqlStr.indexOf(" where");
		int idxGroup = sqlStr.indexOf(" group");
		int idxOrder = sqlStr.indexOf(" order");
		if (-1 == idxWhere) {
			if (-1 == idxGroup) {
				if (-1 == idxOrder) {
					sqlStr += " where 1=2";

				} else {
					String temp = sqlStr.substring(0, idxOrder);
					sqlStr = temp + " where 1=2 " + sqlStr.substring(idxOrder);
				}
			} else {
				String temp = sqlStr.substring(0, idxGroup);
				sqlStr = temp + " where 1=2 " + sqlStr.substring(idxGroup);
			}
		} else {
			String temp = sqlStr.substring(0, idxWhere + 6);
			sqlStr = temp + " 1=2 and " + sqlStr.substring(idxWhere + 6);
		}
		return sqlStr;
	}
	
	public static void main(String[] args) {
		String sql = "select * from m31_inner_smsg";
		JdbcTemplate jdbcTemplate = new DBBaseUtil().getJdbcTemplate();
		List list = jdbcTemplate.queryForList(sql);
		System.out.println(jdbcTemplate.getDataSource());
		System.out.println(list);
		System.out.println(list.size());
	}
}
