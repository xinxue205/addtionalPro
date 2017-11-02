package DBoper.c3p0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 * 数据库操作类
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2015年8月13日 上午10:48:32
 */
public class DB {
	
	/**
	 * 数据源对象
	 */
	private static DataSource dataSource = DBFactory.getDataSource();
	/**
	 * 数据库操作对象
	 */
	public static JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	/**
	 * 以二维数据形式返回查询结果
	 * @param sql
	 * @return
	 */
	public static String[][] searchRs(String sql) {
		List list = queryForList(sql);
		String[][] resultArray = new String[list.size()][];
		Iterator its = list.iterator();
		int rowIndex = -1;
		//int colIndex = -1;
		while(its.hasNext()) {
			ListOrderedMap it = (ListOrderedMap)its.next();
			rowIndex++;
			resultArray[rowIndex] = convertMapToArray(it);
		}

		return resultArray;
	}
	
	/**
	 * 以二维数据形式返回查询结果,带参数形式
	 * @param sql 查询语句
	 * @param args 参数数组
	 * @return
	 */
	public static String[][] searchRs(String sql, Object[] args) {
		return convertListToArray(queryForList(sql,args));
	}
	
	/**
	 * 查询结果集,以对象列表的方式返回
	 * @param sql 查询语句
	 * @param args 语句中所带的参数值
	 * @return
	 */
	public static List queryForList(String sql, Object[] args) {
		return jdbcTemplate.queryForList(sql, args);
	}
	
