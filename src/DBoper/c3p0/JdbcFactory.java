package DBoper.c3p0;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * -------------------------------------------------------
 * Copyright (c) 2009, 广州南天电脑系统有限公司
 * All rights reserved.
 * 
 * FileName：
 * Description：c3p0连接池，数据库操作类
 * History：
 * Version 		Date					Author          Desc
 * 1.0 			2010.05.25   	 		gjf        		创建类   
 * 1.1			2011.06.14				renshuliang		增加queryForInt,queryForMap方法,去掉其他方法的泛型
 * 1.2			2011.09.10				renshuliang		增加数据源获取方法getDataSource()
 * 1.3			2012.06.18				任述亮			修改数据库配置，iMinPoolSize,iMaxIdleTime,cpds.setAcquireIncrement(5);
 * 2.0			2012.08.20				任述亮			修改最大链接数为150
 * 2.1			2013.11.26				汤强				业务需求，将最大链接数增加到 250
 * -------------------------------------------------------
 */
public class JdbcFactory { 
	private static Logger logUtil = Logger.getLogger(JdbcFactory.class); // 日志记录器
	private static ComboPooledDataSource cpds = null;
	private static int iMinPoolSize = 20;
	private static int iMaxPoolSize = 250;
	private static int iMaxIdleTime = 3000;
	private static String sPropertiesFile = "jdbc.properties";

