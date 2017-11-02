package redis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanResult;

public class Main {
		
	public static void main(String[] args) {
		Jedis jedis= new Jedis("127.0.0.1",6379);
		jedis.hset("hsetkey", "hashKey", "hashValue");
	}

    public static void main1(String[] args) {
    	JedisPoolConfig config = new JedisPoolConfig(); 
		config.setMaxIdle(5); 
		config.setTestOnBorrow(false); 
//        config.setMaxActive(20); 
//        config.setMaxWait(1000l); 
		
		JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379);
		Jedis jedis = jedisPool.getResource(); 
        // TODO Auto-generated method stub
//        new RedisClient().show(); 
//        jedis.flushDB();
//        jedisPool.returnResource(jedis);
        // hset  hget  
        jedis.hset("hsetkey", "hashKey", "hashValue");//����ϣ��key �е���field ��ֵ��Ϊvalue �����key �����ڣ�һ���µĹ�ϣ������������HSET �����������field �Ѿ������ڹ�ϣ���У���ֵ�������ǡ�  
        String hash = jedis.hget("hsetkey", "hashKey");//���ع�ϣ��key �и�����field ��ֵ  
        System.out.println("���� hset hget �� hsetkey �ķ���ֵ��"+hash);  
          
        //hsetnx  ���ҽ�����field �����ڡ�����field(ָ�ڶ�������) �Ѿ����ڣ��ò�����Ч��   
        long n = jedis.hsetnx("hsetkeynx", "hashkeynx", "hashvaluenx");  
        System.out.println(n!=0?"�����ɹ�":"����ʧ��");  
        n = jedis.hsetnx("hsetkeynx", "hashkey", "hashvaluenx");  
        System.out.println(n!=0?"�����ɹ�":"����ʧ��");  
        n = jedis.hsetnx("hsetkeynx", "hashkey", "hashvaluenx");  
        System.out.println(n!=0?"�����ɹ�":"����ʧ��");  
          
        //hmset hmget  
        HashMap<String, String> hashMap = new HashMap<String, String>();  
        hashMap.put("hashMap1", "hashValue1");  
        hashMap.put("hashMap2", "hashValue2");  
        hashMap.put("hashMap3", "hashValue3");  
        hashMap.put("hashMap4", "hashValue4");  
        String status  = jedis.hmset("hashMapkey", hashMap);//�������ִ�гɹ�������OK ����key ���ǹ�ϣ��(hash) ����ʱ������һ������  
        hash = jedis.hget("hashMapkey", "hashMap4");  
        System.out.println("OK".equals(status)?"�����ɹ�  ����ֵ��"+hash:"����ʧ��");  
        //����ֵ�� һ���������������Ĺ���ֵ�ı���ֵ������˳��͸��������������˳��һ��  
        List<String> hashList = jedis.hmget("hashMapkey", "hashMap1 hashMap2 hashMap3 hashMap4".split(" "));  
        for(String value : hashList){  
            System.out.print("��Ӧ��valueֵ��  "+value+" ");//����ֵ�� һ���������������Ĺ���ֵ�ı���ֵ������˳��͸��������������˳��һ��  
        }  
        System.out.println();  
          
        //hgetall  ���һ��Map ����key����file��  
        Map<String,String> hashMapKey = jedis.hgetAll("hashMapkey");  
          
        // map �ĵ�һ�ֵ�����ʽ  
        Set<Map.Entry<String, String>> entry = hashMapKey.entrySet();  
        Iterator<Map.Entry<String, String>> it = entry.iterator();  
        while(it.hasNext()){  
            Map.Entry<String, String> e  = it.next();  
            System.out.println("key: "+e.getKey()+"  value: "+e.getValue());  
        }  
          
        // map�ĵڶ��ֵ�����ʽ  
        Set<String> keySet = hashMapKey.keySet();// map�е�����key��set�д���ţ�����ͨ������set�ķ�ʽ �����key  
        Iterator<String> iter = keySet.iterator();  
        while(iter.hasNext()){  
            String key = iter.next();  
            String value = hashMapKey.get(key);  
        }  
          
      
        //hscan  ������ scan �������� key �����е���   ����  file-value ��map ����ʽ��  
        ScanResult<Map.Entry<String, String>> hscanResult = jedis.hscan("hashMapkey", "0");  
        int cursor = hscanResult.getCursor(); // ����0 ˵���������  
        System.out.println("�α�"+cursor);  
        List<Map.Entry<String, String>> scanResult = hscanResult.getResult();  
        for(int m = 0;m < scanResult.size();m++){  
            Map.Entry<String, String> mapentry  = scanResult.get(m);  
            System.out.println("key: "+mapentry.getKey()+"  value: "+mapentry.getValue());  
        }  
          
