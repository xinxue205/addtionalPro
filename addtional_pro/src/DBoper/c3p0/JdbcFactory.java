package DBoper.c3p0;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * -------------------------------------------------------
 * Copyright (c) 2009, �����������ϵͳ���޹�˾
 * All rights reserved.
 * 
 * FileName��
 * Description��c3p0���ӳأ����ݿ������
 * History��
 * Version 		Date					Author          Desc
 * 1.0 			2010.05.25   	 		gjf        		������   
 * 1.1			2011.06.14				renshuliang		����queryForInt,queryForMap����,ȥ�����������ķ���
 * 1.2			2011.09.10				renshuliang		��������Դ��ȡ����getDataSource()
 * 1.3			2012.06.18				������			�޸����ݿ����ã�iMinPoolSize,iMaxIdleTime,cpds.setAcquireIncrement(5);
 * 2.0			2012.08.20				������			�޸����������Ϊ150
 * 2.1			2013.11.26				��ǿ				ҵ�����󣬽�������������ӵ� 250
 * -------------------------------------------------------
 */
public class JdbcFactory { 
	private static Logger logUtil = Logger.getLogger(JdbcFactory.class); // ��־��¼��
	private static ComboPooledDataSource cpds = null;
	private static int iMinPoolSize = 20;
	private static int iMaxPoolSize = 250;
	private static int iMaxIdleTime = 3000;
	private static String sPropertiesFile = "jdbc.properties";

	static {
		try {
			cpds = new ComboPooledDataSource();
			// ���ļ�/conf/jdbc.properties��ȡ���ݿ�������Ϣ
			Properties props = new Properties();
			InputStream inputStream = JdbcFactory.class.getResourceAsStream(sPropertiesFile);
			props.load(inputStream);
			cpds.setDriverClass(props.getProperty("jdbc.driverClassName"));
			cpds.setJdbcUrl(props.getProperty("jdbc.url"));
			cpds.setUser(props.getProperty("jdbc.username"));
			cpds.setPassword(props.getProperty("jdbc.password"));
			
			// ���ӳ��б�������С������
			cpds.setMinPoolSize(iMinPoolSize);
			// ���ӳ��б��������������
			cpds.setMaxPoolSize(iMaxPoolSize);
			// ��ʼ��ʱ��ȡ��������
			cpds.setInitialPoolSize(iMinPoolSize);
			// ������ʱ��,ָ������δʹ�������ӱ���������Ϊ0������������
			cpds.setMaxIdleTime(iMaxIdleTime);
			// �����ӳ��е����Ӻľ���ʱ��c3p0һ��ͬʱ��ȡ��������
			cpds.setAcquireIncrement(5);
			// �����ڴ����ݿ��ȡ������ʧ�ܺ��ظ����ԵĴ���
			cpds.setAcquireRetryAttempts(20);
			// �����Ϊtrue��ô��ȡ�����ӵ�ͬʱ��У�����ӵ���Ч��
			cpds.setTestConnectionOnCheckin(true);
			// �����������Ӳ��Զ�ִ�еĲ�����䡣��ʹ�����Ӳ��Ե���������һ������߲����ٶ�
			cpds.setTestConnectionOnCheckout(true);
			cpds.setAutomaticTestTable("c3p0Test");
			cpds.setIdleConnectionTestPeriod(18000);
			cpds.setCheckoutTimeout(5000);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.error("��ȡ����ʧ�ܣ�", e);
			cpds = null;
		}
	}

	/**
	 * ��ȡ���ݿ�����
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
			logUtil.error("��ȡ���ӳ���", e);
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	public static DataSource getDataSource(){
		return cpds;
	}

	/**
	 * ִ�в�ѯ��������conn��������
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
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			return null;
		}
		if(sqlStr == null){
			logUtil.error("SQL��䲻��Ϊ�գ�");
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
			// logUtil.error("ִ�в�ѯ:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (Exception e) {
			logUtil.error("ִ�в�ѯ����:[SQL]" + getPreparedSQL(sqlStr, values), e);
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
				logUtil.error("�ر����ӳ���:", ex);
				result = null;
			}
		}
		return result;
	}

	/**
	 * ִ�в�ѯ��������conn����������
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
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			return null;
		}
		if(sqlStr == null){
			logUtil.error("SQL��䲻��Ϊ�գ�");
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
			// logUtil.error("ִ�в�ѯ:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (Exception e) {
			logUtil.error("ִ�в�ѯ����:[SQL]" + getPreparedSQL(sqlStr, values), e);
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
				logUtil.error("�ر����ӳ���:", ex);
				result = null;
			}
		}
		return result;
	}

	/**
	 * ִ�в�ѯ����������conn��������
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
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			return null;
		}
		if(sqlStr == null){
			logUtil.error("SQL��䲻��Ϊ�գ�");
		}
		List result = new ArrayList();
		try {
			result = queryForList(conn, sqlStr, values, valueTypes);
		} catch (Exception ex) {
			result = null;
			logUtil.error("ִ�в�ѯ����:[SQL]" + sqlStr, ex);
			getPreparedSQL(sqlStr, values);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				logUtil.error("�ر����ӳ���:", e);
				result = null;
			}
		}
		return result;
	}

	/**
	 * ִ�в�ѯ
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static List<Object> queryForList(String sqlStr) {
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			return null;
		}
		List<Object> result = new ArrayList<Object>();
		try {
			result = queryForList(conn, sqlStr);
		} catch (Exception ex) {
			result = null;
			logUtil.error("ִ�в�ѯ����:[SQL]" + sqlStr, ex);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				logUtil.error("�ر����ӳ���:", e);
				result = null;
			}
		}
		return result;
	}

	/**
	 * ִ�и��²�����������conn��������
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
			logUtil.error("��������Ϊnull");
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
					logUtil.error("�����б������������͸�����һ�£�");
				}
			}
			iNum = stmt.executeUpdate();
		} catch (SQLException e) {
			logUtil.error("ִ�и��²���ʧ��:[SQL]" + getPreparedSQL(sqlStr, values), e);
		} catch (Exception e1) {
			logUtil.error("ִ�и��²�������:[SQL]" + getPreparedSQL(sqlStr, values), e1);
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
	 * ִ�и��²�������������conn��������
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
			logUtil.error("��ȡ��������Ϊnull");
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
			// logUtil.error("ִ�и��²���ʧ��:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (SQLException e) {
			logUtil.error("ִ�и��²���ʧ��:[SQL]" + sqlStr, e);
			return false;
		} catch (Exception e1) {
			logUtil.error("ִ�и��²�������:[SQL]" + getPreparedSQL(sqlStr, values), e1);
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
				logUtil.error("�ر����ӳ���", e);
				return false;
			}
		}
		return true;
	}

	/**
	 * ִ�и��²�����������conn
	 * 
	 * @param conn
	 * @param sqlStr
	 * @return
	 */
	public static int update(Connection conn, String sqlStr) {
		return update(conn, sqlStr, null, null);
	}

