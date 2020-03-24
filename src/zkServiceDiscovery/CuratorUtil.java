package zkServiceDiscovery;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class CuratorUtil {
  private static final Logger LOG = LoggerFactory.getLogger(CuratorUtil.class);
	public static CuratorFramework newClient(String connectionString) {
        CuratorFramework curatorFramework;
        if (System.getProperty("auth")!=null){
			if(System.getProperty("auth").equals("true")){
				//创建权限管理器
				ACLProvider aclProvider = new ACLProvider() {
					private List<ACL> acl;
					@Override public List<ACL> getDefaultAcl() {
						if (acl == null) {
							ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL; //初始化
							acl.clear();
							acl.add(new ACL(ZooDefs.Perms.ALL, new Id("auth", "sinobest")));//添加
							this.acl = acl;
						}
						return acl;
					}
					@Override public List<ACL> getAclForPath(String path) {
						return acl;
					}
				};
				curatorFramework = CuratorFrameworkFactory.builder()
						.aclProvider(aclProvider)
						.connectString(connectionString)
						.retryPolicy(new RetryNTimes(30, 10*1000))
						.authorization("digest","sinobest".getBytes())
						.build();
			}else {
				curatorFramework = CuratorFrameworkFactory.newClient(connectionString, new RetryNTimes(30, 10*1000));
			}

			curatorFramework.start();
		}else {
			curatorFramework = CuratorFrameworkFactory.newClient(connectionString, new RetryNTimes(30, 10*1000));
			curatorFramework.start();
		}


		return curatorFramework;
	}
	
}
