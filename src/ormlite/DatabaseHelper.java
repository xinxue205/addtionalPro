/**
 * 
 */
package ormlite;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author wuxinxue
 * @time 2015-7-13 上午11:03:21
 * @copyright hnisi
 */
public  class DatabaseHelper  
{  
    private static final String TABLE_NAME = "sqlite-test.db";  
  
    private Map<String, Dao> daos = new HashMap<String, Dao>();  
  
    private DatabaseHelper(Context context)  
    {  
//        super(context, TABLE_NAME, null, 4);  
    }  
  
    public void onCreate(ConnectionSource connectionSource)  
    {  
        try  
        {  
            TableUtils.createTable(connectionSource, User.class);  
        } catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
    }  
  
    public void onUpgrade(ConnectionSource connectionSource, int oldVersion, int newVersion)  
    {  
        try  
        {  
            TableUtils.dropTable(connectionSource, User.class, true);  
            onCreate(connectionSource);  
        } catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
    }  
  
    private static DatabaseHelper instance;  
  
    /** 
     * 单例获取该Helper 
     *  
     * @param context 
     * @return 
     */  
    public static synchronized DatabaseHelper getHelper(Context context)  
    {  
        context = context.getApplicationContext();  
        if (instance == null)  
        {  
            synchronized (DatabaseHelper.class)  
            {  
                if (instance == null)  
                    instance = new DatabaseHelper(context);  
            }  
        }  
  
        return instance;  
    }  
  
    public synchronized Dao getDao(Class clazz) throws SQLException  
    {  
        Dao dao = null;  
        String className = clazz.getSimpleName();  
  
        if (daos.containsKey(className))  
        {  
            dao = daos.get(className);  
        }  
        if (dao == null)  
        {  
            dao = super.getDao(clazz);  
            daos.put(className, dao);  
        }  
        return dao;  
    }  
  
    /** 
     * 释放资源 
     */  
    @Override  
    public void close()  
    {  
        super.close();  
  
        for (String key : daos.keySet())  
        {  
            Dao dao = daos.get(key);  
            dao = null;  
        }  
    }  
  
}
