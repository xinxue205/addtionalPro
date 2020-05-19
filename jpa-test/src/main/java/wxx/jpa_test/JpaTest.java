package wxx.jpa_test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wxx.jpa_test.entity.JobDetail;
import wxx.jpa_test.entity.TenantEntity;
import wxx.jpa_test.service.ServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class JpaTest {

    @Autowired
    protected ServiceImpl tenantService;
   
    @Test
    public void test1(){//使用jpa的 Repository 自定义声明式查询方法
   		TenantEntity tenant = new TenantEntity();
        tenant.setId("132");
        tenant.setName("test");
        tenant.setAdditionInfo("comba");
        List newTenant = tenantService.query();//entityManager.createQuery方式
        System.out.println(((JobDetail)newTenant.get(0)).getJobName());
        
        List list = tenantService.queryNeed("1");
    	System.out.println(((JobDetail)list.get(0)).getErrCount());
    	
    	Map map = tenantService.testNativeQuery("1");
    	System.out.println(map.get("fail_count"));
	}
    
	
    public void test(){//使用jpa的 Repository 自定义声明式查询方法,及hsql
		
    	String id = "130";
    	String name = "test";
    	TenantEntity tenant = tenantService.findById(id);
    	System.out.println(tenant.getId());
    	
    	List newTenant3 = tenantService.findByIdAndName1("132", "test2");
    	System.out.println(((TenantEntity)newTenant3.get(0)).getId());
    	
    	TenantEntity tenant1 = tenantService.getTenantEntityByMaxId();
    	System.out.println(tenant1.getId());
    	
    	List newTenant2 = tenantService.listByName("test"); //hsql方式
    	System.out.println(((TenantEntity)newTenant2.get(1)).getId());

    	List newTenant1 = tenantService.findByNameLike("%test%");
    	System.out.println(((TenantEntity)newTenant1.get(2)).getId());

    	List newTenant = tenantService.findByIdAndName("131", "test1");
    	System.out.println(((TenantEntity)newTenant.get(0)).getId());
    	
    	System.out.println(tenantService.getTotal());
    	
    	List list = tenantService.getNeed("1");
    	System.out.println(((JobDetail)list.get(0)).getErrCount());
   }
}