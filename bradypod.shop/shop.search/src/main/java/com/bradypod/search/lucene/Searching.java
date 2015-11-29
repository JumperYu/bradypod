package com.bradypod.search.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searching {

	public static void main(String[] args) {
		Searching.deleteTest();
//		Searching.search("E://index", "1.txt");
	}

	/**
	 * @param indexDir
	 * @param q
	 */
	public static void search(String indexDir, String text) {
		try {
			Analyzer analyzer = new StandardAnalyzer();
			Directory directory = FSDirectory.open(Paths.get(indexDir));
			IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser("filename", analyzer);
			Query query = parser.parse(text);
			TopDocs topDocs = searcher.search(query, 1000);
			System.out.println("总共匹配多少个：" + topDocs.totalHits);
			ScoreDoc[] hits = topDocs.scoreDocs;
			// 应该与topDocs.totalHits相同
			System.out.println("多少条数据：" + hits.length);
			for (ScoreDoc scoreDoc : hits) {
				System.out.println("匹配得分：" + scoreDoc.score);
				System.out.println("文档索引ID：" + scoreDoc.doc);
				Document document = searcher.doc(scoreDoc.doc);
				System.out.println(document.get("filename"));
			}
			reader.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteTest(){
		IndexWriter writer = getWriter("E://index");
		try {
			System.out.println("writer has docs : " + writer.numDocs());
//			writer.deleteDocuments(new Term("id", "1"));
			writer.deleteAll();
			writer.commit();
			System.out.println("writer has dele:" + writer.hasDeletions());
			System.out.println("writer has docs : " + writer.maxDoc());
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取写操作
	 * 
	 * @param index
	 * @return
	 */
	public static IndexWriter getWriter(String index) {
		IndexWriter writer = null;
		try {
			Directory directory = FSDirectory.open(Paths.get(index));
			writer = new IndexWriter(directory, new IndexWriterConfig(
					new StandardAnalyzer()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
}
