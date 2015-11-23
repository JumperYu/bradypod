package test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Lucene学习
 *
 * @author zengxm
 * @date 2015年11月23日
 *
 */
public class IndexWriterDemo {

	public static void main(String[] args) {
		// createIndex();
		readAllIndexDocs(null);
	}

	public static void createIndex() {
		IndexWriter writer = null;
		try {
			Directory directory = FSDirectory.open(Paths.get("E://index"));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, config);
			Document document = null;
			File dir = new File("E:/学习文档");
			for (File file : dir.listFiles()) {
				System.out.println("filename:" + file.getName());
				document = new Document();
				document.add(new LongField("modified", file.lastModified(),
						Field.Store.NO));
				document.add(new TextField("contents", new FileReader(file)));
				document.add(new StringField("path", file.toString(),
						Field.Store.YES));
				writer.addDocument(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查看所有索引文件
	 * 
	 * @author IT学习者-螃蟹
	 * @官网：http://www.itxxz.com
	 * @date 2014-11-17
	 * @param writer
	 * @param file
	 * @throws IOException
	 */
	public static void readAllIndexDocs(IndexWriter writer) {
		try {
			Directory dir = FSDirectory.open(Paths.get("E:/index"));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			reader.maxDoc();
			Document doc = null;
			for (int i = 0; i < reader.maxDoc(); i++) {
				doc = searcher.doc(i);
				System.out.println("Doc [" + i + "] : filename: "
						+ doc.get("filename") + ", Path: " + doc.get("path") + ", Content:" + doc.get("modified"));
				System.out.println(doc.getField("modified"));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