        //hkeys  
        Set<String> setKey = jedis.hkeys("hashMapkey");// keys ���� ���е�key  ,hkeys ���� key ��������е� ��  
        Iterator<String> itset = setKey.iterator();  
        String files = "";  
        while(itset.hasNext()){  
            files =files+" "+itset.next();  
        }  
        System.out.println("hashMapkey �е������� Ϊ��"+files);  
          
        //hvals ���ع�ϣ��key ���������ֵ�����ð汾�� >= 2.0.0ʱ�临�Ӷȣ� O(N)��N Ϊ��ϣ��Ĵ�С������ֵ��һ��������ϣ��������ֵ�ı���key ������ʱ������һ���ձ�  
        List<String> list = jedis.hvals("hashMapkey");  
        for(String s : list){  
            System.out.println(s);  
        }  
          
        // ���� ���Ӧ��ֵ��String  �������Ӧ��ֵ ��list  
        Map<String,List<String>> testMapList = new HashMap<String,List<String>>();  
        List<String> testList = Arrays.asList("testList testList testList testList testList testList testList ");  
        List<String> testList1 = Arrays.asList("testList1 testList1 testList1 testList1 testList1 testList1 testList1 ");  
        List<String> testList2 = Arrays.asList("testList2 testList2 testList2 testList2 testList2 testList2 testList2 ");  
        testMapList.put("testList", testList);  
        testMapList.put("testList1", testList1);  
        testMapList.put("testList2", testList2);  
        String mapString  =  JSON.toJSONString(testMapList,true);// map תΪjson��  
        jedis.set("hashMapkey2", mapString);  
        mapString = jedis.get("hashMapkey2");  
        System.out.println(mapString);    
        testMapList = (Map<String,List<String>>)JSON.parse(mapString);  
        Set<Map.Entry<String, List<String>>> mapListSet = testMapList.entrySet();  
        Iterator<Map.Entry<String, List<String>>> maplistIter = mapListSet.iterator();  
        while(maplistIter.hasNext()){  
            Map.Entry<String, List<String>> mapentryList = maplistIter.next();  
            String key = mapentryList.getKey();  
            List<String> entryList = mapentryList.getValue();  
            System.out.println("testMapList key: "+key+"testMapList value: "+entryList.toString());  
        }  
        // Map ����洢ʵ�����  
        Map<String,Bar> testMapEntity = new HashMap<String,Bar>();  
        Bar bar = new Bar(); bar.setColor("red");bar.setName("lvxiaojian");  
        Bar bar1 = new Bar(); bar.setColor("green");bar.setName("wagnbo");  
        testMapEntity.put("bar", bar);  
        testMapEntity.put("bar1", bar1);  
        String entityString  =  JSON.toJSONString(testMapEntity,true);// map תΪjson��  
        jedis.set("hashMapkey3", entityString);  
        entityString = jedis.get("hashMapkey3");  
        testMapEntity = (Map<String,Bar>)JSON.parse(entityString);  
        Set<String> entitySet = testMapEntity.keySet();  
        Iterator<String> iterentity = entitySet.iterator();  
        while(iterentity.hasNext()){  
            System.out.println("testMapEntity key: "+iterentity.next()+"testMapEntity value: "+testMapEntity.get(iterentity.next()));  
        }  
          
          
        //hlen  ����ֵ����ϣ���������������key ������ʱ������0 ��  
        n = jedis.hlen("hashMapkey");  
        System.out.println("hashMapkey ���������Ϊ�� "+n);  
          
        //hdel  ����ֵ: ���ɹ��Ƴ�����������������������Ե���  
        n = jedis.hdel("hashMapkey","hashMap1 hashMap2 hashMap3 hashMap4".split(" "));  
        System.out.println("���ɹ��Ƴ�����������������������Ե���: "+n);  
          
        //hexists  ����ֵ�������ϣ���и����򣬷���1 �������ϣ�����и����򣬻�key �����ڣ�����0 ��  
        boolean flag = jedis.hexists("hashMapkey", "hashMap1");  
        System.out.println(flag?"��ϣ���и�����":"��ϣ�����и�����");  
          
        hashMap.clear();// ���map  
        hashMap.put("hashMap1", "1");  
        hashMap.put("hashMap2", "2");  
        hashMap.put("hashMap3", "3");  
        hashMap.put("hashMap4", "4");  
        hashMap.put("hashMap5", "5");  
        hashMap.put("hashMap6", "6");  
        jedis.hmset("hashMapkey", hashMap);  
        flag = jedis.hexists("hashMapkey", "hashMap1");  
        System.out.println(flag?"��ϣ���и�����":"��ϣ�����и�����");  
          
