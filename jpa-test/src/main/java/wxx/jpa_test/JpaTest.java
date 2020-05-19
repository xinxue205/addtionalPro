package wxx.jpa_test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wxx.jpa_test.entity.JobDetail;
import wxx.jpa_test.entity.TenantEntity;
import wxx.jpa_test.entity.ViewInfo;
import wxx.jpa_test.service.ServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class JpaTest {
	
	/**
		CrudRepository提供CRUD的功能。
		PagingAndSortingRepository提供分页和排序功能, 继承自CrudRepository
		JpaRepository提供JPA相关的方法，如刷新持久化数据、批量删除。继承自PagingAndSortingRepository
		所以JpaRepository包含了CrudRepository和PagingAndSortingRepository所有的API。
		不需要JpaRepository和PagingAndSortingRepository提供的功能时，可直接使用CrudRepository。
	 */

    @Autowired
    protected ServiceImpl tenantService;
   
    public void test2() {
    	Sort sort=new Sort(Sort.Direction.DESC,"id");
    	//排序查询
		Iterable<TenantEntity> it=tenantService.findAllSort(sort);
		for (TenantEntity en : it) {
			System.out.println(en.getId());
		}
		
    	//排序并分页查询
		int pageIndex = 1;
		Pageable page= new PageRequest(pageIndex -1, 2, sort);
		while(page != null) {
			Page<TenantEntity> findAll = tenantService.findAll( page);
			List<TenantEntity> list = findAll.getContent();
			for (TenantEntity en : list) {
				System.out.println(en.getId() + "\t" +en.getName());
			}
			page = findAll.nextPageable();
		}
    }
    
    public void test1(){//使用jpa的 Repository 自定义声明式查询方法
   		TenantEntity tenant = new TenantEntity();
        tenant.setId("132");
        tenant.setName("test");
        tenant.setAdditionInfo("comba");
        List newTenant = tenantService.query();//entite sql方式查询(无参)
        System.out.println(((JobDetail)newTenant.get(0)).getJobName());
        
        List list = tenantService.queryNeed("1");//entite sql方式查询(有参)
    	System.out.println(((JobDetail)list.get(0)).getErrCount());
    	
    	Map map = tenantService.testNativeQuery("1");//sql原生语句查询，map方式返回
    	System.out.println(map.get("fail_count"));
	}
    
    @Test
    public void test(){//使用jpa的 Repository 自定义声明式查询方法,及hsql
		
    	String id = "130";
    	String name = "test";
    	TenantEntity tenant = tenantService.findById(id);//CrudRepository默认方法查询
    	System.out.println(tenant.getId());

    	List newTenant = tenantService.findByIdAndName("131", "test1");//CrudRepository默认方法查询(带参)
    	System.out.println(((TenantEntity)newTenant.get(0)).getId());
    	
    	List newTenant1 = tenantService.findByNameLike("%test%");//CrudRepository默认方法查询(like)
    	System.out.println(((TenantEntity)newTenant1.get(2)).getId());
    	
    	TenantEntity tenant1 = tenantService.getTenantEntityByMaxId();//CrudRepository 自定义声明式查询最大值对象
    	System.out.println(tenant1.getId());
    	
    	List list1 = tenantService.getTenantWithTenant1();//CrudRepository 自定义声明式关联查询
    	System.out.println(((TenantEntity)list1.get(1)).getId());
    	
    	List list2 = tenantService.getTenantWithTenant2();//CrudRepository 自定义声明式关联查询出合并记录对象
    	System.out.println(((ViewInfo)list2.get(1)).getE().getId());
    	
    	List newTenant2 = tenantService.listByName("test"); //CrudRepository 自定义声明式查询（?1传参）
    	System.out.println(((TenantEntity)newTenant2.get(1)).getId());

    	List newTenant3 = tenantService.findByIdAndName1("132", "test2");//CrudRepository 自定义声明式查询（:name传参）
    	System.out.println(((TenantEntity)newTenant3.get(0)).getId());
    	
    	System.out.println(tenantService.getTotal());//CrudRepository 自定义声明式查询条数
    	System.out.println(tenantService.count());
    	System.out.println(tenantService.countByName("test"));
    	
    	List list = tenantService.getNeed("1");//CrudRepository 自定义声明式查询（定义实例构造）
    	System.out.println(((JobDetail)list.get(0)).getErrCount());
   }
}