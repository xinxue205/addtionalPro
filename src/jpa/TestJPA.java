package jpa;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TestJPA {
	
	public static void main(String[] args) {
		//1.����entityManagerFactory -- ����Ϊ<persistence-unit name="jpademo">��nameֵ
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpapostgre");
				//2.����EntityManager
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				//3.����������
//				EntityTransaction transaction = entityManager.getTransaction();
//				transaction.begin();
//				//4.ִ�����ݵ�CRUD
//				User user = new User();
//				user.setUsername("admin");
//				user.setPassword("admin123");
//				entityManager.persist(user);
				
				StringBuffer sb = new StringBuffer();
				sb.append("select new JobHistoryScheduled(");
					sb.append("to_char(e.logdate, 'YYYY-MM-DD HH') as logdate,sum(e.count) as count)")
					  .append(" from JobHistoryScheduled e where e.logdate >= :startTime and e.logdate <= :endTime ")
					  .append("group by logdate order by logdate");
				Query query = entityManager.createQuery(sb.toString());
				Timestamp startTime = new Timestamp(new Date().getTime());
				Timestamp endTime = new Timestamp(new Date().getTime());
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
				System.out.println(query.getResultList());
				//5.�ύ����
//				transaction.commit();
				//6.�ر�entityManager��entityManagerFactory
				entityManager.close();
				entityManagerFactory.close();
	}

	public static void main1(String[] args) {
		//1.����entityManagerFactory -- ����Ϊ<persistence-unit name="jpademo">��nameֵ
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpademo");
				//2.����EntityManager
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				//3.����������
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				//4.ִ�����ݵ�CRUD
				User user = new User();
				user.setUsername("admin");
				user.setPassword("admin123");
				entityManager.persist(user);
				//5.�ύ����
				transaction.commit();
				//6.�ر�entityManager��entityManagerFactory
				entityManager.close();
				entityManagerFactory.close();
	}
}
