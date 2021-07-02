package jpa;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import jpa.left.VRDatabaseAttribute;

public class Test {

	static EntityManager entityManager = null;
	
	private String regex = "([\\\\_%])";
	private String replace =  "\\\\$1";
	private String regex2 = "([_%\\\\])";
	
	
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpademo");
		entityManager = emfactory.createEntityManager();
		Test t = new Test();
		String superAdmin = "admin";
		String kettleUser = "admin";
		String text = null;
		String isUsePool = null;
		String conType = null;
		String type = "28";
		try {
			int a = t.queryDataSourceListCount(type, conType, isUsePool, text, kettleUser, superAdmin);
			System.out.println(a);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			emfactory.close();
		}
	}
	
	public  List<Map<String, Object>>   queryDataSourceList(String type,String conType,String isUsePool,String text,String kettleUser,String superAdmin){
		List<Object> paramList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		StringBuilder querySql = new StringBuilder();		
		querySql.append("  select t  from  VRDatabaseAttribute t where t.code='USE_POOLING' and t.name!='"+AppConstant.KETTLE_INSTANCE_NAME +"' ");
		
		if(!kettleUser.equals(superAdmin)){
			   querySql.append(" and (t.modifiedUser = :modified_user  or t.createdUser = :created_user ) ");
				
			   paramList.add(kettleUser);
			   nameList.add("modified_user");
			
			   paramList.add(kettleUser);
			   nameList.add("created_user");
		}
		
		if(null!=type&&!"".equals(type)){
			if(!"0".equals(type)){
			   querySql.append(" and t.idDatabaseType = :id_database_type");
			   paramList.add(Long.parseLong(type));
			   nameList.add("id_database_type");
			}else{
				querySql.append(" and t.idDatabaseType not in ( select e.idDatabaseType from TDatabaseTypeContypeConf e group by e.idDatabaseType ) ");
			}
		}
		
		if(null!=conType&&!"".equals(conType)){
			if(!"0".equals(conType)){
			   querySql.append(" and t.idDatabaseContype = :id_database_contype ");
			   paramList.add(Long.parseLong(conType));
			   nameList.add("id_database_contype");
			}else{
			   querySql.append(" and t.idDatabaseContype not in ( '1' ) ");
			}
		}
		
		if(null!=isUsePool&&!"".equals(isUsePool)){
			if(isUsePool.equals("Y")){
			   querySql.append(" and t.valueStr= :value_str");
			   paramList.add(isUsePool);
			   nameList.add("value_str");
			}else if(isUsePool.equals("N")){
				querySql.append(" and (  t.valueStr=:value_str  or  t.valueStr is null ) ");
				   paramList.add(isUsePool);
				   nameList.add("value_str");
			}
		}
		
		if(null!=text&&!"".equals(text.trim())){
			querySql.append(" and ( t.name like :name  or t.hostName like :host_name"
					+ " or t.databaseName like :database_name or t.userName like :username ) ");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("name");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("host_name");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("database_name");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("username");
			
		}
		querySql.append("  order by t.name   ");
		
		TypedQuery<VRDatabaseAttribute> query = entityManager.createQuery(querySql.toString(), VRDatabaseAttribute.class);
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(nameList.get(i), paramList.get(i));
		}
		List<VRDatabaseAttribute> l = query.getResultList();
		List<Map<String, Object>> result = new ArrayList(l.size()); 
		for (VRDatabaseAttribute e: l) {
			Map map = new CaseInsensitiveMap();
			map.put("ID_DATABASE", e.getIdDatabase());
			map.put("name", e.getName());
			map.put("HOST_NAME", e.getHostName());
			map.put("DATABASE_NAME", e.getDatabaseName());
			map.put("PORT", e.getPort());
			map.put("USERNAME", e.getUsername());
			map.put("ID_DATABASE_TYPE", e.getIdDatabaseType());
			map.put("ID_DATABASE_CONTYPE", e.getIdDatabaseContype());
			map.put("code", e.getCode());
			map.put("value_str", e.getValueStr());
			result.add(map);
		}
		return result;
	}
	
	public  int queryDataSourceListCount(String type,String conType,String isUsePool,String text,String kettleUser,String superAdmin){
		
		List<Object> paramList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		StringBuilder querySql = new StringBuilder();		
		querySql.append("  select count(t)  from  VRDatabaseAttribute t where t.code='USE_POOLING' and t.name!='"+AppConstant.KETTLE_INSTANCE_NAME +"' ");
		
		if(!kettleUser.equals(superAdmin)){
			   querySql.append(" and (t.modifiedUser = :modified_user  or t.createdUser = :created_user ) ");
				
			   paramList.add(kettleUser);
			   nameList.add("modified_user");
			
			   paramList.add(kettleUser);
			   nameList.add("created_user");
		}
        
        
		if(null!=type&&!"".equals(type)){
			if(!"0".equals(type)){
			   querySql.append(" and t.idDatabaseType = :id_database_type");
			   paramList.add(Long.parseLong(type));
			   nameList.add("id_database_type");
			}else{
				querySql.append(" and t.idDatabaseType not in ( select e.idDatabaseType from TDatabaseTypeContypeConf e group by e.idDatabaseType ) ");
			}
		}
		
		if(null!=conType&&!"".equals(conType)){
			if(!"0".equals(conType)){
			   querySql.append(" and t.idDatabaseContype = :id_database_contype ");
			   paramList.add(Long.parseLong(conType));
			   nameList.add("id_database_contype");
			}else{
			   querySql.append(" and t.idDatabaseContype not in ( 1,2,5 ) ");
			}
		}
		
		if(null!=isUsePool&&!"".equals(isUsePool)){
			if(isUsePool.equals("Y")){
			   querySql.append(" and t.valueStr= :value_str");
			   paramList.add(isUsePool);
			   nameList.add("value_str");
			}else if(isUsePool.equals("N")){
				querySql.append(" and (  t.valueStr=:value_str  or  t.valueStr is null ) ");
				   paramList.add(isUsePool);
				   nameList.add("value_str");
			}
		}
		
		if(null!=text&&!"".equals(text.trim())){
			querySql.append(" and ( t.name like :name  or t.hostName like :host_name"
					+ " or t.databaseName like :database_name or t.userName like :username ) ");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("name");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("host_name");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("database_name");
			paramList.add("%" + text.trim().replaceAll(regex, replace) + "%");
			nameList.add("username");
			
		}
		
		Query query = entityManager.createQuery(querySql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(nameList.get(i), paramList.get(i));
		}
		long result = (long) query.getSingleResult();
		return (int)result;
	}

	public  List<Map<String,Object>>  queryDataBaseContype(String kettleUser,String superAdmin,String text){
		List<Object> paramList = new ArrayList<>();
		List<Integer> typeList = new ArrayList<>();
		StringBuilder querySql = new StringBuilder();		
	    querySql.append("select a.id_dataBase_contype as \"id_dataBase_contype\",a.code,a.description ,case when b.num is null then 0 else b.num end num from R_DATABASE_contype a  left join (select count(*) num ,c.id_database_contype from R_DATABASE c where 1=1 and NAME!='"+AppConstant.KETTLE_INSTANCE_NAME +"' " );
			
		 if(!kettleUser.equals(superAdmin)){
			   querySql.append(" and (c.MODIFIED_USER =?  or c.CREATED_USER =? ) ");
				
			   paramList.add(kettleUser);
			   typeList.add(Types.VARCHAR);
			
			   paramList.add(kettleUser);
			   typeList.add(Types.VARCHAR);
		}
		 if(null!=text&&!"".equals(text.trim())){		
				querySql.append(" and ( c.name like ?  or c.HOST_NAME like ? or c.DATABASE_NAME like ? or c.USERNAME like ? ) ");
				paramList.add("%" + text.trim().replaceAll("([\\\\_%])", replace) + "%");
				typeList.add(Types.VARCHAR);
				paramList.add("%" + text.trim().replaceAll(regex2, replace) + "%");
				typeList.add(Types.VARCHAR);
				paramList.add("%" + text.trim().replaceAll(regex2, replace) + "%");
				typeList.add(Types.VARCHAR);
				paramList.add("%" + text.trim().replaceAll(regex2, replace) + "%");
				typeList.add(Types.VARCHAR);				
			}
		 
		 querySql.append( "group by c.id_database_contype) b on a.ID_DATABASE_conTYPE=b.ID_DATABASE_conTYPE where  a.id_database_conTYPE in ('1') ") ;
		 
		Object[] paramO = paramList.toArray();
		int[] argTypes = new int[typeList.size()];
		for (int i = 0; i < typeList.size(); i++) {
			argTypes[i] = typeList.get(i);
		}
		List<Map<String,Object>> result = new ArrayList();
//		Map map = new HashMap();
//		map.put(key, value)
//		result.add();
		return result;
		
	}
}
