package ormlite;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class PersistenceDao {
	ConnectionSource connectionSource;
	public PersistenceDao(Properties cfg) throws SQLException{
		String databaseUrl = cfg.getProperty("dbUrl", "${dbUrl}");
		String username = cfg.getProperty("dbUser", "${dbUser}");
		String password = cfg.getProperty("dbPass", "${dbPass}");
		connectionSource = new JdbcConnectionSource(databaseUrl, username, password);
	}

	public <T> Dao<T, String> createDao(Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.createDao(connectionSource, targetClass);
		try{
			if(!dao.isTableExists())
				TableUtils.createTable(connectionSource, targetClass);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dao;
	}
	
	public <T extends AbsModel> T saveOrUpdate(T target, Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		if(dao.idExists(target.getId()))
			dao.update(target);
		else
			dao.create(target);
		
		return target;
	}
	
	public <T extends AbsModel> void delete(T target, Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		target.setDeleted(true);
		dao.update(target);
	}
	
	public <T extends AbsModel> List<T> queryForAll(Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		return dao.queryForAll();
	}
	
	public <T extends AbsModel> List<T> query(String fieldName, String value, Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		return dao.queryForEq(fieldName, value);
	}
	
	public <T extends AbsModel> T queryById(String id, Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		return dao.queryForId(id);
	}
	
	public <T extends AbsModel> List<T> query(Map<String, Object> fieldValues, Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		return dao.queryForFieldValuesArgs(fieldValues);
	}
	
	public <T extends AbsModel> T queryFirst(String fieldName, String value, Class<T> targetClass) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		List<T> ls = dao.queryForEq(fieldName, value);
		if(ls.size() > 0 )
			return ls.get(0);
		return null;
	}
	
	public <T extends AbsModel> int exec(Class<T> targetClass, String statement, String... value) throws SQLException{
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		return dao.executeRaw(statement, value);
	}
	
	public <T extends AbsModel> QueryBuilder<T,String> queryBuilder(Class<T> targetClass){
		Dao<T, String> dao = DaoManager.lookupDao(connectionSource, targetClass);
		return dao.queryBuilder(); 
	}
}
