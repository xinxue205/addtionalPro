/**
 * 
 */
package h2;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;

import server.util.LogUtil;


import encode.EncodingFilter;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 ����3:36:22
 * @Description
 * @version 1.0 Shawn create
 */
public class H2Manager {


	private static final LogUtil logUtil = new LogUtil(H2Manager.class); // ��־��¼��
	private static JdbcConnectionPool cp = null;

	private static H2Manager instance = new H2Manager();

	private H2Manager() {

		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:~/app/ATMVH_APP/press");
		ds.setUser("atmvh");
		ds.setPassword("atmvh");
		cp = JdbcConnectionPool.create(ds);
		cp.setMaxConnections(50);

	}

	/**
	 * ����ʵ��
	 */
	public static H2Manager getInstance() {
		if (instance == null) {
			instance = new H2Manager();
		}
		return instance;
	}

	/**
	 * ��ȡ���ӳ�
	 */
	public Connection getConnection() {
		logUtil.info("��ʼ��ȡ�ڴ����ݿ����ӡ���");
		Connection conn = null;
		try {
			conn = cp.getConnection();
		} catch (SQLException e) {
			logUtil.error("��ȡ�ڴ����ݿ�����ʧ��", e);
		}
		logUtil.info("��ȡ�ڴ����ݿ����ӽ���");
		return conn;
	}

