package jpa.left;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class TestJPA {
	
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpademo");
		EntityManager entityManager = emfactory.createEntityManager();
		String sb = "SELECT e from VRDatabaseAttribute e where 1=1 and e.createdUser= :kettleUser";
		Query query = entityManager.createQuery(sb);
		query.setParameter("kettleUser", "sdi");
//		query.setParameter("role_entity_id", 10136L);
		List list = query.getResultList();
		System.out.println(list.size());
	
		query.setParameter("kettleUser", "admin");
//		query.setParameter("role_entity_id", 10136L);
		List list1 = query.getResultList();
		System.out.println(list1.size());
		list.addAll(list1);
		System.out.println(list.size());
		System.out.println(list.get(0));
		emfactory.close();
	}
}
