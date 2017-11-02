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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 下午4:49:14
 * @Description 数据库公共处理函数类
 * @version 1.0 Shawn create
 */
public class DBBaseUtil {
	private final LogUtil logUtil = new LogUtil(DBBaseUtil.class); // 日志记录器
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
	 * 根据任务选择具体的数据源
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
//			logUtil.error("初始化数据库库连接发生异常:" + e.toString());
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
			//增加锁等待的时间20秒 防止有表锁了马上返回失败
			this.jdbcTemplate.execute("SET LOCK MODE TO WAIT 20") ;
 
		} catch (Exception e) {
			logUtil.error("初始化数据库库连接发生异常:" + e.toString());
		}
	}
	
	public JdbcTemplate getJdbcTemplate(){
		return this.jdbcTemplate ;
	}
	
	public DataSource getDataSource(){
		return this.dataSource ;
	}

	/**
	 * 根据查询语句获取查询结果对应的类型
	 * 
	 * @param sQuerySql
	 *            查询语句
	 * @return 查询结果类型
	 */
	public Map<String, String> queryForColumn(String sQuerySql) {

		alFieldName = new ArrayList<String>(); 
		
		Connection connection = DataSourceUtils.getConnection(this.dataSource);
		Map<String, String> mFieldType = new HashMap<String, String>();
		try {
			// 得到一个查不出结果集的sql语句，用来得到metadata。
			//gj++ 改为支持带group by order by 的数据导出
			sQuerySql = getCountSQLStr( sQuerySql );
			logUtil.debug( "查询metadata的SQL["+ sQuerySql +"]" );
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
			// 根据字段名获取对应的字段类型
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
				logUtil.error("关闭数据库连接发生异常:" + e.toString());
			}
		}
	}
	
	/**
	 * 根据查询语句获取查询结果对应的类型
	 * 
	 * @param sQuerySql
	 *            查询语句
	 * @return 查询结果类型
	 */
	public int[] batchInsert(List<String> alInsertSql) {

		Connection connection = DataSourceUtils.getConnection(this.dataSource);
		try {
			// 得到一个查不出结果集的sql语句，用来得到metadata。
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
				logUtil.error("关闭数据库连接发生异常:" + e.toString());
			}
		}
	}

	/**
	 * 创建临时数据表
	 * 
	 * @return 负为失败
	 */
	public int createTempTable(String sCreateSql) {
		try {
			// 检查数据库连接池是否有效
			if (jdbcTemplate == null) {
				logUtil.warn("jdbcTemplate is null 数据库连接失败");
				return -1;
			}
			logUtil.debug("建表语句:" + sCreateSql);
			jdbcTemplate.execute(sCreateSql);
		} catch (DataAccessException sqlExce) {
			logUtil.error("创建数据表失败!! 异常信息:["+sqlExce.toString()+"]");
			return -1;
		}
		return 1;
	}

	/**
	 * 创建临时数据表 包括索引中的限制 创建表的SQL, 索引和表的创建语句不能放在一条字符串中
	 * 
	 * @return 负为失败
	 */
	public int createTempTable(String[] sCreateSqlArray) {
		if (sCreateSqlArray == null) {
			logUtil.error("建表语句不能为空!!");
			return -1;
		}
		try {
			// 检查数据库连接池是否有效
			if (jdbcTemplate == null) {
				logUtil.warn("jdbcTemplate is null 数据库连接失败");
				return -1;
			}
			String sCreateSql = "";
			for (int iIndex = 0; iIndex < sCreateSqlArray.length; iIndex++) {
				sCreateSql = sCreateSqlArray[iIndex];
				logUtil.debug("建表语句:" + sCreateSql);
				jdbcTemplate.execute(sCreateSql);
			}
		} catch (DataAccessException sqlExce) {
			logUtil.error("创建数据表及索引失败!![" + sqlExce.toString() + "]");
			return -1;
		}
		return 1;
	}

	/**
	 * 将临时表中的数据导入到对应表中
	 * 
	 * @return 负为失败
	 */
	public int importDataToTable(String sTableName, String sTempTable) {
		String sImportSql = "insert into " + sTableName + " select * from "
				+ sTempTable;
		try {
			int iResult = this.jdbcTemplate.update(sImportSql);
			if (iResult < 0) {
				logUtil.error("从临时表更新到数据库失败!");
			}
			return iResult;
		} catch (DataAccessException e) {
			logUtil.error("从临时表更新到数据库失败!" + e.getMessage());
			return -1;
		}
	}

	/**
	 * 删除临时表
	 * 
	 * @return 负为失败
	 */
	public int dropTempTable(String sTempTable) throws DataAccessException {
		String sDropTable = "drop table " + sTempTable;
		try {
			// 检查数据库连接池是否有效
			if (jdbcTemplate == null) {
				logUtil.warn("jdbcTemplate is null 数据库连接失败");
				return -1;
			}
			jdbcTemplate.execute(sDropTable);
		} catch (DataAccessException sqlExce) {
			logUtil.error("删除数据库表失败!!" + sqlExce.toString());
			return -1;
		}
		return 1;
	}

	/**
	 * 获取事务管理器
	 * 
	 * @param dataSource
	 *            配置的数据源
	 * @return 事务管理器
	 */
	public DataSourceTransactionManager getTranscationManage() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(this.dataSource);
		return transactionManager;
	}

	/**
	 * 开启一个事务
	 * 
	 * @param transactionManager
	 *            事务管理类
	 * @param ipropagationBehavior
	 *            事务的传播行为(DefaultTransactionDefinition中定义了)
	 * @return 事务状态信息
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
	 * 提交一个事务
	 * 
	 * @param transactionManager
	 *            事务管理类
	 * @param tranStatus
	 *            事务状态信息
	 * @return 执行结果 负为失败
	 */
	public int transcationCommit(
			DataSourceTransactionManager transactionManager,
			TransactionStatus tranStatus) {
		try {
			transactionManager.commit(tranStatus);
		} catch (TransactionException transExcepiton) {
			logUtil.error(" 事务提交出错! 异常信息:["+transExcepiton.toString()+"]");
			return -1;
		}
		return 1;
	}

	/**
	 * 回滚一个事务
	 * 
	 * @param transactionManager
	 *            事务管理类
	 * @param tranStatus
	 *            事务状态信息
	 * @return 执行结果 负为失败
	 */
	public int transcationrollback(
			DataSourceTransactionManager transactionManager,
			TransactionStatus tranStatus) {
		try {
			transactionManager.rollback(tranStatus);
		} catch (TransactionException transExcepiton) {
			logUtil.error(" 事务回滚出错! 异常信息:["+transExcepiton.toString()+"]");
			return -1;
		}
		return 1;
	}

	/**
	 * 获取字段名
	 * 
	 * @return 字段名
	 */
	public List<String> getFieldName() {
		return this.alFieldName;
	}
	
	/**
	 * 根据查询语句获取查询结果对应的类型
	 * 
	 * @param sQuerySql
	 *            查询语句
	 * @return 查询结果类型
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
				logUtil.warn("没有找到" + sTableName + "主键信息!");
				return tableKeyList;
			}
			while (rs.next()) {
				//System.out.println("键名:"+rs.getString("index_name"));
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
			logUtil.error("获取表[" + sTableName + "]的主键出错! 异常信息:["+e.toString()+"]");
			throw new RuntimeException(e);
		} finally {
			try {
				if (!connection.isClosed()) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				logUtil.error("关闭数据库连接发生异常:" + e.toString());
			}
		}
	}
	
	/**
	 * 解释sql成正确的语句得到只要数据结果类型的语句
	 * 
	 * @return 处理后的sql
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
