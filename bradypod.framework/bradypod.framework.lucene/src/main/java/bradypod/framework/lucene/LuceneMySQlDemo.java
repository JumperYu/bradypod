package bradypod.framework.lucene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Lucene + SmartChineseAnalyzer + MySQL
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月25日 上午11:19:12
 */
public class LuceneMySQlDemo {

	private static final String driverClassName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://mysql.bradypod.com:3306/item_center?characterEncoding=utf-8";
	private static final String username = "zxm";
	private static final String password = "qwerty";

	private Directory directory = null;
	private DirectoryReader ireader = null;
	private IndexWriter iwriter = null;

	private Connection conn;

	public static void main(String[] args) {
		LuceneMySQlDemo luceneDemo = new LuceneMySQlDemo();
		luceneDemo.createIndex();
		luceneDemo.searchByTerm("title", "女包", 100);
	}

	public LuceneMySQlDemo() {
		// 初始化内存索引
		directory = new RAMDirectory();
	}

	/**
	 * 搜索
	 * 
	 * @param field
	 * @param keyword
	 * @param num
	 */
	public void searchByTerm(String field, String keyword, int num) {
		IndexSearcher isearcher = getSearcher();
		Analyzer analyzer = getAnalyzer();
		// 使用QueryParser查询分析器构造Query对象
		QueryParser qp = new QueryParser(field, analyzer);
		// 这句所起效果？
		qp.setDefaultOperator(QueryParser.OR_OPERATOR);
		try {
			Query query = qp.parse(keyword);
			ScoreDoc[] hits;

			// 注意searcher的几个方法
			hits = isearcher.search(query, num).scoreDocs;
			
			System.out.println();
			
			for (int i = 0; i < hits.length; i++) {
				Document doc = isearcher.doc(hits[i].doc);
				System.out.format("searched data id:%s,title:%s,price:%s\n", doc.getField("id")
						.stringValue(), doc.getField("title").stringValue(), doc.getField("price")
						.stringValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得搜索器
	 */
	public IndexSearcher getSearcher() {
		try {
			if (ireader == null) {
				ireader = DirectoryReader.open(directory);
			} else {
				DirectoryReader tr = DirectoryReader.openIfChanged(ireader);
				if (tr != null) {
					ireader.close();
					ireader = tr;
				}
			}
			return new IndexSearcher(ireader);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取mysql连接
	 */
	public Connection getConnection() {
		if (this.conn == null) {
			try {
				Class.forName(driverClassName);
				conn = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return conn;
	}

	/**
	 * 获取分析器
	 */
	private Analyzer getAnalyzer() {
		return new SmartChineseAnalyzer();
	}

	/**
	 * 从mysql获取数据并创建索引
	 */
	public void createIndex() {
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try (Connection conn = getConnection();) {
			// sql
			String sql = "select id, title, price from t_item_info";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			IndexWriterConfig iwConfig = new IndexWriterConfig(getAnalyzer());
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);

			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String price = rs.getString(3);
				System.out.format("selected data id:%d,title:%s,price:%s\n", id, title, price);
				Document doc = new Document();
				doc.add(new TextField("id", id + "", Field.Store.YES));
				doc.add(new TextField("title", title, Field.Store.YES));
				doc.add(new TextField("price", price, Field.Store.YES));
				iwriter.addDocument(doc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (iwriter != null)
					iwriter.close();
				rs.close();
				pstmt.close();
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
