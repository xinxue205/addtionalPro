package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestJPA {
	
	public static void main(String[] args) {
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
