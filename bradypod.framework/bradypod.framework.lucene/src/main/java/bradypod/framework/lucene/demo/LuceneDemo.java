package bradypod.framework.lucene.demo;

import java.io.IOException;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import bradypod.framework.lucene.LuceneUtils;

public class LuceneDemo {
	
	public static void main(String[] args) throws IOException {
		
		Directory directory = LuceneUtils.openFSDirectory("E://index");
		
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		
		IndexWriter writer = LuceneUtils.getIndexWrtier(directory, config);
		
		Document document = new Document();
		document.add(new StringField("xx", "123", Field.Store.YES));
		
		writer.addDocument(document);
		
		writer.close();
	}
	
}