	static {
		try {
			cpds = new ComboPooledDataSource();
			// 从文件/conf/jdbc.properties获取数据库连接信息
			Properties props = new Properties();
			InputStream inputStream = JdbcFactory.class.getResourceAsStream(sPropertiesFile);
			props.load(inputStream);
			cpds.setDriverClass(props.getProperty("jdbc.driverClassName"));
			cpds.setJdbcUrl(props.getProperty("jdbc.url"));
			cpds.setUser(props.getProperty("jdbc.username"));
			cpds.setPassword(props.getProperty("jdbc.password"));
			
			// 连接池中保留的最小连接数
			cpds.setMinPoolSize(iMinPoolSize);
			// 连接池中保留的最大连接数
			cpds.setMaxPoolSize(iMaxPoolSize);
			// 初始化时获取连接数量
			cpds.setInitialPoolSize(iMinPoolSize);
			// 最大空闲时间,指定秒内未使用则连接被丢弃。若为0则永不丢弃。
			cpds.setMaxIdleTime(iMaxIdleTime);
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
			cpds.setAcquireIncrement(5);
			// 定义在从数据库获取新连接失败后重复尝试的次数
			cpds.setAcquireRetryAttempts(20);
			// 如果设为true那么在取得连接的同时将校验连接的有效性
			cpds.setTestConnectionOnCheckin(true);
			// 定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度
			cpds.setTestConnectionOnCheckout(true);
			cpds.setAutomaticTestTable("c3p0Test");
			cpds.setIdleConnectionTestPeriod(18000);
			cpds.setCheckoutTimeout(5000);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.error("获取连接失败：", e);
			cpds = null;
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = cpds.getConnection();
			PreparedStatement stmt  = conn.prepareStatement("SET LOCK MODE TO WAIT 10") ;
			stmt.execute();
		} catch (Exception e) {
			logUtil.error("获取连接出错", e);
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	public static DataSource getDataSource(){
		return cpds;
	}

	/**
	 * 执行查询，带连接conn，带参数
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static List queryForList(Connection conn, String sqlStr,
			List<Object> values, List<Object> valueTypes) {
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			return null;
		}
		if(sqlStr == null){
			logUtil.error("SQL语句不能为空！");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				return null;
			} else
				haveCondition = true;
		List result = new ArrayList();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sqlStr,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.clearParameters();
			if (haveCondition) {
				int valueType = 0;
				for (int i = 0; i < values.size(); i++) {
					valueType = (Integer) valueTypes.get(i);
					switch (valueType) {
					case 0:
						stmt.setInt(i + 1, (Integer) values.get(i));
						break;
					case 1:
						stmt.setString(i + 1, (String) values.get(i));
						break;
					case 2:
						stmt.setDouble(i + 1, (Double) values.get(i));
						break;
					case 3:
						stmt.setLong(i + 1, Long
								.valueOf((String) values.get(i)));
						break;
					}
				}
			}
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			while (rs.next()) {
				Map row = new HashMap();
				for (int i = 1; i <= num; i++) {
					String columnType = rsmd.getColumnTypeName(i);// .toUpperCase();
					// String key = rsmd.getColumnName(i).toUpperCase();
					String key = rsmd.getColumnLabel(i);// .toUpperCase();
					Object value = null;
					if (columnType.equalsIgnoreCase("CLOB")) {
						Clob tmpClob = rs.getClob(i);
						if (tmpClob != null) {
							value = tmpClob.getSubString(1, (int) tmpClob
									.length());
						} else
							value = null;
					} else if (columnType.equalsIgnoreCase("BLOB")) {
						Blob blob = rs.getBlob(i);
						value = new String(blob
								.getBytes(1, (int) blob.length()), "iso-8859-1");
					} else
						value = rs.getString(i);
					if (value == null)
						value = "";
					row.put(key, value);
				}
				result.add(row);
			}
			// logUtil.error("执行查询:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (Exception e) {
			logUtil.error("执行查询出错:[SQL]" + getPreparedSQL(sqlStr, values), e);
			result = null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException ex) {
				logUtil.error("关闭连接出错:", ex);
				result = null;
			}
		}
		return result;
	}

	/**
	 * 执行查询，带连接conn，不带参数
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static List queryForList(Connection conn, String sqlStr) {
		List<Object> values = null;
		List<Object> valueTypes = null;
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			return null;
		}
		if(sqlStr == null){
			logUtil.error("SQL语句不能为空！");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				return null;
			} else
				haveCondition = true;
		List result = new ArrayList();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sqlStr,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.clearParameters();
			if (haveCondition) {
				int valueType = 0;
				for (int i = 0; i < values.size(); i++) {
					valueType = (Integer) valueTypes.get(i);
					switch (valueType) {
					case 0:
						stmt.setInt(i + 1, (Integer) values.get(i));
						break;
					case 1:
						stmt.setString(i + 1, (String) values.get(i));
						break;
					case 2:
						stmt.setDouble(i + 1, (Double) values.get(i));
						break;
					case 3:
						stmt.setLong(i + 1, Long
								.valueOf((String) values.get(i)));
						break;
					}
				}
			}
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			while (rs.next()) {
				Map row = new HashMap();
				for (int i = 1; i <= num; i++) {
					String columnType = rsmd.getColumnTypeName(i);// .toUpperCase();
					// String key = rsmd.getColumnName(i).toUpperCase();
					String key = rsmd.getColumnLabel(i);// .toUpperCase();
					Object value = null;
					if (columnType.equalsIgnoreCase("CLOB")) {
						Clob tmpClob = rs.getClob(i);
						if (tmpClob != null) {
							value = tmpClob.getSubString(1, (int) tmpClob
									.length());
						} else
							value = null;
					} else if (columnType.equalsIgnoreCase("BLOB")) {
						Blob blob = rs.getBlob(i);
						value = new String(blob
								.getBytes(1, (int) blob.length()), "iso-8859-1");
					} else
						value = rs.getString(i);
					if (value == null)
						value = "";
					row.put(key, value);
				}
				result.add(row);
			}
			// logUtil.error("执行查询:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (Exception e) {
			logUtil.error("执行查询出错:[SQL]" + getPreparedSQL(sqlStr, values), e);
			result = null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException ex) {
				logUtil.error("关闭连接出错:", ex);
				result = null;
			}
		}
		return result;
	}

	/**
	 * 执行查询，不带连接conn，带参数
	 * 
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static List queryForList(String sqlStr, List<Object> values,
			List<Object> valueTypes) {
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			return null;
		}
		if(sqlStr == null){
			logUtil.error("SQL语句不能为空！");
		}
		List result = new ArrayList();
		try {
			result = queryForList(conn, sqlStr, values, valueTypes);
		} catch (Exception ex) {
			result = null;
			logUtil.error("执行查询出错:[SQL]" + sqlStr, ex);
			getPreparedSQL(sqlStr, values);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				logUtil.error("关闭连接出错:", e);
				result = null;
			}
		}
		return result;
	}

	/**
	 * 执行查询
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static List<Object> queryForList(String sqlStr) {
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			return null;
		}
		List<Object> result = new ArrayList<Object>();
		try {
			result = queryForList(conn, sqlStr);
		} catch (Exception ex) {
			result = null;
			logUtil.error("执行查询出错:[SQL]" + sqlStr, ex);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				logUtil.error("关闭连接出错:", e);
				result = null;
			}
		}
		return result;
	}

	/**
	 * 执行更新操作，带连接conn，带参数
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static int update(Connection conn, String sqlStr,
			List<Object> values, List<Object> valueTypes) {
		int iNum = 0;
		if (conn == null) {
			logUtil.error("数据连接为null");
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sqlStr);
			stmt.clearParameters();
			if (values != null && valueTypes != null){
				if(values.size()==valueTypes.size()){
					for (int i = 0; i < values.size(); i++) {
						int valueType = (Integer) valueTypes.get(i);
						switch (valueType) {
						case 0:
							stmt.setInt(i + 1, Integer.valueOf(values.get(i)
									.toString().trim()));
							break;
						case 1:
							stmt.setString(i + 1, (String) values.get(i));
							break;
						case 2:
							stmt.setDouble(i + 1, Double.valueOf((String) values
									.get(i)));
							break;
						case 3:
							stmt.setLong(i + 1, Long
									.valueOf((String) values.get(i)));
						case 4:
							stmt.setBytes(i + 1, (byte[]) values.get(i));
							break;
						}
					}
				}else{
					logUtil.error("参数列表个数与参数类型个数不一致！");
				}
			}
			iNum = stmt.executeUpdate();
		} catch (SQLException e) {
			logUtil.error("执行更新操作失败:[SQL]" + getPreparedSQL(sqlStr, values), e);
		} catch (Exception e1) {
			logUtil.error("执行更新操作出错:[SQL]" + getPreparedSQL(sqlStr, values), e1);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return iNum;
	}

	/**
	 * 执行更新操作，不带连接conn，带参数
	 * 
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static boolean update(String sqlStr, List<Object> values,
			List<Object> valueTypes) {
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("获取数据连接为null");
			return false;
		}
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sqlStr);
			stmt.clearParameters();
			if (values != null && valueTypes != null)
				for (int i = 0; i < values.size(); i++) {
					int valueType = (Integer) valueTypes.get(i);
					switch (valueType) {
					case 0:
						stmt.setInt(i + 1, Integer.valueOf(String
								.valueOf(values.get(i))));
						break;
					case 1:
						stmt.setString(i + 1, String.valueOf(values.get(i)));
						break;
					case 2:
						stmt.setDouble(i + 1, Double.valueOf(String
								.valueOf(values.get(i))));
						break;
					case 3:
						stmt.setLong(i + 1, Long.valueOf(String.valueOf(values
								.get(i))));
						break;
					}
				}
			stmt.executeUpdate();
			conn.commit();
			// logUtil.error("执行更新操作失败:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (SQLException e) {
			logUtil.error("执行更新操作失败:[SQL]" + sqlStr, e);
			return false;
		} catch (Exception e1) {
			logUtil.error("执行更新操作出错:[SQL]" + getPreparedSQL(sqlStr, values), e1);
			getPreparedSQL(sqlStr, values);
			e1.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				logUtil.error("关闭连接出错", e);
				return false;
			}
		}
		return true;
	}

	/**
	 * 执行更新操作，带连接conn
	 * 
	 * @param conn
	 * @param sqlStr
	 * @return
	 */
	public static int update(Connection conn, String sqlStr) {
		return update(conn, sqlStr, null, null);
	}

	/**
	 * 执行更新操作
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static boolean update(String sqlStr) {
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("获取数据连接为null");
			return false;
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			logUtil.error("执行更新操作失败:[SQL]" + sqlStr, e);
			return false;
		} catch (Exception e1) {
			logUtil.error("执行更新操作出错:[SQL]" + sqlStr, e1);
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				logUtil.error("关闭连接失败" + sqlStr, e);
			}
		}
		return true;
	}

	/**
	 * 获得PreparedStatement向数据库提交的SQL语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private static String getPreparedSQL(String sql, List<Object> params) {
		// 1 如果没有参数，说明不是动态SQL语句
		int paramNum = 0;
		if (null != params)
			paramNum = params.size();
		if (1 > paramNum)
			return "";
		// 2 如果有参数，则是动态SQL语句
		StringBuffer returnSQL = new StringBuffer();
		String[] subSQL = sql.split("\\?");
		for (int i = 0; i < paramNum; i++)
			returnSQL.append(subSQL[i]).append("'").append(params.get(i))
					.append("'");
		if (subSQL.length > params.size())
			returnSQL.append(subSQL[subSQL.length - 1]);
		return returnSQL.toString();
	}

	/**
	 * 执行查询数量，带连接conn，带参数,只能用于统计，类似于(select count(*) from ... [where ...])
	 * 
	 * @param conn
	 * @param sqlStr (select count(*) from ... [where ...])
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static int queryForInt(Connection conn,String sqlStr,
			List<Object> values, List<Object> valueTypes)throws Exception{
				
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			throw new SQLException("获取数据库连接为null");
		}
		if(sqlStr == null){
			logUtil.error("SQL操作为null");
			throw new Exception("SQL操作为null");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				throw new Exception("参数列表个数与参数类型个数不一致！");
			} else
				haveCondition = true;
		int result = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sqlStr,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.clearParameters();
			if (haveCondition) {
				int valueType = 0;
				for (int i = 0; i < values.size(); i++) {
					valueType = (Integer) valueTypes.get(i);
					switch (valueType) {
					case 0:
						stmt.setInt(i + 1, (Integer) values.get(i));
						break;
					case 1:
						stmt.setString(i + 1, (String) values.get(i));
						break;
					case 2:
						stmt.setDouble(i + 1, (Double) values.get(i));
						break;
					case 3:
						stmt.setLong(i + 1, Long
								.valueOf((String) values.get(i)));
						break;
					}
				}
			}
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int nrOfColumns = rsmd.getColumnCount();	//结果集的列数
			if(nrOfColumns != 1){
				throw new Exception("SQL查询出的结果不只一列！");
			}
			if(rs.next()){
				result = Integer.valueOf(rs.getString(1));
			}
			if(rs.next()){
				throw new Exception("SQL查询出的结果不只一行！");
			}
		} catch (Exception e) {
			logUtil.error("执行查询出错:[SQL]" + getPreparedSQL(sqlStr, values), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException ex) {
				logUtil.error("关闭数据库操作或结果集出错:", ex);
			}
		}
		return result;
	}

	/**
	 * 执行查询数量，带连接conn，不带参数
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static int queryForInt(Connection conn, String sqlStr)throws Exception {
		return queryForInt(conn, sqlStr, null, null);
	}
	
	/**
	 * 执行查询数量，不带连接conn，带参数
	 * 
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static int queryForInt(String sqlStr, List<Object> values,
			List<Object> valueTypes)throws Exception {
		Connection conn = null;
		int iSum = 0;
		try{
			conn = getConnection();
			iSum = queryForInt(conn, sqlStr, values, valueTypes);
		}catch(Exception e){
			logUtil.error("", e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(SQLException e){
				logUtil.error("关闭数据库失败！", e);
			}
		}
		return iSum;
	}
	
	/**
	 * 执行查询数量 无数据库连接 无参数
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static int queryForInt(String sqlStr)throws Exception {
		return queryForInt(sqlStr, null, null);
	}
	
	/**
	 * 执行查询，带连接conn，带参数
	 * 
	 * @param conn
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return Map
	 */
	public static Map queryForMap(Connection conn, String sqlStr,
			List<Object> values, List<Object> valueTypes)throws Exception{
		Map result = new HashMap();
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			throw new Exception("获取数据库连接为null");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				throw new Exception("参数列表个数与参数类型个数不一致！");
			} else
				haveCondition = true;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sqlStr,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.clearParameters();
			if (haveCondition) {
				int valueType = 0;
				for (int i = 0; i < values.size(); i++) {
					valueType = (Integer) valueTypes.get(i);
					switch (valueType) {
					case 0:
						stmt.setInt(i + 1, (Integer) values.get(i));
						break;
					case 1:
						stmt.setString(i + 1, (String) values.get(i));
						break;
					case 2:
						stmt.setDouble(i + 1, (Double) values.get(i));
						break;
					case 3:
						stmt.setLong(i + 1, Long
								.valueOf((String) values.get(i)));
						break;
					}
				}
			}
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			if(rs.next()){
				for (int i = 1; i <= num; i++) {
					String columnType = rsmd.getColumnTypeName(i);
					String key = rsmd.getColumnLabel(i);
					String value = null;
					if (columnType.equalsIgnoreCase("CLOB")) {
						Clob tmpClob = rs.getClob(i);
						if (tmpClob != null) {
							value = tmpClob.getSubString(1, (int) tmpClob
									.length());
						} else
							value = null;
					} else if (columnType.equalsIgnoreCase("BLOB")) {
						Blob blob = rs.getBlob(i);
						value = new String(blob
								.getBytes(1, (int) blob.length()), "iso-8859-1");
					} else
						value = rs.getString(i);
					if (value == null)
						value = "";
					result.put(key, value);
				}
			}
			if(rs.next()){
				throw new Exception("SQL查询出的结果不只一行！");
			}
		}catch (Exception e) {
			logUtil.error("执行查询出错:[SQL]" + getPreparedSQL(sqlStr, values), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException ex) {
				logUtil.error("关闭数据库操作或结果集出错:", ex);
			}
		}
		return result;
	}
	
