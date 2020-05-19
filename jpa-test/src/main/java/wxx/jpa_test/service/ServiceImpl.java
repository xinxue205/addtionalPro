package wxx.jpa_test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import wxx.jpa_test.dao.JobDetailDao;
import wxx.jpa_test.dao.TenantDao;
import wxx.jpa_test.entity.TenantEntity;

@Service
public class ServiceImpl {

    @Autowired
    private TenantDao tenantDao;
    
    @Resource 
	private JobDetailDao jobDetailDao;
    
    
    public List query() {
    	return jobDetailDao.query();
    }

    public List queryNeed(String id) {
    	return jobDetailDao.queryNeed(id);
    }
    
    public Map testNativeQuery(String id) {
    	return jobDetailDao.testNativeQuery(id);
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

    public List findByIdAndName1(String id, String name){
    	return tenantDao.findByIdAndName1(id, name);
    }
    
    public long getTotal(){
    	return tenantDao.getTotal();
    }
    
    public List getNeed(String id) {
        return tenantDao.getNeed(id);
    }
    
}