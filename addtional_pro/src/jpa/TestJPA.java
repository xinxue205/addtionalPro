package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TestJPA {
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpademo");
		query2(emfactory);
		emfactory.close();
	}
	
	public static void query2(EntityManagerFactory emfactory) {
	   	EntityManager entitymanager = emfactory.createEntityManager();
	   	String sql = "select o from jpa.JobHistoryScheduled o where o.id=:id";
	   	Query query=entitymanager.createQuery(sql);
		query.setParameter("id", "00002294-198b-11e9-8b8d-52542d598fa2");
		List<JobHistoryScheduled> list = query.getResultList();
	   	System.out.println(list);
	}
	
	
	public static void query1(EntityManagerFactory emfactory) {
	   	EntityManager entitymanager = emfactory.createEntityManager();
	   	
	   	JobHistoryScheduled j=entitymanager.find(JobHistoryScheduled.class, "00002294-198b-11e9-8b8d-52542d598fa2");
	   	System.out.println(j.getLogdate()+"--"+j.getCount());
	}

	public static void update() {
		//1.����entityManagerFactory -- ����Ϊ<persistence-unit name="jpademo">��nameֵ
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpademo");
				//2.����EntityManager
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				//3.����������
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				//4.ִ�����ݵ�CRUD
				UserDemo user = new UserDemo();
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
