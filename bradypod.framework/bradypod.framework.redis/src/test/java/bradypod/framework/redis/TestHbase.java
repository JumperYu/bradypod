package bradypod.framework.redis;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;


public class TestHbase {

	private static Configuration conf = null;
	private static Connection conn = null;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "127.0.0.1");
//        conf.set("zookeeper.znode.parent", "/hbase");
        conf.set("hbase.zookeeper.property.clientPort", "2181"); 
        conf.set("hbase.client.pause", "50"); 
        conf.set("hbase.client.retries.number", "1"); 
        conf.set("hbase.rpc.timeout", "1001"); 
        conf.set("hbase.client.operation.timeout", "1002"); 
        conf.set("hbase.client.scanner.timeout.period", "1003");
        
        try {
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		createTable("water_meter", new String[] { "famil01" });
	}

	// 创建数据库表
	public static void createTable(String tableName, String[] columnFamilys) throws IOException {
		// 创建一个数据库管理员
		Admin admin = conn.getAdmin();
		
		boolean isExist = admin.tableExists(TableName.valueOf(tableName));
		if (!isExist) {
			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
			
			table.addFamily(new HColumnDescriptor("fc1"));
//			table.addFamily(new HColumnDescriptor(Bytes.toBytes("fc2")));
			
			admin.createTable(table);
		}
		admin.close();
	}
	
}
