package wxx.jpa_test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import wxx.jpa_test.entity.JobDetail;
import wxx.jpa_test.entity.TenantEntity;
import wxx.jpa_test.entity.ViewInfo;

public interface TenantDao extends CrudRepository<TenantEntity, String> {
	
	TenantEntity findById(String id);
	List<TenantEntity> findByIdAndName(String id, String name);
	List<TenantEntity> findByNameLike(String name);
	Long countByName(String name);
	
	long count();
    
 // 查询id最大的那个person
    @Query("from TenantEntity p1 where p1.id = (select max(p2.id) from TenantEntity p2)")
    TenantEntity getTenantEntityByMaxId();
    
    @Query("select new TenantEntity(p1.id, p1.name, p1.additionInfo) from TenantEntity p1, TenantEntity1 p2 where p1.id =p2.id")
    List<TenantEntity> getTenantWithTenant1();
    
    @Query("select new wxx.jpa_test.entity.ViewInfo(p, p1) from TenantEntity p, TenantEntity1 p1 where p.id =p1.id")
    List<ViewInfo> getTenantWithTenant2();
    
    @Query("from TenantEntity where name = ?1 order by id desc")
    List<TenantEntity> listByName(String name);

    @Query("from TenantEntity p where p.name = :name and p.id = :id")
    List findByIdAndName1(@Param("id") String id, @Param("name") String name);
    
    @Query(value = "select count(1) from tenant", nativeQuery = true)
    Long getTotal();
    
    @Query(value = "SELECT new JobDetail(e.createdDate,e.createdUser,e.modifiedDate,e.modifiedUser,(case when e.errCount is null then 0 else e.errCount end) as errCount, (case when e.runCount is null then 0 else e.runCount end ) as runCount) from JobDetail e where e.id =:id")
    List getNeed(@Param("id") String id);
    
}