        //hincrBy  key ����  ��Ҳ���ڵ����  ����ֵ�� ִ��HINCRBY ����֮�󣬹�ϣ��key ����field ��ֵ  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap1 ��ֵ   ��ȥ 1 ֮ǰ����Ϊ��"+jedis.hget("hashMapkey", "hashMap1"));// ����ֵ���� hash����key ΪhashMapkey ����hashMap1 ��ֵ   ��ȥ 1 ֮ǰ����Ϊ��1  
        n = jedis.hincrBy("hashMapkey", "hashMap1", -1); // �� hash����key ΪhashMapkey ����hashMap1 ��ֵ  ��ȥ 1  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap1 ��ֵ  ��ȥ 1 ���Ϊ��"+n);// ����ֵ���� hash����key ΪhashMapkey ����hashMap1 ��ֵ  ��ȥ 1 ���Ϊ��0  
          
        System.out.println("�� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2 ֮ǰ����Ϊ��"+jedis.hget("hashMapkey", "hashMap2"));//����ֵ���� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2 ֮ǰ����Ϊ��2  
        n = jedis.hincrBy("hashMapkey", "hashMap2", 2); // �� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2 ���Ϊ��"+n);//����ֵ���� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2 ���Ϊ��4  
          
        // key ����  �򲻴��ڵ����  ���� �� ����:�����²������Կ��������򲻴��ڵ�ʱ����ִ�в�����ʱ����ȸ����ֵĬ�ϳ�ʼ��Ϊ 0 �ڽ��мӼ�����  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap7 ��ֵ   ��ȥ 1 ֮ǰ����Ϊ��"+jedis.hget("hashMapkey", "hashMap7"));//����ֵ���� hash����key ΪhashMapkey ����hashMap7 ��ֵ   ��ȥ 1 ֮ǰ����Ϊ��null  
        n = jedis.hincrBy("hashMapkey", "hashMap7", -1); // �� hash����key ΪhashMapkey ����hashMap1 ��ֵ  ��ȥ 1  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap7��ֵ  ��ȥ 1 ���Ϊ��"+n);//����ֵ���� hash����key ΪhashMapkey ����hashMap7��ֵ  ��ȥ 1 ���Ϊ��-1  
          
        System.out.println("�� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ֮ǰ����Ϊ��"+jedis.hget("hashMapkey", "hashMap8"));//����ֵ���� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ֮ǰ����Ϊ��null  
        n = jedis.hincrBy("hashMapkey", "hashMap8", 2); // �� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ���Ϊ��"+n);//�� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ���Ϊ��2  
          
        //key ������ ִ�в��� ǰ�ȸ� ��Ĭ��ֵ ��ʼ Ϊ 0  ��ִ��set ��������ִ�� hincrby ����  
        System.out.println("�� hash����key ΪhashMapkey1 ����hashMap7 ��ֵ   ��ȥ 1 ֮ǰ����Ϊ��"+jedis.hget("hashMapkey1", "hashMap7"));//����ֵ���� hash����key ΪhashMapkey ����hashMap7 ��ֵ   ��ȥ 1 ֮ǰ����Ϊ��null  
        n = jedis.hincrBy("hashMapkey1", "hashMap7", -1); // �� hash����key ΪhashMapkey ����hashMap1 ��ֵ  ��ȥ 1  
        System.out.println("�� hash����key ΪhashMapkey ����hashMap7��ֵ  ��ȥ 1 ���Ϊ��"+n);//����ֵ���� hash����key ΪhashMapkey ����hashMap7��ֵ  ��ȥ 1 ���Ϊ��-1  
          
        System.out.println("�� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ֮ǰ����Ϊ��"+jedis.hget("hashMapkey1", "hashMap8"));//����ֵ���� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ֮ǰ����Ϊ��null  
        n = jedis.hincrBy("hashMapkey1", "hashMap8", 2); // �� hash����key ΪhashMapkey ����hashMap2 ��ֵ  ���� 2  
        System.out.println("�� hash����key ΪhashMapkey1 ����hashMap8 ��ֵ  ���� 2 ���Ϊ��"+n);//�� hash����key ΪhashMapkey ����hashMap8 ��ֵ  ���� 2 ���Ϊ��2  
          
        //incrbyfloat ���������� incrby �����������ֵ��double ���ȸ���  
    }
    
}