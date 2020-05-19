package DBoper.c3p0;

import java.io.File;
import java.io.FileInputStream;
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
public class DBFactory { 
	private static Logger logUtil = Logger.getLogger(DBFactory.class); // 日志记录器
	private static ComboPooledDataSource cpds = null;
	private static int iMinPoolSize = 20;
	private static int iMaxPoolSize = 250;
	private static int iMaxIdleTime = 3000;
	private static String sPropertiesFile = "/conf/jdbc.properties";

	static {
		try {
			// 从文件/conf/jdbc.properties获取数据库连接信息
			Properties props = new Properties();
			InputStream inputStream = DBFactory.class.getResourceAsStream(sPropertiesFile);//new FileInputStream(sPropertiesFile);//
			props.load(inputStream);
			cpds = new ComboPooledDataSource();
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
//			PreparedStatement stmt  = conn.prepareStatement("SET LOCK MODE TO WAIT 10") ;
//			stmt.execute();
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
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			stmt = conn.prepareStatement(sqlStr,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
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
	 * 执行查询，获取对象 无数据库连接 无参数
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static Map queryForMap(String sqlStr)throws Exception {
		return queryForMap(sqlStr, null, null);
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
	public static Map queryForMap(String sqlStr,
			List<Object> values, List<Object> valueTypes)throws Exception{
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("获取数据库连接为null");
			return null;
		}
		Map result = new HashMap();
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
}
