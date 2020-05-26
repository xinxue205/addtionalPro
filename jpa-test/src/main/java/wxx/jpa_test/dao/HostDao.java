package wxx.jpa_test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import wxx.jpa_test.entity.Host;
import wxx.jpa_test.entity.TenantEntity;

public interface HostDao extends CrudRepository<TenantEntity, String> {
	@Query("from Host p1 where p1.ip = (select p2.ip from TRoleEntity p2 where p2.id=:id)")
    Host getHostByEntity(@Param("id") String entityId);
	
	//@Query("from Host p1 where p1.lastTime > date_add(now(),interval -7 day) ORDER BY p1.lastTime asc")
	@Query("from Host p1 where p1.lastTime > now() ORDER BY p1.lastTime asc")
    List<Host> getHostsPerFunc();
}
