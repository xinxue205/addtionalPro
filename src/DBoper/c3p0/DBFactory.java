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
public class DBFactory { 
	private static Logger logUtil = Logger.getLogger(DBFactory.class); // ��־��¼��
	private static ComboPooledDataSource cpds = null;
	private static int iMinPoolSize = 20;
	private static int iMaxPoolSize = 250;
	private static int iMaxIdleTime = 3000;
	private static String sPropertiesFile = "/conf/jdbc.properties";

	static {
		try {
			// ���ļ�/conf/jdbc.properties��ȡ���ݿ�������Ϣ
			Properties props = new Properties();
			InputStream inputStream = DBFactory.class.getResourceAsStream(sPropertiesFile);//new FileInputStream(sPropertiesFile);//
			props.load(inputStream);
			cpds = new ComboPooledDataSource();
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
//			PreparedStatement stmt  = conn.prepareStatement("SET LOCK MODE TO WAIT 10") ;
//			stmt.execute();
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
	 * ִ�в�ѯ����ȡ���� �����ݿ����� �޲���
	 * 
	 * @param sqlStr
	 * @return
	 */
	public static Map queryForMap(String sqlStr)throws Exception {
		return queryForMap(sqlStr, null, null);
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
	public static Map queryForMap(String sqlStr,
			List<Object> values, List<Object> valueTypes)throws Exception{
		Connection conn = getConnection();
		if (conn == null) {
			logUtil.error("��ȡ���ݿ�����Ϊnull");
			return null;
		}
		Map result = new HashMap();
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
}
