package wxx.jpa_test.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class JobDetailDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	public List query() {
		String sb = "select new JobDetail(e.jobName, e.currStartTime, e.id) from JobDetail e where STATE=:state";
		Query query = entityManager.createQuery(sb);
		query.setParameter("state", 3);
		return query.getResultList();
	}
	
	public List queryNeed(String id) {   
		String sb = "SELECT new JobDetail(e.createdDate,e.createdUser,e.modifiedDate,e.modifiedUser,(case when e.errCount is null then 0 else e.errCount end) as errCount, (case when e.runCount is null then 0 else e.runCount end ) as runCount) from JobDetail e where e.id =:id";
		Query query = entityManager.createQuery(sb);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public Map testNativeQuery(String id){  
        String sql = "select * from (select count(1) as succ_count from t_job_log where (STATUS = 'end' or STATUS = 'stop') and (ERRORS = 0 or ERRORS is null) and JOBID=:id) t, (select count(1) as fail_count from t_job_log where (STATUS = 'error' or ERRORS > 0) and JOBID=:id) tt";
		Query query = entityManager.createNativeQuery(sql);  
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		query.setParameter("id", id);
        List rows = query.getResultList();
        if(rows!=null && !rows.isEmpty()) {
        	return (Map)rows.get(0);
        } else {
        	return null;
        }
    }  
	
	public void testNativeQuery1(){  
		String sql = "select * from (select count(1) as succ_count from t_job_log where (STATUS = 'end' or STATUS = 'stop') and (ERRORS = 0 or ERRORS is null) and JOBID=:id) t, "
				+ "(select count(1) as fail_count from t_job_log where (STATUS = 'error' or ERRORS > 0) and JOBID=:id) tt";
		Query query = entityManager.createNativeQuery(sql);
		 query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
			query.setParameter("id", 1);
	        List rows = query.getResultList();
	        if(rows!=null && !rows.isEmpty()) {
	        	System.out.println(rows.get(0));
	        } else {
	        	return ;
	        }
    }  

	public List<Map> findMapBySql(String sqlStr) {
        Session session = entityManager.unwrap(Session.class);
        return session.createSQLQuery(sqlStr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }
}
