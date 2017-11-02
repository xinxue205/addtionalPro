/**
 * 
 */
package ormlite;

/**
 * @author wuxinxue
 * @time 2015-7-13 上午11:09:46
 * @copyright hnisi
 */
public class UserDao  
{  
    private Context context;  
    private Dao<User, Integer> userDaoOpe;  
    private DatabaseHelper helper;  
  
    public UserDao(Context context)  
    {  
        this.context = context;  
        try  
        {  
            helper = DatabaseHelper.getHelper(context);  
            userDaoOpe = helper.getDao(User.class);  
        } catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 增加一个用户 
     * @param user 
     */  
    public void add(User user)  
    {  
        try  
        {  
            userDaoOpe.create(user);  
        } catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
  
    }//...other operations  
  
  
}
