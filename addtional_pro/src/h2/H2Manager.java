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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 下午3:36:22
 * @Description
 * @version 1.0 Shawn create
 */
public class H2Manager {


	private static final LogUtil logUtil = new LogUtil(H2Manager.class); // 日志记录器
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
	 * 返回实例
	 */
	public static H2Manager getInstance() {
		if (instance == null) {
			instance = new H2Manager();
		}
		return instance;
	}

	/**
	 * 获取连接池
	 */
	public Connection getConnection() {
		logUtil.info("开始获取内存数据库链接……");
		Connection conn = null;
		try {
			conn = cp.getConnection();
		} catch (SQLException e) {
			logUtil.error("获取内存数据库链接失败", e);
		}
		logUtil.info("获取内存数据库链接结束");
		return conn;
	}

	public void closeConnection(ResultSet rs, Statement st, Connection conn) {
		logUtil.info("释放数据库链接");
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			logUtil.error("释放数据库资源出错：", e);
		}
	}

	/**
	 * 根据传入的sql语句(取记录条数),执行相应的查询操作
	 * 
	 * @param strSql
	 *            完整的sql语句select count(*) from ** where ...
	 * @return int 返回表中符合条件的记录数
	 */
	public int hasData(String sql) throws Exception {

		int _count = 0;
		Connection _conn = null;
		Statement _st = null;
		ResultSet _rs = null;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("获取内存数据库链接为空！");
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
			throw new Exception("执行查询操作失败" + e.getMessage());
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
				throw new Exception("释放数据库资源失败");
			}
		}
	}

	/**
	 * 返回查询结果
	 * 
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数数组
	 * @return 查询结果二维数组
	 * @throws SQLException
	 */
	public String[][] queryDB(String sql) throws Exception {
		logUtil.info("开始查询内存数据库，查询sql：" + sql);
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
				logUtil.error("获取内存数据库链接为空！");
				return null;
			}
			_st = _conn.createStatement();
			_rs = _st.executeQuery(sql);

			int n = 0;
			while (_rs.next()) {
				// 存储每条记录字段内容
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
			throw new Exception("执行查询操作失败" + e.getMessage());
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
				throw new Exception("释放数据库资源失败");
			}
		}
	}

	/**
	 * 返回查询结果
	 * 
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数数组
	 * @param nCols
	 *            输出列数
	 * @return 查询结果二维数组
	 * @throws SQLException
	 */
	public String[][] queryDB(String sql, int nCols) throws Exception {
		logUtil.info("开始查询内存数据库，查询sql：" + sql);
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
				logUtil.error("获取内存数据库链接为空！");
				return null;
			}
			_st = _conn.createStatement();
			_rs = _st.executeQuery(sql);

			int n = 0;
			while (_rs.next()) {
				// 存储每条记录字段内容
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
			throw new Exception("执行查询操作失败", e);
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
				throw new Exception("释放数据库资源失败");
			}
		}
	}

	/**
	 * 执行insert操作,返回是否成功
	 * 
	 * @param sql
	 *            insert语句
	 * @return 成功与否
	 * @throws SQLException
	 */
	public boolean insertDB(String sql) throws Exception {
		logUtil.info("开始执行内存数据库插入操作，sql：" + sql);
		Connection _conn = null;
		Statement _st = null;
		int nFlag = -1;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("获取内存数据库链接为空！");
				return false;
			}
			_st = _conn.createStatement();
			nFlag = _st.executeUpdate(sql);
			return nFlag >= 0 ? true : false;
		}

		catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("执行插入操作失败", e);
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
				throw new Exception("释放数据库资源失败");
			}
		}
	}

	/**
	 * 执行update操作,返回是否成功
	 * 
	 * @param sql
	 *            update语句
	 * @return 成功与否
	 * @throws SQLException
	 */
	public boolean updateDB(String sql) throws Exception {
		logUtil.info("开始更新内存数据库，更新sql：" + sql);
		Connection _conn = null;
		Statement _st = null;
		int nFlag = -1;
		try {
			_conn = instance.getConnection();
			if (_conn == null) {
				logUtil.error("获取内存数据库链接为空！");
				return false;
			}
			_st = _conn.createStatement();
			nFlag = _st.executeUpdate(sql);
			return nFlag >= 0 ? true : false;
		}

		catch (Exception e) {
			logUtil.error("sql=" + sql, e);
			throw new Exception("执行插入操作失败", e);
		} finally {
			this.closeConnection(null, _st, _conn);
		}
	}

	/**
	 * 创建内存数据库表或者索引
	 * 
	 * @param sql
	 * @return
	 */
	public int createTableOrIndex(String sql) {
		logUtil.info("开始创建内存数据库表或者索引，sql：" + sql);
		int result = 0;
		Connection conn = null;
		Statement st = null;
		try {
			conn = this.getConnection();
			st = conn.createStatement();
			st.execute(sql);
		} catch (Exception e) {
			logUtil.error("创建表失败：", e);
		} finally {
			this.closeConnection(null, st, conn);
		}
		return result;
	}

	/**
	 * 执行查询
	 * 
	 * @param sqlStr
	 * @return
	 */
	public List queryForList(String sqlStr) throws SQLException {
		logUtil.info("开始查询内存数据库，查询sql：" + sqlStr);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object> values = null;
		List<Object> valueTypes = null;
		if (sqlStr == null) {
			logUtil.error("SQL语句不能为空！");
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
				logUtil.error("获取数据库连接为空");
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
			// logUtil.error("执行查询:[SQL]" + getPreparedSQL(sqlStr, values));
		} catch (SQLException e) {
			logUtil.error("执行查询出错:[SQL]" + this.getPreparedSQL(sqlStr, values),
					e);
			throw new SQLException(e);
		} catch (UnsupportedEncodingException e) {
			logUtil.error("编码格式不对：", e);
		} finally {
			this.closeConnection(rs, stmt, conn);
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
	public int update(String sqlStr, List<Object> values,
			List<Object> valueTypes) {
		logUtil.info("开始更新内存数据库，更新sql：" + sqlStr);
		Connection conn = null;
		PreparedStatement stmt = null;
		int iNum = 0;
		try {
			conn = this.getConnection();
			if (conn == null) {
				logUtil.error("获取内存数据连接为空");
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
					logUtil.error("参数列表个数与参数类型个数不一致！");
				}
			}
			iNum = stmt.executeUpdate();
			logUtil.info("更新结果："+iNum);
		} catch (SQLException e) {
			e.printStackTrace();
			logUtil.error("执行更新操作失败:[SQL]" + getPreparedSQL(sqlStr, values), e);
		} catch (Exception e1) {
			logUtil.error("执行更新操作出错:[SQL]" + getPreparedSQL(sqlStr, values),e1);
		} finally {
			this.closeConnection(null, stmt, conn);
		}
		return iNum;
	}

	/**
	 * 获得PreparedStatement向数据库提交的SQL语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private String getPreparedSQL(String sql, List<Object> params) {
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