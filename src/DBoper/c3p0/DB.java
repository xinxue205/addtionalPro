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
 * ���ݿ������
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2015��8��13�� ����10:48:32
 */
public class DB {
	
	public static void main(String[] args) {
		String querySql = "SELECT * FROM cpu_info WHERE code=?";
		Map map = DB.queryForMap(querySql, new String[]{"300770"});
		System.out.println(map.get("name"));
	}
	
	/**
	 * ����Դ����
	 */
	private static DataSource dataSource = DBFactory.getDataSource();
	/**
	 * ���ݿ��������
	 */
	public static JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	/**
	 * �Զ�ά������ʽ���ز�ѯ���
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
	 * �Զ�ά������ʽ���ز�ѯ���,��������ʽ
	 * @param sql ��ѯ���
	 * @param args ��������
	 * @return
	 */
	public static String[][] searchRs(String sql, Object[] args) {
		return convertListToArray(queryForList(sql,args));
	}
	
	/**
	 * ��ѯ�����,�Զ����б�ķ�ʽ����
	 * @param sql ��ѯ���
	 * @param args ����������Ĳ���ֵ
	 * @return
	 */
	public static List queryForList(String sql, Object[] args) {
		return jdbcTemplate.queryForList(sql, args);
	}
	
	/**
	 * ��ѯ�����,�Զ����б�ķ�ʽ����
	 * @param sql ��ѯ���
	 * @param args ����������Ĳ���ֵ
	 * @return
	 */
	public static List queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}	
	
	/**
	 * ��ѯ���ݿ�,��ListOrderedMap��ʽ���ؽ��
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param args
	 *            ����������Ĳ���ֵ
	 * @return
	 */
	public static ListOrderedMap queryForMap(String sql, Object[] args) {
		ListOrderedMap map = new ListOrderedMap();
		map = (ListOrderedMap) jdbcTemplate
				.queryForMap(sql, args);
		return map;
	}
	
	/**
	 * ��ѯ���ݿ�,��ListOrderedMap��ʽ���ؽ��
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return
	 */
	public static ListOrderedMap queryForMap(String sql) {
		ListOrderedMap map = new ListOrderedMap();
		map = (ListOrderedMap) jdbcTemplate
				.queryForMap(sql);
		
		return map;
	}

	/**
	 * ��һ����ά�����������������,ת����һ��Map,һ��ΪKey, һ��ΪValue,�ӵ�0�п�ʼ��
	 * 
	 * @param map ����Map 
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
	 * ��һ������Mapת����һ��һά����,���ĳһ���ֶε�ֵΪ��,��������������ֵΪ�ַ�������"null"
	 * 
	 * @param map ����Map 
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
	 * ��һ������Listת����һ����ά����
	 * 
	 * @param map ����Map 
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
	 * ��һ���ַ�������ķ�ʽ���ؽ���ĵ�һ������
	 * @param sql ��ѯ���
	 * @param args  ����������Ĳ���ֵ
	 * @return
	 */
	public static String[] queryForLineStringArray(String sql, Object[] args) {
		return convertMapToArray(queryForMap(sql, args));
	}
	
	/**
	 * ��һ���ַ�������ķ�ʽ���ؽ���ĵ�һ������
	 * @param sql ��ѯ���
	 * @return
	 */
	public static String[] queryForLineStringArray(String sql) {
		return convertMapToArray(queryForMap(sql));
	}
	
	/**
	 * ���ҽ������һ�е�����
	 * @param sql ��ѯ���
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
	 * ���ҽ������һ�е�����
	 * @param sql ��ѯ���
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
	 * ���ҽ�����е�ĳ������
	 * @param sql ��ѯ���
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
	 * ���ҽ�����е�ĳ������
	 * @param sql ��ѯ���
	 * @param args ��������
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
	 * ��������
	 * @param sql ��ѯ���
	 * @return
	 */
	public static void insertDB(String sql) {
		jdbcTemplate.execute(sql);
	}
	

	
	/**
	 * ������������
	 * @param sql ��ѯ���
	 * @return
	 */
	public static int[] insertDB(String[] sqls) {
		return jdbcTemplate.batchUpdate(sqls);
	}	
	
	/**
	 * ��������
	 * @param sql ��ѯ���
	 * @return
	 */
	public static int updateDB(String sql) {
		return jdbcTemplate.update(sql);
	}	
	
	/**
	 * ��������, ������
	 * @param sql ��ѯ���
	 * @return
	 */
	public static int updateDB(String sql, Object[] args) {
		return jdbcTemplate.update(sql, args);
	}	

	/**
	 * ��������ѯSQL����������
	 * @param sql
	 * @param args ����
	 * @return
	 */
	@Deprecated
	public static int query4Int(String sql, Object... args){
		return 0;
//		return jdbcTemplate.queryForInt(sql, args);
	}
	
	/**
	 * ��������ѯSQL����������
	 * @param sql
	 * @param args ����
	 * @return
	 */
	@Deprecated
	public static int queryForInt(String sql, Object[] args){
		return 0;
//		return jdbcTemplate.queryForInt(sql, args);
	}

	/**
	 * ��������ѯSQL�����س�����
	 * @param sql
	 * @param args ����
	 * @return
	 */
	@Deprecated
	public static long query4Long(String sql, Object... args){
		return 0;
//		return jdbcTemplate.queryForLong(sql, args);
	}
	
	/**
	 * ��������ѯSQL�����س�����
	 * @param sql
	 * @param args ����
	 * @return
	 */
	@Deprecated
	public static long queryForLong(String sql, Object[] args){
		return 0;
//		return jdbcTemplate.queryForLong(sql, args);
	}
	
	/**
	 * ��������ѯ�������ݣ�����Map<String, Object>
	 * @param sql
	 * @param args ����
	 * @return
	 */
  public static Map<String, Object> query4Map(String sql, Object... args){
		return jdbcTemplate.queryForMap(sql, args);
	}

	/**
	 * ��������ѯ�������ݣ�����һά����
	 * @param sql
	 * @return
	 */
	public static Object[] query4Array(String sql, Object... args){
		return map2Array(queryForMap(sql, args));
	}
	
	/**
	 * ��������ѯ�������ݣ�����һά����
	 * @param sql
	 * @return
	 */
  public static Object[] queryForArray(String sql, Object[] args){
		return map2Array(queryForMap(sql, args));
	}
	
	/**
	 * ���� ListOrderedMap �� Map<String, Object> ����ת��Ϊһά����
	 * @param map
	 * @return
	 */
	private static Object[] map2Array(Map<String, Object> map) {
	  return ((ListOrderedMap) map).valueList().toArray();
  }

	/**
	 * ��������ѯ�������ݣ�����List<Map<String, Object>>
	 * @param sql
	 * @param args ����
	 * @return
	 */
  public static List<Map<String, Object>> query4List(String sql, Object... args){
		return jdbcTemplate.queryForList(sql, args);
	}
  
	/**
	 * ��������ѯ�������ݣ����ض�ά����
	 * @param sql
	 * @param args ����
	 * @return
	 */
	public static Object[][] query4Arrays(String sql, Object... args){
		return list2Arrays(queryForList(sql, args));
	}
	
	/**
	 * ��������ѯ�������ݣ����ض�ά����
	 * @param sql
	 * @param args ����
	 * @return
	 */
  public static Object[][] queryForArrays(String sql, Object[] args){
		return list2Arrays(queryForList(sql, args));
	}

	/**
	 * ���� ListOrderedMap �� List<Map<String, Object>> ת��Ϊ ��ά����
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
	 * ��������ѯ�������ݣ����ض�ά������ row �� col �е���ֵ
	 * @param row �кţ�0 ����
	 * @param col �кţ�0 ����
	 * @param sql
	 * @param args ����
	 * @return
	 */
	public static Object selectSingle(int row, int col, String sql, Object... args){
		return queryForArrays(sql, args)[row][col];
	}
	
	/**
	 * ��������ѯ�������ݣ����ض�ά������ row �� col �е���ֵ
	 * @param row �кţ�0 ����
	 * @param col �кţ�0 ����
	 * @param sql
	 * @param args ����
	 * @return
	 */
	public static Object selForSingle(int row, int col, String sql, Object[] args){
		return queryForArrays(sql, args)[row][col];
	}
	
	/**
	 * �������������ݿ⣬������Ӱ�����������
	 * @param sql
	 * @param args ����
	 * @return
	 */
	public static int update(String sql, Object... args){
		return jdbcTemplate.update(sql, args);
	}
	
	/**
	 * �����������ݿ⣬����ÿ�����ݵ���Ӱ������
	 * @param sql
	 * @return
	 */
	public static int[] batchUpdate(String... sql){
		return jdbcTemplate.batchUpdate(sql);
	}
	

}