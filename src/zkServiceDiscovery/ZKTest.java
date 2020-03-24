package zkServiceDiscovery;

import org.apache.curator.framework.CuratorFramework;
import org.json.JSONObject;

public class ZKTest {
	public static void main(String[] args) {
		try {
		JSONObject jsonObject=new JSONObject();
		//worker和ganger共享配置
		jsonObject.put("sdi_octopus_db_url", "2");
		 String rootPath="/knob/test";
	        String sdiPath="/knob/test/instance";
	        String zkHost="192.168.11.104";
	        String zkPort="2181";
		CuratorFramework zkClient = CuratorUtil.newClient(zkHost+":"+zkPort);
                
            if(zkClient.checkExists().forPath(rootPath)==null){
                zkClient.create().forPath(rootPath);
                zkClient.checkExists().forPath(rootPath);
                zkClient.create().forPath(sdiPath);
                zkClient.setData().forPath(sdiPath, jsonObject.toString().getBytes());
            }else if("Y".equals("Y")){
                if(zkClient.checkExists().forPath(sdiPath)==null){
                    zkClient.create().forPath(sdiPath);
                    zkClient.setData().forPath(sdiPath, jsonObject.toString().getBytes());
                    // zkClient.setData().forPath("/knob/db_connection", jsonObject.toString().getBytes());
                }else{
                    //System.out.println("====配置信息已存在====="+ new String(zkClient.getData().forPath(sdiPath)));
                    zkClient.setData().forPath(sdiPath, jsonObject.toString().getBytes());
//                    CuratorFramework zkClient1 = CuratorUtil.newClient(zkHost+":"+zkPort);
//                    zkClient1.getData().forPath("/knob/sdi/instance");
//                    CuratorFramework zkCl= CuratorFrameworkFactory.newClient();
                }
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
	}
}
