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

import wxx.jpa_test.entity.Host;
import wxx.jpa_test.entity.JobDetail;
import wxx.jpa_test.entity.TRoleEntity;
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
    protected ServiceImpl svc;
   
    public void test3() {
//		TRoleEntity trole = svc.findByRolename("worker117");
//    	trole.getId();
//    	Host h = svc.getHostByEntity("238");
//    	System.out.println(h.getHostname());
    	List<Host> list = svc.getHostsPerFunc();
    	for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
    }
    
    public void test2() {
    	Sort sort=new Sort(Sort.Direction.DESC,"id");
    	//排序查询
		Iterable<TenantEntity> it=svc.findAllSort(sort);
		for (TenantEntity en : it) {
			System.out.println(en.getId());
		}
		
    	//排序并分页查询
		int pageIndex = 1;
		Pageable page= new PageRequest(pageIndex -1, 2, sort);
		while(page != null) {
			Page<TenantEntity> findAll = svc.findAll( page);
			List<TenantEntity> list = findAll.getContent();
			for (TenantEntity en : list) {
				System.out.println(en.getId() + "\t" +en.getName());
			}
			page = findAll.nextPageable();
		}
    }
    
    @Test
    public void test1(){//使用jpa的 Repository 自定义声明式查询方法
   		TenantEntity tenant = new TenantEntity();
        tenant.setId("132");
        tenant.setName("test");
        tenant.setAdditionInfo("comba");
        List newTenant = svc.query();//entite sql方式查询(无参)
        System.out.println(((JobDetail)newTenant.get(0)).getJobName());
        
        List list = svc.queryNeed("1");//entite sql方式查询(有参)
    	System.out.println(((JobDetail)list.get(0)).getErrCount());
    	
    	Map map = svc.testNativeQuery("1");//sql原生语句查询，map方式返回
    	System.out.println(map.get("fail_count"));
    	
    	svc.testNativeQuery1();//sql原生语句查询，内部打印记录
	}
    
    public void test(){//使用jpa的 Repository 自定义声明式查询方法,及hsql
    	String id = "130";
    	String name = "test";
    	TenantEntity tenant = svc.findById(id);//CrudRepository默认方法查询
    	System.out.println(tenant.getId());

    	List newTenant = svc.findByIdAndName("131", "test1");//CrudRepository默认方法查询(带参)
    	System.out.println(((TenantEntity)newTenant.get(0)).getId());
    	
    	List newTenant1 = svc.findByNameLike("%test%");//CrudRepository默认方法查询(like)
    	System.out.println(((TenantEntity)newTenant1.get(2)).getId());
    	
    	TenantEntity tenant1 = svc.getTenantEntityByMaxId();//CrudRepository 自定义声明式查询最大值对象
    	System.out.println(tenant1.getId());
    	
    	List list1 = svc.getTenantWithTenant1();//CrudRepository 自定义声明式关联查询
    	System.out.println(((TenantEntity)list1.get(1)).getId());
    	
    	List list2 = svc.getTenantWithTenant2();//CrudRepository 自定义声明式关联查询出合并记录对象
    	System.out.println(((ViewInfo)list2.get(1)).getE().getId());
    	
    	List newTenant2 = svc.listByName("test"); //CrudRepository 自定义声明式查询（?1传参）
    	System.out.println(((TenantEntity)newTenant2.get(1)).getId());

    	List newTenant3 = svc.findByIdAndName1("132", "test2");//CrudRepository 自定义声明式查询（:name传参）
    	System.out.println(((TenantEntity)newTenant3.get(0)).getId());
    	
    	System.out.println(svc.getTotal());//CrudRepository 自定义声明式查询条数
    	System.out.println(svc.count());//CrudRepository 默认统计方法
    	System.out.println(svc.countByName("test"));//CrudRepository 默认统计方法
    	
    	List list = svc.getNeed("1");//CrudRepository 自定义声明式查询（定义实例构造）
    	System.out.println(((JobDetail)list.get(0)).getErrCount());
   }
}