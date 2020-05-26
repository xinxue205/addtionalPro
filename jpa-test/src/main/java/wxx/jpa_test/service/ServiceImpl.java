package wxx.jpa_test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import wxx.jpa_test.dao.HostDao;
import wxx.jpa_test.dao.JobDetailDao;
import wxx.jpa_test.dao.TRoleEntityRepository;
import wxx.jpa_test.dao.TenantPageDao;
import wxx.jpa_test.dao.TenantDao;
import wxx.jpa_test.entity.Host;
import wxx.jpa_test.entity.JobDetail;
import wxx.jpa_test.entity.TRoleEntity;
import wxx.jpa_test.entity.TenantEntity;

@Service
public class ServiceImpl {

    @Autowired
    private TenantDao tenantDao;
    
    @Resource 
	private JobDetailDao jobDetailDao;
    
    @Resource 
	private TenantPageDao tenantPageDao;
    
    @Resource 
	private HostDao hostDao;
    
    @Resource
    private TRoleEntityRepository troleRep;
    
    public Host getHostByEntity(String entityId){
		return hostDao.getHostByEntity(entityId);
	}
    
    public List<Host> getHostsPerFunc(){
		return hostDao.getHostsPerFunc();
	}
    
    public Iterable<TenantEntity> findAllSort(Sort sort){
		return tenantPageDao.findAll(sort);
	}
	
	public Page<TenantEntity> findAll(Pageable page){
		return tenantPageDao.findAll(page);
	}
    
    public List query() {
    	return jobDetailDao.query();
    }

    public List queryNeed(String id) {
    	return jobDetailDao.queryNeed(id);
    }
    
    public Map testNativeQuery(String id) {
    	return jobDetailDao.testNativeQuery(id);
    }
    
    public void testNativeQuery1() {
    	jobDetailDao.testNativeQuery1();
    }
    
    public TenantEntity save(TenantEntity entity) {
    	return tenantDao.save(entity);
    }

    public TenantEntity findById(String id) {
        return tenantDao.findById(id);
    }

    public List findByIdAndName(String id, String name) {
        return tenantDao.findByIdAndName(id, name);
    }
    
    public List findByNameLike(String name) {
        return tenantDao.findByNameLike(name);
    }
    
    public List listByName(String name) {
        return tenantDao.listByName(name);
    }
    
    public TenantEntity getTenantEntityByMaxId() {
    	return tenantDao.getTenantEntityByMaxId();
    }
    
    public List getTenantWithTenant1() {
    	return tenantDao.getTenantWithTenant1();
    }
    
    public List getTenantWithTenant2() {
    	return tenantDao.getTenantWithTenant2();
    }

    public List findByIdAndName1(String id, String name){
    	return tenantDao.findByIdAndName1(id, name);
    }
    
    public long getTotal(){
    	return tenantDao.getTotal();
    }
    
    public List getNeed(String id) {
        return tenantDao.getNeed(id);
    }
    
    public long count(){
    	return tenantDao.count();
    }
    
    public long countByName(String name){
    	return tenantDao.countByName(name);
    }
    
    public TRoleEntity findByRolename(String rolename) {
    	return troleRep.findByRolename(rolename);
    }
}