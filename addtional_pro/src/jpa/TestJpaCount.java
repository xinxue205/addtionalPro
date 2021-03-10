package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TestJpaCount {
	
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpademo");
		EntityManager entityManager = emfactory.createEntityManager();
		String sb = "SELECT e.createdUser, count(e), max(e.idDatabaseAttribute) from VRDatabaseAttribute e where 1=1 GROUP BY e.createdUser";
		Query query = entityManager.createQuery(sb);
		List list = query.getResultList();
		System.out.println(list.get(0));
		emfactory.close();
	}
}