	/**
	 * ִ�и��²���
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static boolean update(String sqlStr) {
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("��ȡ��������Ϊnull");
			return false;
		}
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			logUtil.error("ִ�и��²���ʧ��:[SQL]" + sqlStr, e);
			return false;
		} catch (Exception e1) {
			logUtil.error("ִ�и��²�������:[SQL]" + sqlStr, e1);
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				logUtil.error("�ر�����ʧ��" + sqlStr, e);
			}
		}
		return true;
	}

	/**
	 * ���PreparedStatement�����ݿ��ύ��SQL���
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private static String getPreparedSQL(String sql, List<Object> params) {
		// 1 ���û�в�����˵�����Ƕ�̬SQL���
		int paramNum = 0;
		if (null != params)
			paramNum = params.size();
		if (1 > paramNum)
			return "";
		// 2 ����в��������Ƕ�̬SQL���
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
	 * ִ�в�ѯ������������conn��������,ֻ������ͳ�ƣ�������(select count(*) from ... [where ...])
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
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			throw new SQLException("��ȡ���ݿ�����Ϊnull");
		}
		if(sqlStr == null){
			logUtil.error("SQL����Ϊnull");
			throw new Exception("SQL����Ϊnull");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				throw new Exception("�����б������������͸�����һ�£�");
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
			int nrOfColumns = rsmd.getColumnCount();	//�����������
			if(nrOfColumns != 1){
				throw new Exception("SQL��ѯ���Ľ����ֻһ�У�");
			}
			if(rs.next()){
				result = Integer.valueOf(rs.getString(1));
			}
			if(rs.next()){
				throw new Exception("SQL��ѯ���Ľ����ֻһ�У�");
			}
		} catch (Exception e) {
			logUtil.error("ִ�в�ѯ����:[SQL]" + getPreparedSQL(sqlStr, values), e);
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
				logUtil.error("�ر����ݿ��������������:", ex);
			}
		}
		return result;
	}

	/**
	 * ִ�в�ѯ������������conn����������
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
	 * ִ�в�ѯ��������������conn��������
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
				logUtil.error("�ر����ݿ�ʧ�ܣ�", e);
			}
		}
		return iSum;
	}
	
	/**
	 * ִ�в�ѯ���� �����ݿ����� �޲���
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static int queryForInt(String sqlStr)throws Exception {
		return queryForInt(sqlStr, null, null);
	}
	
	/**
	 * ִ�в�ѯ��������conn��������
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
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			throw new Exception("��ȡ���ݿ�����Ϊnull");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				throw new Exception("�����б������������͸�����һ�£�");
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
				throw new Exception("SQL��ѯ���Ľ����ֻһ�У�");
			}
		}catch (Exception e) {
			logUtil.error("ִ�в�ѯ����:[SQL]" + getPreparedSQL(sqlStr, values), e);
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
				logUtil.error("�ر����ݿ��������������:", ex);
			}
		}
		return result;
	}
	
	/**
	 * ִ�в�ѯ��������conn����������
	 * 
	 * @param conn
	 * @param sqlStr
	 * @return
	 */
	public static Map queryForMap(Connection conn, String sqlStr)throws Exception {
		return queryForMap(conn, sqlStr,null,null);
	}
	
	/**
	 * ִ�в�ѯ����ȡ���� �����ݿ����� �в���
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
				logUtil.error("�ر����ݿ�ʧ�ܣ�", e);
			}
		}
		return mQuery;
	}
	
	/**
	 * ִ�в�ѯ����ȡ���� �����ݿ����� �޲���
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static Map queryForMap(String sqlStr)throws Exception {
		return queryForMap(sqlStr, null, null);
	}
	
	/**
	 * �����������ݿ⣬����ÿ�����ݵ���Ӱ������
	 * @param sql
	 * @return 0--�ɹ�, -1--ʧ��
	 */
	public static int batchUpdate(String... sql){
		if(sql.length>100){
			logUtil.error("��ȡ��������Ϊnull");
			return -1;
		}
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("��ȡ��������Ϊnull");
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
			logUtil.error("ִ�и��²���ʧ��:[SQL]" , e);
			return -1;
		} catch (Exception e1) {
			logUtil.error("ִ�и��²�������:[SQL]", e1);
			return -1;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				logUtil.error("�ر�����ʧ��", e);
			}
		}
		return 0;
	}
}