	/**
	 * 查询结果集,以对象列表的方式返回
	 * @param sql 查询语句
	 * @param args 语句中所带的参数值
	 * @return
	 */
	public static List queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}	
	
	/**
	 * 查询数据库,以ListOrderedMap方式返回结果
	 * 
	 * @param sql
	 *            查询语句
	 * @param args
	 *            语句中所带的参数值
	 * @return
	 */
	public static ListOrderedMap queryForMap(String sql, Object[] args) {
		ListOrderedMap map = new ListOrderedMap();
		map = (ListOrderedMap) jdbcTemplate
				.queryForMap(sql, args);
		return map;
	}
	
	/**
	 * 查询数据库,以ListOrderedMap方式返回结果
	 * 
	 * @param sql
	 *            查询语句
	 * @return
	 */
	public static ListOrderedMap queryForMap(String sql) {
		ListOrderedMap map = new ListOrderedMap();
		map = (ListOrderedMap) jdbcTemplate
				.queryForMap(sql);
		
		return map;
	}

	/**
	 * 把一个二维数组的其中两列数据,转换成一个Map,一列为Key, 一列为Value,从第0列开始算
	 * 
	 * @param map 有序Map 
	 * @return
	 */
	public static Map convertArrayToMap(String[][] squareArray, int keyColumm, int valueColumm) {
		Map resultMap = new HashMap();
		if(squareArray != null && squareArray.length > 0) {
			for(int i=0; i<squareArray.length; i++) {
				resultMap.put(squareArray[i][keyColumm], squareArray[i][valueColumm]);
			}
		}
		return resultMap;
	}	
	
	
	/**
	 * 把一个有序Map转化成一个一维数组,如果某一个字段的值为空,则在数组中它的值为字符串常量"null"
	 * 
	 * @param map 有序Map 
	 * @return
	 */
	public static String[] convertMapToArray(ListOrderedMap map) {
		if (map == null) {
			return null;
		}
		int arrayLength = map.size();
		String[] resultArray = new String[arrayLength];

		for (int i = 0; i < arrayLength; i++) {
			resultArray[i] = map.getValue(i) != null ? ("" + map.getValue(i)) : "null";
		}
		return resultArray;
	}
	
	/**
	 * 把一个有序List转化成一个二维数组
	 * 
	 * @param map 有序Map 
	 * @return
	 */
	public static String[][] convertListToArray(List list) {
		String[][] resultArray = new String[list.size()][];
		Iterator its = list.iterator();
		int rowIndex = -1;
		//int colIndex = -1;
		while(its.hasNext()) {
			ListOrderedMap it = (ListOrderedMap)its.next();
			rowIndex++;
			resultArray[rowIndex] = convertMapToArray(it);
		}
		return resultArray;
	}
	
	/**
	 * 以一个字符串数组的方式返回结果的第一个对象
	 * @param sql 查询语句
	 * @param args  语句中所带的参数值
	 * @return
	 */
	public static String[] queryForLineStringArray(String sql, Object[] args) {
		return convertMapToArray(queryForMap(sql, args));
	}
	
	/**
	 * 以一个字符串数组的方式返回结果的第一个对象
	 * @param sql 查询语句
	 * @return
	 */
	public static String[] queryForLineStringArray(String sql) {
		return convertMapToArray(queryForMap(sql));
	}
	
	/**
	 * 查找结果集中一列的数据
	 * @param sql 查询语句
	 * @return
	 */
	public static String[] searchOneCol(String sql, int colIndex) {
		String[][] tempArray = searchRs(sql);
		String[] resultArray = new String[tempArray.length];
		for(int i=0; i<tempArray.length; i++) {
			resultArray[i] = tempArray[i][colIndex];
		}
		return resultArray;
	}
	
	/**
	 * 查找结果集中一列的数据
	 * @param sql 查询语句
	 * @return
	 */
	public static String[] searchOneCol(String sql,  Object[] args,int colIndex) {
		String[][] tempArray = searchRs(sql,args);
		String[] resultArray = new String[tempArray.length];
		for(int i=0; i<tempArray.length; i++) {
			resultArray[i] = tempArray[i][colIndex];
		}
		return resultArray;
	}
	
	
	
	/**
	 * 查找结果集中的某个数据
	 * @param sql 查询语句
	 * @return
	 */
	public static String searchOneData(String sql, int rowIndex, int colIndex) {
		String[][] tempArray = searchRs(sql);
		String result = null;
		if(tempArray != null && tempArray.length > 0) {
			result = tempArray[rowIndex][colIndex];
		}
		
		return result;
	}
	
	/**
	 * 查找结果集中的某个数据
	 * @param sql 查询语句
	 * @param args 参数数组
	 * @return
	 */
	public static String searchOneData(String sql, Object[] args, int rowIndex, int colIndex) {
		String[][] tempArray = searchRs(sql, args);
		String result = null;
		if(tempArray != null && tempArray.length > 0) {
			result = tempArray[rowIndex][colIndex];
		}
		return result;
	}

	/**
	 * 插入数据
	 * @param sql 查询语句
	 * @return
	 */
	public static void insertDB(String sql) {
		jdbcTemplate.execute(sql);
	}
	

	
	/**
	 * 批量插入数据
	 * @param sql 查询语句
	 * @return
	 */
	public static int[] insertDB(String[] sqls) {
		return jdbcTemplate.batchUpdate(sqls);
	}	
	
	/**
	 * 更新数据
	 * @param sql 查询语句
	 * @return
	 */
	public static int updateDB(String sql) {
		return jdbcTemplate.update(sql);
	}	
	
	/**
	 * 更新数据, 带参数
	 * @param sql 查询语句
	 * @return
	 */
	public static int updateDB(String sql, Object[] args) {
		return jdbcTemplate.update(sql, args);
	}	

	/**
	 * 带参数查询SQL，返回整型
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	@Deprecated
	public static int query4Int(String sql, Object... args){
		return 0;
//		return jdbcTemplate.queryForInt(sql, args);
	}
	
	/**
	 * 带参数查询SQL，返回整型
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	@Deprecated
	public static int queryForInt(String sql, Object[] args){
		return 0;
//		return jdbcTemplate.queryForInt(sql, args);
	}

	/**
	 * 带参数查询SQL，返回长整型
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	@Deprecated
	public static long query4Long(String sql, Object... args){
		return 0;
//		return jdbcTemplate.queryForLong(sql, args);
	}
	
	/**
	 * 带参数查询SQL，返回长整型
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	@Deprecated
	public static long queryForLong(String sql, Object[] args){
		return 0;
//		return jdbcTemplate.queryForLong(sql, args);
	}
	
	/**
	 * 带参数查询单条数据，返回Map<String, Object>
	 * @param sql
	 * @param args 参数
	 * @return
	 */
  public static Map<String, Object> query4Map(String sql, Object... args){
		return jdbcTemplate.queryForMap(sql, args);
	}

	/**
	 * 带参数查询单条数据，返回一维数组
	 * @param sql
	 * @return
	 */
	public static Object[] query4Array(String sql, Object... args){
		return map2Array(queryForMap(sql, args));
	}
	
	/**
	 * 带参数查询单条数据，返回一维数组
	 * @param sql
	 * @return
	 */
  public static Object[] queryForArray(String sql, Object[] args){
		return map2Array(queryForMap(sql, args));
	}
	
	/**
	 * 利用 ListOrderedMap 将 Map<String, Object> 数据转换为一维数组
	 * @param map
	 * @return
	 */
	private static Object[] map2Array(Map<String, Object> map) {
	  return ((ListOrderedMap) map).valueList().toArray();
  }

	/**
	 * 带参数查询多条数据，返回List<Map<String, Object>>
	 * @param sql
	 * @param args 参数
	 * @return
	 */
  public static List<Map<String, Object>> query4List(String sql, Object... args){
		return jdbcTemplate.queryForList(sql, args);
	}
  
	/**
	 * 带参数查询多条数据，返回二维数组
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	public static Object[][] query4Arrays(String sql, Object... args){
		return list2Arrays(queryForList(sql, args));
	}
	
	/**
	 * 带参数查询多条数据，返回二维数组
	 * @param sql
	 * @param args 参数
	 * @return
	 */
  public static Object[][] queryForArrays(String sql, Object[] args){
		return list2Arrays(queryForList(sql, args));
	}

	/**
	 * 利用 ListOrderedMap 将 List<Map<String, Object>> 转换为 二维数组
	 * @param list
	 * @return
	 */
	private static Object[][] list2Arrays(List<Map<String, Object>> list) {
		Object[][] result = new Object[list.size()][];
		int i = 0;
		for(Map<String, Object> map : list){
			result[i++] = ((ListOrderedMap)map).valueList().toArray();
		}
	  return result;
  }

	/**
	 * 带参数查询单个数据，返回二维数组中 row 行 col 列的数值
	 * @param row 行号，0 起算
	 * @param col 列号，0 起算
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	public static Object selectSingle(int row, int col, String sql, Object... args){
		return queryForArrays(sql, args)[row][col];
	}
	
	/**
	 * 带参数查询单个数据，返回二维数组中 row 行 col 列的数值
	 * @param row 行号，0 起算
	 * @param col 列号，0 起算
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	public static Object selForSingle(int row, int col, String sql, Object[] args){
		return queryForArrays(sql, args)[row][col];
	}
	
	/**
	 * 带参数更新数据库，返回受影响的数据条数
	 * @param sql
	 * @param args 参数
	 * @return
	 */
	public static int update(String sql, Object... args){
		return jdbcTemplate.update(sql, args);
	}
	
	/**
	 * 批量更新数据库，返回每条数据的受影响条数
	 * @param sql
	 * @return
	 */
	public static int[] batchUpdate(String... sql){
		return jdbcTemplate.batchUpdate(sql);
	}
	

}