	public void closeConnection(ResultSet rs, Statement st, Connection conn) {
		logUtil.info("�ͷ����ݿ�����");
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			logUtil.error("�ͷ����ݿ���Դ����", e);
		}
	}

	/**
	 * ���ݴ����sql���(ȡ��¼����),ִ����Ӧ�Ĳ�ѯ����
	 * 
	 * @param strSql
	 *            ������sql���select count(*) from ** where ...
	 * @return int ���ر��з��������ļ�¼��
	 */
	public int hasData(String sql) throws Exception {

		int _count = 0;
		Connection _conn = null;
		Statement _st = null;
		ResultSet _rs = null;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("��ȡ�ڴ����ݿ�����Ϊ�գ�");
				return 0;
			}
			_st = _conn.createStatement();
			_rs = _st.executeQuery(sql);

			while (_rs.next()) {
				_count = _rs.getInt(1);
			}

			return _count;
		} catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("ִ�в�ѯ����ʧ��" + e.getMessage());
		} finally {
			try {
				if (_rs != null) {
					_rs.close();
				}
				if (_st != null) {
					_st.close();
				}
				if (_conn != null) {
					_conn.close();
				}
			} catch (Exception e) {
				logUtil.error(e);
				throw new Exception("�ͷ����ݿ���Դʧ��");
			}
		}
	}

	/**
	 * ���ز�ѯ���
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param params
	 *            ��������
	 * @return ��ѯ�����ά����
	 * @throws SQLException
	 */
	public String[][] queryDB(String sql) throws Exception {
		logUtil.info("��ʼ��ѯ�ڴ����ݿ⣬��ѯsql��" + sql);
		String[][] _result = null;
		int _count = 0;
		Connection _conn = null;
		Statement _st = null;
		ResultSet _rs = null;
		Collection _list1 = new ArrayList();
		int _cols = 5;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("��ȡ�ڴ����ݿ�����Ϊ�գ�");
				return null;
			}
			_st = _conn.createStatement();
			_rs = _st.executeQuery(sql);

			int n = 0;
			while (_rs.next()) {
				// �洢ÿ����¼�ֶ�����
				Collection _list2 = new ArrayList();
				String tmp = null;
				for (int i = 1; i <= _cols; i++) {
					tmp = _rs.getString(i);
					if (tmp != null) {
						tmp = EncodingFilter.getStrFromDBS(tmp);
					}
					_list2.add(tmp);
				}
				_list1.add(_list2);
				n++;
			}
			if (n == 0) {
				_list1 = null;
			}

			if (_list1 == null) {
				return null;
			}

			_result = new String[_list1.size()][_cols];
			Iterator _it = _list1.iterator();
			int x = 0;
			while (_it.hasNext()) {
				ArrayList _list3 = (ArrayList) _it.next();
				Iterator _it2 = _list3.iterator();
				int y = 0;
				while (_it2.hasNext()) {
					_result[x][y] = (String) _it2.next();
					if (_result[x][y] != null) {
						_result[x][y] = _result[x][y].trim();
					}
					y++;
				}
				x++;
			}

			return _result;
		} catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("ִ�в�ѯ����ʧ��" + e.getMessage());
		} finally {
			try {
				if (_rs != null) {
					_rs.close();
				}
				if (_st != null) {
					_st.close();
				}
				if (_conn != null) {
					_conn.close();
				}
			} catch (Exception e) {
				logUtil.error(e);
				throw new Exception("�ͷ����ݿ���Դʧ��");
			}
		}
	}

	/**
	 * ���ز�ѯ���
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param params
	 *            ��������
	 * @param nCols
	 *            �������
	 * @return ��ѯ�����ά����
	 * @throws SQLException
	 */
	public String[][] queryDB(String sql, int nCols) throws Exception {
		logUtil.info("��ʼ��ѯ�ڴ����ݿ⣬��ѯsql��" + sql);
		String[][] _result = null;
		int _count = 0;
		Connection _conn = null;
		Statement _st = null;
		ResultSet _rs = null;
		Collection _list1 = new ArrayList();
		int _cols = nCols;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("��ȡ�ڴ����ݿ�����Ϊ�գ�");
				return null;
			}
			_st = _conn.createStatement();
			_rs = _st.executeQuery(sql);

			int n = 0;
			while (_rs.next()) {
				// �洢ÿ����¼�ֶ�����
				Collection _list2 = new ArrayList();
				String tmp = null;
				for (int i = 1; i <= _cols; i++) {
					tmp = _rs.getString(i);
					if (tmp != null) {
						tmp = EncodingFilter.getStrFromDBS(tmp);
					}
					_list2.add(tmp);
				}
				_list1.add(_list2);
				n++;
			}
			if (n == 0) {
				_list1 = null;
			}

			if (_list1 == null) {
				return null;
			}

			_result = new String[_list1.size()][_cols];
			Iterator _it = _list1.iterator();
			int x = 0;
			while (_it.hasNext()) {
				ArrayList _list3 = (ArrayList) _it.next();
				Iterator _it2 = _list3.iterator();
				int y = 0;
				while (_it2.hasNext()) {
					_result[x][y] = (String) _it2.next();
					if (_result[x][y] != null) {
						_result[x][y] = _result[x][y].trim();
					}
					y++;
				}
				x++;
			}

			return _result;
		} catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("ִ�в�ѯ����ʧ��", e);
		} finally {
			try {
				if (_rs != null) {
					_rs.close();
				}
				if (_st != null) {
					_st.close();
				}
				if (_conn != null) {
					_conn.close();
				}
			} catch (Exception e) {
				logUtil.error(e);
				throw new Exception("�ͷ����ݿ���Դʧ��");
			}
		}
	}

	/**
	 * ִ��insert����,�����Ƿ�ɹ�
	 * 
	 * @param sql
	 *            insert���
	 * @return �ɹ����
	 * @throws SQLException
	 */
	public boolean insertDB(String sql) throws Exception {
		logUtil.info("��ʼִ���ڴ����ݿ���������sql��" + sql);
		Connection _conn = null;
		Statement _st = null;
		int nFlag = -1;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("��ȡ�ڴ����ݿ�����Ϊ�գ�");
				return false;
			}
			_st = _conn.createStatement();
			nFlag = _st.executeUpdate(sql);
			return nFlag >= 0 ? true : false;
		}

		catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("ִ�в������ʧ��", e);
		} finally {
			try {
				if (_st != null) {
					_st.close();
				}
				if (_conn != null) {
					_conn.close();
				}
			} catch (Exception e) {
				logUtil.error(e);
				throw new Exception("�ͷ����ݿ���Դʧ��");
			}
		}
	}

	/**
	 * ִ��update����,�����Ƿ�ɹ�
	 * 
	 * @param sql
	 *            update���
	 * @return �ɹ����
	 * @throws SQLException
	 */
	public boolean updateDB(String sql) throws Exception {
		logUtil.info("��ʼ�����ڴ����ݿ⣬����sql��" + sql);
		Connection _conn = null;
		Statement _st = null;
		int nFlag = -1;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("��ȡ�ڴ����ݿ�����Ϊ�գ�");
				return false;
			}
			_st = _conn.createStatement();
			nFlag = _st.executeUpdate(sql);
			return nFlag >= 0 ? true : false;
		}

		catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("ִ�в������ʧ��", e);
		} finally {
			this.closeConnection(null, _st, _conn);
		}
	}

	/**
	 * �����ڴ����ݿ���������
	 * 
	 * @param sql
	 * @return
	 */
	public int createTableOrIndex(String sql) {
		logUtil.info("��ʼ�����ڴ����ݿ�����������sql��" + sql);
		int result = 0;
		Connection conn = null;
		Statement st = null;
		try {
			conn = this.getConnection();
			st = conn.createStatement();
			st.execute(sql);
		} catch (Exception e) {
			logUtil.error("������ʧ�ܣ�", e);
		} finally {
			this.closeConnection(null, st, conn);
		}
		return result;
	}

	/**
	 * ִ�в�ѯ
	 * 
	 * @param sqlStr
	 * @return
	 */
	public List queryForList(String sqlStr) throws SQLException {
		logUtil.info("��ʼ��ѯ�ڴ����ݿ⣬��ѯsql��" + sqlStr);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object> values = null;
		List<Object> valueTypes = null;
		if (sqlStr == null) {
			logUtil.error("SQL��䲻��Ϊ�գ�");
		}
		boolean haveCondition = false;
		if (values != null && valueTypes != null)
			if (values.size() != valueTypes.size()) {
				return null;
			} else
				haveCondition = true;
		List result = new ArrayList();
		try {
			conn = this.getConnection();
			if (conn == null) {
				logUtil.error("��ȡ���ݿ�����Ϊ��");
				return null;
			}
			stmt = conn.prepareStatement(sqlStr,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// stmt.clearParameters();
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
		} catch (SQLException e) {
			logUtil.error("ִ�в�ѯ����:[SQL]" + this.getPreparedSQL(sqlStr, values),
					e);
			throw new SQLException(e);
		} catch (UnsupportedEncodingException e) {
			logUtil.error("�����ʽ���ԣ�", e);
		} finally {
			this.closeConnection(rs, stmt, conn);
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
	public int update(String sqlStr, List<Object> values,
			List<Object> valueTypes) {
		logUtil.info("��ʼ�����ڴ����ݿ⣬����sql��" + sqlStr);
		Connection conn = null;
		PreparedStatement stmt = null;
		int iNum = 0;
		try {
			conn = this.getConnection();
			if (conn == null) {
				logUtil.error("��ȡ�ڴ���������Ϊ��");
				return 0;
			}
			stmt = conn.prepareStatement(sqlStr);
			// stmt.clearParameters();
			if (values != null && valueTypes != null) {
				if (values.size() == valueTypes.size()) {
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
							stmt.setDouble(i + 1, Double
									.valueOf((String) values.get(i)));
							break;
						case 3:
							stmt.setLong(i + 1, Long.valueOf((String) values
									.get(i)));
						case 4:
							stmt.setBytes(i + 1, (byte[]) values.get(i));
							break;
						}
					}
				} else {
					logUtil.error("�����б������������͸�����һ�£�");
				}
			}
			iNum = stmt.executeUpdate();
			logUtil.info("���½����"+iNum);
		} catch (SQLException e) {
			e.printStackTrace();
			logUtil.error("ִ�и��²���ʧ��:[SQL]" + getPreparedSQL(sqlStr, values), e);
		} catch (Exception e1) {
			logUtil.error("ִ�и��²�������:[SQL]" + getPreparedSQL(sqlStr, values),e1);
		} finally {
			this.closeConnection(null, stmt, conn);
		}
		return iNum;
	}

	/**
	 * ���PreparedStatement�����ݿ��ύ��SQL���
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private String getPreparedSQL(String sql, List<Object> params) {
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