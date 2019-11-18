package mybatis;

import org.apache.ibatis.session.SqlSession;

public class TestMybatis {

	 public static void main(String[] args) throws Exception{
        SqlSession sqlSession = null;
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            //这里的RoleMapper.class是一个接口类来的；
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setRoleName("testName");
            role.setNote("testnote");
            roleMapper.insertRole(role);
//            roleMapper.deleteRole(1L);
            sqlSession.commit();
            Role user=    roleMapper.getRole(2L);
        }catch(Exception e){
            System.err.println(e.getMessage());
            sqlSession.rollback();
        }
        finally{
            if(sqlSession != null)
                sqlSession.close();
        }
    }
}