	/**
	 * 执行查询，带连接conn，不带参数
	 * 
	 * @param conn
	 * @param sqlStr
	 * @return
	 */
	public static Map queryForMap(Connection conn, String sqlStr)throws Exception {
		return queryForMap(conn, sqlStr,null,null);
	}
	
	/**
	 * 执行查询，获取对象 无数据库连接 有参数
	 * 
	 * @param sqlStr
	 * @param values
	 * @param valueTypes
	 * @return
	 */
	public static Map queryForMap(String sqlStr, List<Object> values,
			List<Object> valueTypes)throws Exception {
		Connection conn = null;
		Map mQuery = null;
		try{
			conn = getConnection();
			mQuery = queryForMap(conn, sqlStr, values, valueTypes);
		}catch(Exception e){
			logUtil.error("", e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(SQLException e){
				logUtil.error("关闭数据库失败！", e);
			}
		}
		return mQuery;
	}
	
	/**
	 * 执行查询，获取对象 无数据库连接 无参数
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static Map queryForMap(String sqlStr)throws Exception {
		return queryForMap(sqlStr, null, null);
	}
	
	/**
	 * 批量更新数据库，返回每条数据的受影响条数
	 * @param sql
	 * @return 0--成功, -1--失败
	 */
	public static int batchUpdate(String... sql){
		if(sql.length>100){
			logUtil.error("获取数据连接为null");
			return -1;
		}
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("获取数据连接为null");
			return -1;
		}
		if(sql.length==0){
			return -1;
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			for (int i = 0; i < sql.length; i++) {
				stmt.addBatch(sql[i]);
			}
			stmt.executeBatch();
		} catch (SQLException e) {
			logUtil.error("执行更新操作失败:[SQL]" , e);
			return -1;
		} catch (Exception e1) {
			logUtil.error("执行更新操作出错:[SQL]", e1);
			return -1;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				logUtil.error("关闭连接失败", e);
			}
		}
		return 0;
	}
}
