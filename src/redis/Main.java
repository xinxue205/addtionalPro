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
        jedis.hset("hsetkey", "hashKey", "hashValue");//将哈希表key 中的域field 的值设为value 。如果key 不存在，一个新的哈希表被创建并进行HSET 操作。如果域field 已经存在于哈希表中，旧值将被覆盖。  
        String hash = jedis.hget("hsetkey", "hashKey");//返回哈希表key 中给定域field 的值  
        System.out.println("测试 hset hget ： hsetkey 的返回值："+hash);  
          
        //hsetnx  当且仅当域field 不存在。若域field(指第二个参数) 已经存在，该操作无效。   
        long n = jedis.hsetnx("hsetkeynx", "hashkeynx", "hashvaluenx");  
        System.out.println(n!=0?"操作成功":"操作失败");  
        n = jedis.hsetnx("hsetkeynx", "hashkey", "hashvaluenx");  
        System.out.println(n!=0?"操作成功":"操作失败");  
        n = jedis.hsetnx("hsetkeynx", "hashkey", "hashvaluenx");  
        System.out.println(n!=0?"操作成功":"操作失败");  
          
        //hmset hmget  
        HashMap<String, String> hashMap = new HashMap<String, String>();  
        hashMap.put("hashMap1", "hashValue1");  
        hashMap.put("hashMap2", "hashValue2");  
        hashMap.put("hashMap3", "hashValue3");  
        hashMap.put("hashMap4", "hashValue4");  
        String status  = jedis.hmset("hashMapkey", hashMap);//如果命令执行成功，返回OK 。当key 不是哈希表(hash) 类型时，返回一个错误。  
        hash = jedis.hget("hashMapkey", "hashMap4");  
        System.out.println("OK".equals(status)?"操作成功  返回值："+hash:"操作失败");  
        //返回值： 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样  
        List<String> hashList = jedis.hmget("hashMapkey", "hashMap1 hashMap2 hashMap3 hashMap4".split(" "));  
        for(String value : hashList){  
            System.out.print("对应的value值：  "+value+" ");//返回值： 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样  
        }  
        System.out.println();  
          
        //hgetall  获得一个Map 返回key整个file域  
        Map<String,String> hashMapKey = jedis.hgetAll("hashMapkey");  
          
        // map 的第一种迭代方式  
        Set<Map.Entry<String, String>> entry = hashMapKey.entrySet();  
        Iterator<Map.Entry<String, String>> it = entry.iterator();  
        while(it.hasNext()){  
            Map.Entry<String, String> e  = it.next();  
            System.out.println("key: "+e.getKey()+"  value: "+e.getValue());  
        }  
          
        // map的第二种迭代方式  
        Set<String> keySet = hashMapKey.keySet();// map中的所有key在set中存放着，可以通过迭代set的方式 来获得key  
        Iterator<String> iter = keySet.iterator();  
        while(iter.hasNext()){  
            String key = iter.next();  
            String value = hashMapKey.get(key);  
        }  
          
      
        //hscan  类似于 scan 遍历库中 key 下所有的域   返回  file-value 以map 的形式；  
        ScanResult<Map.Entry<String, String>> hscanResult = jedis.hscan("hashMapkey", "0");  
        int cursor = hscanResult.getCursor(); // 返回0 说明遍历完成  
        System.out.println("游标"+cursor);  
        List<Map.Entry<String, String>> scanResult = hscanResult.getResult();  
        for(int m = 0;m < scanResult.size();m++){  
            Map.Entry<String, String> mapentry  = scanResult.get(m);  
            System.out.println("key: "+mapentry.getKey()+"  value: "+mapentry.getValue());  
        }  
          
        //hkeys  
        Set<String> setKey = jedis.hkeys("hashMapkey");// keys 返回 所有的key  ,hkeys 返回 key 下面的所有的 域  
        Iterator<String> itset = setKey.iterator();  
        String files = "";  
        while(itset.hasNext()){  
            files =files+" "+itset.next();  
        }  
        System.out.println("hashMapkey 中的所有域 为："+files);  
          
        //hvals 返回哈希表key 中所有域的值。可用版本： >= 2.0.0时间复杂度： O(N)，N 为哈希表的大小。返回值：一个包含哈希表中所有值的表。当key 不存在时，返回一个空表。  
        List<String> list = jedis.hvals("hashMapkey");  
        for(String s : list){  
            System.out.println(s);  
        }  
          
        // 以上 域对应的值是String  下面域对应的值 是list  
        Map<String,List<String>> testMapList = new HashMap<String,List<String>>();  
        List<String> testList = Arrays.asList("testList testList testList testList testList testList testList ");  
        List<String> testList1 = Arrays.asList("testList1 testList1 testList1 testList1 testList1 testList1 testList1 ");  
        List<String> testList2 = Arrays.asList("testList2 testList2 testList2 testList2 testList2 testList2 testList2 ");  
        testMapList.put("testList", testList);  
        testMapList.put("testList1", testList1);  
        testMapList.put("testList2", testList2);  
        String mapString  =  JSON.toJSONString(testMapList,true);// map 转为json串  
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
        // Map 里面存储实体对象  
        Map<String,Bar> testMapEntity = new HashMap<String,Bar>();  
        Bar bar = new Bar(); bar.setColor("red");bar.setName("lvxiaojian");  
        Bar bar1 = new Bar(); bar.setColor("green");bar.setName("wagnbo");  
        testMapEntity.put("bar", bar);  
        testMapEntity.put("bar1", bar1);  
        String entityString  =  JSON.toJSONString(testMapEntity,true);// map 转为json串  
        jedis.set("hashMapkey3", entityString);  
        entityString = jedis.get("hashMapkey3");  
        testMapEntity = (Map<String,Bar>)JSON.parse(entityString);  
        Set<String> entitySet = testMapEntity.keySet();  
        Iterator<String> iterentity = entitySet.iterator();  
        while(iterentity.hasNext()){  
            System.out.println("testMapEntity key: "+iterentity.next()+"testMapEntity value: "+testMapEntity.get(iterentity.next()));  
        }  
          
          
        //hlen  返回值：哈希表中域的数量。当key 不存在时，返回0 。  
        n = jedis.hlen("hashMapkey");  
        System.out.println("hashMapkey 中域的数量为： "+n);  
          
        //hdel  返回值: 被成功移除的域的数量，不包括被忽略的域  
        n = jedis.hdel("hashMapkey","hashMap1 hashMap2 hashMap3 hashMap4".split(" "));  
        System.out.println("被成功移除的域的数量，不包括被忽略的域: "+n);  
          
        //hexists  返回值：如果哈希表含有给定域，返回1 。如果哈希表不含有给定域，或key 不存在，返回0 。  
        boolean flag = jedis.hexists("hashMapkey", "hashMap1");  
        System.out.println(flag?"哈希表含有给定域":"哈希表不含有给定域");  
          
        hashMap.clear();// 清除map  
        hashMap.put("hashMap1", "1");  
        hashMap.put("hashMap2", "2");  
        hashMap.put("hashMap3", "3");  
        hashMap.put("hashMap4", "4");  
        hashMap.put("hashMap5", "5");  
        hashMap.put("hashMap6", "6");  
        jedis.hmset("hashMapkey", hashMap);  
        flag = jedis.hexists("hashMapkey", "hashMap1");  
        System.out.println(flag?"哈希表含有给定域":"哈希表不含有给定域");  
          
        //hincrBy  key 存在  域也存在的情况  返回值： 执行HINCRBY 命令之后，哈希表key 中域field 的值  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap1 的值   减去 1 之前数据为："+jedis.hget("hashMapkey", "hashMap1"));// 返回值：对 hash表中key 为hashMapkey 的域hashMap1 的值   减去 1 之前数据为：1  
        n = jedis.hincrBy("hashMapkey", "hashMap1", -1); // 对 hash表中key 为hashMapkey 的域hashMap1 的值  减去 1  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap1 的值  减去 1 结果为："+n);// 返回值：对 hash表中key 为hashMapkey 的域hashMap1 的值  减去 1 结果为：0  
          
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2 之前数据为："+jedis.hget("hashMapkey", "hashMap2"));//返回值：对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2 之前数据为：2  
        n = jedis.hincrBy("hashMapkey", "hashMap2", 2); // 对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2 结果为："+n);//返回值：对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2 结果为：4  
          
        // key 存在  域不存在的情况  做加 减 操作:从以下操作可以看出，当域不存在的时候，在执行操作的时候会先给域的值默认初始化为 0 在进行加减操作  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap7 的值   减去 1 之前数据为："+jedis.hget("hashMapkey", "hashMap7"));//返回值：对 hash表中key 为hashMapkey 的域hashMap7 的值   减去 1 之前数据为：null  
        n = jedis.hincrBy("hashMapkey", "hashMap7", -1); // 对 hash表中key 为hashMapkey 的域hashMap1 的值  减去 1  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap7的值  减去 1 结果为："+n);//返回值：对 hash表中key 为hashMapkey 的域hashMap7的值  减去 1 结果为：-1  
          
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 之前数据为："+jedis.hget("hashMapkey", "hashMap8"));//返回值：对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 之前数据为：null  
        n = jedis.hincrBy("hashMapkey", "hashMap8", 2); // 对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 结果为："+n);//对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 结果为：2  
          
        //key 不存在 执行操作 前先给 个默认值 初始 为 0  先执行set 操作，在执行 hincrby 操作  
        System.out.println("对 hash表中key 为hashMapkey1 的域hashMap7 的值   减去 1 之前数据为："+jedis.hget("hashMapkey1", "hashMap7"));//返回值：对 hash表中key 为hashMapkey 的域hashMap7 的值   减去 1 之前数据为：null  
        n = jedis.hincrBy("hashMapkey1", "hashMap7", -1); // 对 hash表中key 为hashMapkey 的域hashMap1 的值  减去 1  
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap7的值  减去 1 结果为："+n);//返回值：对 hash表中key 为hashMapkey 的域hashMap7的值  减去 1 结果为：-1  
          
        System.out.println("对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 之前数据为："+jedis.hget("hashMapkey1", "hashMap8"));//返回值：对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 之前数据为：null  
        n = jedis.hincrBy("hashMapkey1", "hashMap8", 2); // 对 hash表中key 为hashMapkey 的域hashMap2 的值  加上 2  
        System.out.println("对 hash表中key 为hashMapkey1 的域hashMap8 的值  加上 2 结果为："+n);//对 hash表中key 为hashMapkey 的域hashMap8 的值  加上 2 结果为：2  
          
        //incrbyfloat 操作类似于 incrby 不过这个返回值是double 精度更高  
    }
    
}