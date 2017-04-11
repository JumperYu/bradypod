
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class TestHbase {

	Logger logger = LoggerFactory.getLogger(TestHbase.class);

	private static Configuration conf = null;
	private static Connection conn = null;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "127.0.0.1");
		// conf.set("zookeeper.znode.parent", "/hbase");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.client.pause", "50");
		conf.set("hbase.client.retries.number", "1");
		conf.set("hbase.rpc.timeout", "1001");
		conf.set("hbase.client.operation.timeout", "1002");
		conf.set("hbase.client.scanner.timeout.period", "1003");

		try {
			/**
			 * HTable类读写时是非线程安全的，已经标记为Deprecated
			 * 建议通过org.apache.hadoop.hbase.client.Connection来获取实例
			 */
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		// 创建一个数据库管理员
		// Admin admin = conn.getAdmin();

		TestHbase hbase = new TestHbase();

		// hbase.createTable(admin, "water_meter", new String[] { "famil01" });

//		hbase.putData("test", new String[] { "r1" }, "cf", "c1", "jha");
		
//		hbase.getData("test", "r1", "cf");
		
		hbase.deleteData("test", "r1", "cf", "c2");
	}

	public void listTable(Admin admin) throws IOException {
		TableName[] names = admin.listTableNames();
		for (TableName tableName : names) {
			System.out.println("Table Name is " + tableName.getNameAsString());
		}
	}

	public boolean isExists(Admin admin, String tableName) throws IOException {
		boolean isExist = admin.tableExists(TableName.valueOf(tableName));
		return isExist;
	}

	/**
	 * 创建表
	 * 
	 * @param admin
	 * @param tableName
	 * @throws IOException
	 */
	public void createTable(Admin admin, String tableName, String[] columnFamilys) throws IOException {
		HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));

		for (String columnFamily : columnFamilys) {
			table.addFamily(new HColumnDescriptor(columnFamily));
		}

		admin.createTable(table);
	}

	public void deleteTable(Admin admin, String name) throws IOException {
		TableName tableName = TableName.valueOf(name);
		// 先disable再delete
		admin.disableTable(tableName);
		admin.deleteTable(tableName);
	}

	public void putData(String tableName, String[] rowKeys, String columnFamily, String qualifier, String value)
			throws TableNotFoundException, IOException {

		logger.info("begin put data");

		Table table = conn.getTable(TableName.valueOf(tableName));

		List<Put> puts = Lists.newArrayList();
		for (String rowKey : rowKeys) {
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value));
			puts.add(put);
		}
		table.put(puts);

		table.close();

		logger.error("put data done");
	}

	public void getData(String tableName, String rowKey, String columnFamily) throws IOException {
		byte[] family = Bytes.toBytes(columnFamily);
		byte[] row = Bytes.toBytes(rowKey);
		Table table = conn.getTable(TableName.valueOf(tableName));

		Get get = new Get(row);
		get.addFamily(family);
		// 也可以通过addFamily或addColumn来限定查询的数据
		Result result = table.get(get);
		List<Cell> cells = result.listCells();
		for (Cell cell : cells) {
			String qualifier = new String(CellUtil.cloneQualifier(cell));
			String value = new String(CellUtil.cloneValue(cell), "UTF-8");
			// @Deprecated
			// LOG.info(cell.getQualifier() + "\t" + cell.getValue());
			System.out.println(qualifier + "\t" + value);
		}
	}
	
	public void deleteData(String tableName, String rowKey, String columnFamily, String qualifier) throws IOException {
		
		byte[] family = Bytes.toBytes(columnFamily);
		byte[] row = Bytes.toBytes(rowKey);
		Table table = conn.getTable(TableName.valueOf(tableName));
		
		Delete delete = new Delete(row);
		
		delete.addColumn(family, Bytes.toBytes(qualifier));
		
		table.delete(delete);
		
	}
}
