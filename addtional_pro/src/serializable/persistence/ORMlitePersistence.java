package serializable.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;


/**
 * 持久化到数据库
 * @author zhangshisong
 *
 * @param <T>
 */
public class ORMlitePersistence<T> implements IPersistence<T> {

	//PersistenceDao dao;
	Dao<MapEntry, String> baseDao;
	Class<T> clasz;
	Kryo kryo = new Kryo();
	
	public ORMlitePersistence(ConnectionSource conSource, String tableName, Class<T> clasz) throws SQLException{
		this.clasz = clasz;
		
		DatabaseTableConfig<MapEntry> config = new DatabaseTableConfig<MapEntry>();
		config.setTableName(tableName);
		config.setDataClass(MapEntry.class);
		baseDao = DaoManager.createDao(conSource, config);
		if(!baseDao.isTableExists())
			TableUtils.createTable(conSource, config);
	}
	
	public Map<String, T> getAll() {
		Map<String, T> map = new HashMap<String, T>();
		try {
			//List<T> ls = dao.query("DELETED", Boolean.FALSE, clasz);
			List<MapEntry> ls = baseDao.queryForAll();
			for(MapEntry entry : ls){
				map.put(entry.key, deserialize(entry.value));
			}
			return map;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public T get(String key) {
		try {
//			Map<String, Object> fieldValues = new HashMap<String, Object>();
//			fieldValues.put("DELETED", Boolean.FALSE);
//			fieldValues.put(keyName, key);
//			List<T> ls = dao.query(fieldValues, clasz);
//			if(ls.size() > 0)
//				return ls.get(0);
			MapEntry entry = baseDao.queryForId(key);
			if(entry != null){
				return deserialize(entry.value);
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(String key, T value) {
		//String key = value.getId();
		try {
		//	dao.saveOrUpdate(value, clasz);
			MapEntry entry = new MapEntry();
			entry.key = key;
			entry.value = serialize(value);
			baseDao.create(entry);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(String key, T value) {
		try {
			//dao.saveOrUpdate(value, clasz);
			MapEntry entry = baseDao.queryForId(key);
			if(entry == null)
				add(key, value);
			else{
				entry.value = serialize(value);
				baseDao.update(entry);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String key) {
		try {
			//T o = get(key);
			//dao.delete(o, clasz);
			baseDao.deleteById(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void clear() {
		//for(T t: getAll().values()){
			try {
				//dao.delete(t, clasz);
				baseDao.deleteBuilder().delete();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//}
	}
	
	private T deserialize(byte[] data){
		ByteBufferInput bbi = new ByteBufferInput(data);
		return kryo.readObject(bbi, clasz);
	}
	
	private byte[] serialize(T value){
		ByteBufferOutput bbo = new ByteBufferOutput(1024 * 1024);
		kryo.writeObject(bbo, value);
		return bbo.toBytes();
	}
	
	static class MapEntry{
		@DatabaseField(columnName = "key", id = true)
		String key;
		
		@DatabaseField(columnName = "VALUE", dataType = DataType.BYTE_ARRAY, columnDefinition = "BLOB")
		byte[] value;
	}
}
