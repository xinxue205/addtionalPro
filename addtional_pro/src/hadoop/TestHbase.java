package hadoop;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHbase {
	private static final String table = "test_table";

	private static Configuration cf = null;

	private static Connection cnn = null;

	/**
	 * 
	 * @param tableName 表名
	 * @param familys   列族列表
	 * @throws IOException
	 */
	public void createTable(TableName tableName, String[] familys) throws IOException {
		Admin admin = cnn.getAdmin();

		if (admin.tableExists(tableName)) {
			System.out.println(tableName.toString() + "表已经存在");
		} else {
			HTableDescriptor descriptor = new HTableDescriptor(tableName);// 表对象

			HColumnDescriptor columnDescriptor = null;// 一个列族对象

			for (String fm : familys) {
				columnDescriptor = new HColumnDescriptor(fm);
				descriptor.addFamily(columnDescriptor);
			}

			admin.createTable(descriptor);// 创建表

			System.out.println("表创建成功！！");

		}
	}

	@Before
	public void before() {
		// System.out.println("程序初始化........");
		cf = HBaseConfiguration.create();

        cf.set("hbase.zookeeper.property.clientPort", "5181");
		try {
			cnn = ConnectionFactory.createConnection(cf);// 通过配置获取链接
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(cf.get("hbase.zookeeper.quorum"));
	}

	@Test
	public void test() throws IOException {
		TableName tableName = TableName.valueOf(table);
		this.createTable(tableName, new String[] { "fm1", "fm2" });
	}

	@After
	public void after() throws IOException {

	}

}
