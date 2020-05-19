package wxx.jpa_test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import wxx.jpa_test.entity.TenantEntity;

public interface TenantDao2 extends JpaRepository<TenantEntity, String> {
	
	TenantEntity findById(String id);
	List<TenantEntity> findByIdAndName(String id, String name);
	List<TenantEntity> findByNameLike(String name);
	
    @Query("from TenantEntity where name = ?1 order by id desc")
	List<TenantEntity> listByName(String name);
}
