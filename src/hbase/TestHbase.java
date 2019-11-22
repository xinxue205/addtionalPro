package hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
 
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
 
/**
 * JDBC 连接 Hbase测试
 * 
 * @author lucky
 *
 */
public class TestHbase {
 
	/**
	 * 列族
	 */
	private static final String FAMILY_NAMES = "a,b,c,d,e,f";
	/**
	 * 表名
	 */
	private static final String TABLE_NAME = "bigTab";
	/**
	 * 连接对象
	 */
	private static Connection conn;
	/**
	 * Hbase集群地址，多个地址用“,”分开
	 */
	private static final String HOSTS = "127.0.0.1";
 
	static {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", HOSTS);
		try {
			conn = ConnectionFactory.createConnection(conf);
			createTable(TABLE_NAME, FAMILY_NAMES);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * 创建表
	 * 
	 * @param tableName
	 *            表名
	 * @param familyNames
	 *            列族，多个列族用“,”分开
	 * @throws IOException
	 */
	private static void createTable(String tableName, String familyNames) throws IOException {
		Admin admin = null;
		TableName tn = TableName.valueOf(tableName);
 
		admin = conn.getAdmin();
		if (!admin.tableExists(tn)) {
			HTableDescriptor descriptor = new HTableDescriptor(tn);
			for (String familyName : familyNames.split(",")) {
 
				descriptor.addFamily(new HColumnDescriptor(familyName));
			}
			admin.createTable(descriptor);
			System.out.println("创建表：" + tableName);
		}
 
	}
 
	/**
	 * 删除表
	 * 
	 * @param tableName
	 *            表名
	 * @throws IOException
	 */
	private static void dropTable(String tableName) throws IOException {
		Admin admin = null;
		try {
			TableName tn = TableName.valueOf(tableName);
			admin = conn.getAdmin();
			if (admin.tableExists(tn)) {
				admin.disableTable(tn);
				admin.deleteTable(tn);
				System.out.println("删除表：" + tableName);
			}
		} finally {
			IOUtils.closeQuietly(admin);
		}
 
	}
 
	/**
	 * 添加数据
	 * 
	 * @param tableName
	 *            表名
	 * @param rowKey
	 *            行号
	 * @param datas
	 *            数据
	 * @throws IOException
	 */
	private static void add(String tableName, String rowKey, List<String[]> datas) throws IOException {
 
		Table table = conn.getTable(TableName.valueOf(tableName));
		try {
			Put list = new Put(rowKey.getBytes());
			for (String[] data : datas) {
				list.addColumn(data[0].getBytes(), data[1].getBytes(), data[2].getBytes());
			}
			table.put(list);
		} finally {
			if (table != null) {
				IOUtils.closeQuietly(table);
			}
		}
	}
 
	/**
	 * 查看所有数据
	 * 
	 * @param tableName
	 * @throws IOException
	 */
	private static void scan(String tableName) throws IOException {
 
		Table table = conn.getTable(TableName.valueOf(tableName));
		try {
			ResultScanner rs = table.getScanner(new Scan());
			for (Result r : rs) {
				NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = r.getMap();
				Set<Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>>> set = map.entrySet();
				for (Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : set) {
 
					Set<Entry<byte[], NavigableMap<Long, byte[]>>> entrySet = entry.getValue().entrySet();
					for (Entry<byte[], NavigableMap<Long, byte[]>> entry2 : entrySet) {
						System.out.print(new String(r.getRow()));
						System.out.print("\t");
						System.out.print(new String(entry.getKey()));
						System.out.print(":");
						System.out.print(new String(entry2.getKey()));
						System.out.print(" => ");
						System.out.println(new String(entry2.getValue().firstEntry().getValue()));
					}
				}
			}
		} finally {
			if (table != null) {
				IOUtils.closeQuietly(table);
			}
		}
	}
 
	/**
	 * 根据rowKey查询
	 * 
	 * @param tableName
	 * @param rowKey
	 * @throws IOException
	 */
	private static void scanByrowKey(String tableName, String rowKey) throws IOException {
 
		Table table = conn.getTable(TableName.valueOf(tableName));
		try {
			Result r = table.get(new Get(rowKey.getBytes()));
 
			NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = r.getMap();
			Set<Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>>> set = map.entrySet();
			for (Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : set) {
 
				Set<Entry<byte[], NavigableMap<Long, byte[]>>> entrySet = entry.getValue().entrySet();
				for (Entry<byte[], NavigableMap<Long, byte[]>> entry2 : entrySet) {
					System.out.print(new String(r.getRow()));
					System.out.print("\t");
					System.out.print(new String(entry.getKey()));
					System.out.print(":");
					System.out.print(new String(entry2.getKey()));
					System.out.print(" => ");
					System.out.println(new String(entry2.getValue().firstEntry().getValue()));
				}
			}
 
		} finally {
			if (table != null) {
				IOUtils.closeQuietly(table);
			}
		}
	}
 
	public static void main(String[] args) throws IOException {
		List<String[]> list = new ArrayList<>();
		list.add("a, a1, www1".split(","));
		list.add("a, a2, www2".split(","));
		list.add("a, a3, www3".split(","));
		list.add("a, a4, www4".split(","));
		list.add("b, a1, www5".split(","));
		list.add("b, a2, www6".split(","));
		list.add("b, a3, www7".split(","));
		list.add("b, a4, www8".split(","));
		list.add("c, a5, www9".split(","));
		list.add("c, a6, www10".split(","));
		list.add("c, a1, www11".split(","));
		list.add("c, a2, www12".split(","));
		list.add("d, a3, www13".split(","));
		list.add("e, a4, www14".split(","));
		list.add("f, a5, www15".split(","));
		add(TABLE_NAME, "100001", list);
		list = new ArrayList<>();
		list.add("a, a1, vvv1".split(","));
		list.add("a, a2, vvv2".split(","));
		list.add("a, a3, vvv3".split(","));
		list.add("a, a4, vvv4".split(","));
		list.add("b, a1, vvv5".split(","));
		list.add("b, a2, vvv6".split(","));
		list.add("b, a3, vvv7".split(","));
		list.add("b, a4, vvv8".split(","));
		list.add("c, a5, vvv9".split(","));
		list.add("c, a6, vvv10".split(","));
		list.add("c, a1, vvv11".split(","));
		list.add("c, a2, vvv12".split(","));
		list.add("d, a3, vvv13".split(","));
		list.add("e, a4, vvv14".split(","));
		list.add("f, a5, vvv15".split(","));
		add(TABLE_NAME, "100002", list);
 
		scan(TABLE_NAME);
		System.out.println();
		scanByrowKey(TABLE_NAME, "100002");
		dropTable(TABLE_NAME);
	}
}
