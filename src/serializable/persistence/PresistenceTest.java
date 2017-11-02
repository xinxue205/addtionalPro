package serializable.persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class PresistenceTest {
	
	public static <T> void main(String args[]) throws Exception {
		// TODO Auto-generated method stub
		String tableName = "KNOB_SCHED_JOB_MAP";
		Kryo kryo = new Kryo();
		
		ConnectionSource conSource = new JdbcPooledConnectionSource("", "", "");
		DatabaseTableConfig<MapEntry> config = new DatabaseTableConfig<MapEntry>();
		config.setTableName(tableName);
		config.setDataClass(MapEntry.class);
		Dao<MapEntry, String> baseDao = DaoManager.createDao(conSource, config);
		List<MapEntry> ls = baseDao.queryForAll();
		Map<String, T> map = new HashMap<String, T>();
		for(MapEntry entry : ls){
			T t = (T) deserialize(entry.value, kryo, SchedJob.class);
			map.put(entry.key, (T) deserialize(entry.value, kryo, SchedJob.class));
		}
		System.out.println(map);
	}
	
	private static <T> T deserialize(byte[] data, Kryo kryo, Class<T> clasz){
		ByteBufferInput bbi = new ByteBufferInput(data);
		return kryo.readObject(bbi, clasz);
	}
	
	static class MapEntry{
		@DatabaseField(columnName = "key", id = true)
		String key;
		
		@DatabaseField(columnName = "VALUE", dataType = DataType.BYTE_ARRAY, columnDefinition = "BLOB")
		byte[] value;
	}
}