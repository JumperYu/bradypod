package bradypod.framework.lucene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
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
		luceneDemo.searchByTerm("title", "cloth", 100);
	}

	public LuceneMySQlDemo() {
		directory = new RAMDirectory();
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

	private Analyzer getAnalyzer() {
		return new SimpleAnalyzer();
	}

	/**
	 * 
	 */
	public void createIndex() {
		Connection conn = getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		if (conn == null) {
			System.out.println("get the connection error...");
			return;
		}
		String sql = "select id,title,price from t_item_info";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			IndexWriterConfig iwConfig = new IndexWriterConfig(getAnalyzer());
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);

			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String price = rs.getString(3);
				System.out.format("id:%d,title:%s,price:%s\n", id, title, price);
				Document doc = new Document();
				doc.add(new TextField("id", id + "", Field.Store.YES));
				doc.add(new TextField("title", title, Field.Store.YES));
				doc.add(new TextField("price", price, Field.Store.YES));
				iwriter.addDocument(doc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

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

			for (int i = 0; i < hits.length; i++) {
				Document doc = isearcher.doc(hits[i].doc);
				System.out.println("the ids is = " + doc.get("id") + " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
