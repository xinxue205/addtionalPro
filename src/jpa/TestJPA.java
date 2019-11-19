package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestJPA {
	
	public static void main(String[] args) {
		//1.创建entityManagerFactory -- 参数为<persistence-unit name="jpademo">的name值
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpademo");
				//2.创建EntityManager
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				//3.开启事务处理
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				//4.执行数据的CRUD
				User user = new User();
				user.setUsername("admin");
				user.setPassword("admin123");
				entityManager.persist(user);
				//5.提交事务
				transaction.commit();
				//6.关闭entityManager和entityManagerFactory
				entityManager.close();
				entityManagerFactory.close();
	}
}
