package wxx.jpa_test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import wxx.jpa_test.entity.JobDetail;
import wxx.jpa_test.entity.TenantEntity;

public interface TenantDao extends CrudRepository<TenantEntity, String> {
	
	TenantEntity findById(String id);
	List<TenantEntity> findByIdAndName(String id, String name);
	List<TenantEntity> findByNameLike(String name);
	
    @Query("from TenantEntity where name = ?1 order by id desc")
	List<TenantEntity> listByName(String name);
    
 // 查询id最大的那个person
    @Query("from TenantEntity p1 where p1.id = (select max(p2.id) from TenantEntity p2)")
    TenantEntity getTenantEntityByMaxId();
    
    @Query("from TenantEntity p where p.name = :name and p.id = :id")
    List findByIdAndName1(@Param("id") String id, @Param("name") String name);
    
    @Query(value = "select count(1) from tenant", nativeQuery = true)
    Long getTotal();
    
    @Query(value = "SELECT new JobDetail(e.createdDate,e.createdUser,e.modifiedDate,e.modifiedUser,(case when e.errCount is null then 0 else e.errCount end) as errCount, (case when e.runCount is null then 0 else e.runCount end ) as runCount) from JobDetail e where e.id =:id")
    List getNeed(@Param("id") String id);
    
}
