package wxx.jpa_test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import wxx.jpa_test.entity.TRoleEntity;

public interface TRoleEntityRepository extends JpaRepository <TRoleEntity, Integer> {
	TRoleEntity findByRolename(String rolename);
	
	@Modifying
	@Query("update TRoleEntity set isModified=?2  where rolename=?1")
	void updateIsModifiedByRolename(String rolename, int isModified);
}
