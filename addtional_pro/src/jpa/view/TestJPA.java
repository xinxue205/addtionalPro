package jpa.view;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class TestJPA {//t_role_entity  t_config  t_config_type
	
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpademo");
		EntityManager entityManager = emfactory.createEntityManager();
		String sb = "SELECT e from RoleEntityHostsConfig e where e.role_entity_id =:role_entity_id";
		TypedQuery<RoleEntityHostsConfig> query = entityManager.createQuery(sb,RoleEntityHostsConfig.class);
		query.setParameter("role_entity_id", 10136L);
		List<RoleEntityHostsConfig> list = query.getResultList();
		System.out.println(list.get(0));
		emfactory.close();
	}
}
