package bradypod.framework.redis;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHbase {

	private static Configuration conf = null;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.0.106");
		conf.set("hbase.zookeeper.property.clientPort", "2181"); // 2181
	}

	public static void main(String[] args) throws IOException {
		createTable("water_meter", new String[] { "famil01" });
	}

	// 创建数据库表
	public static void createTable(String tableName, String[] columnFamilys) throws IOException {
		// 创建一个数据库管理员
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		boolean isExist = admin.tableExists(Bytes.toBytes(tableName));
		if (isExist) {
			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
			
			table.addFamily(new HColumnDescriptor(Bytes.toBytes("fc1")));
			table.addFamily(new HColumnDescriptor(Bytes.toBytes("fc2")));
			
			admin.createTable(table);
		}
		admin.close();
	}
	
}
