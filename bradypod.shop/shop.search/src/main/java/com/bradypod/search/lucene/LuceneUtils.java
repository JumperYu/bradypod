package com.bradypod.search.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneUtils {
	private static final LuceneManager luceneManager = LuceneManager.getInstance();
	private static Analyzer analyzer = new StandardAnalyzer();

	/**
	 * 打开索引目录
	 * 
	 * @param luceneDir
	 * @return
	 * @throws IOException
	 */
	public static FSDirectory openFSDirectory(String luceneDir) {
		FSDirectory directory = null;
		try {
			directory = FSDirectory.open(Paths.get(luceneDir));
			/**
			 * 注意：isLocked方法内部会试图去获取Lock,如果获取到Lock，会关闭它，否则return
			 * false表示索引目录没有被锁， 这也就是为什么unlock方法被从IndexWriter类中移除的原因
			 */
			IndexWriter.isLocked(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directory;
	}

	/**
	 * 关闭索引目录并销毁
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public static void closeDirectory(Directory directory) throws IOException {
		if (null != directory) {
			directory.close();
			directory = null;
		}
	}

	/**
	 * 获取IndexWriter
	 * 
	 * @param dir
	 * @param config
	 * @return
	 */
	public static IndexWriter getIndexWrtier(Directory dir, IndexWriterConfig config) {
		return luceneManager.getIndexWriter(dir, config);
	}

	/**
	 * 获取IndexWriter
	 * 
	 * @param dir
	 * @param config
	 * @return
	 */
	public static IndexWriter getIndexWrtier(String directoryPath, IndexWriterConfig config) {
		FSDirectory directory = openFSDirectory(directoryPath);
		return luceneManager.getIndexWriter(directory, config);
	}

	/**
	 * 获取IndexReader
	 * 
	 * @param dir
	 * @param enableNRTReader
	 *            是否开启NRTReader
	 * @return
	 */
	public static IndexReader getIndexReader(Directory dir, boolean enableNRTReader) {
		return luceneManager.getIndexReader(dir, enableNRTReader);
	}

	/**
	 * 获取IndexReader(默认不启用NRTReader)
	 * 
	 * @param dir
	 * @return
	 */
	public static IndexReader getIndexReader(Directory dir) {
		return luceneManager.getIndexReader(dir);
	}

	/**
	 * 获取IndexSearcher
	 * 
	 * @param reader
	 *            IndexReader对象
	 * @param executor
	 *            如果你需要开启多线程查询，请提供ExecutorService对象参数
	 * @return
	 */
	public static IndexSearcher getIndexSearcher(IndexReader reader, ExecutorService executor) {
		return luceneManager.getIndexSearcher(reader, executor);
	}

	/**
	 * 获取IndexSearcher(不支持多线程查询)
	 * 
	 * @param reader
	 *            IndexReader对象
	 * @return
	 */
	public static IndexSearcher getIndexSearcher(IndexReader reader) {
		return luceneManager.getIndexSearcher(reader);
	}

	/**
	 * 创建QueryParser对象
	 * 
	 * @param field
	 * @param analyzer
	 * @return
	 */
	public static QueryParser createQueryParser(String field, Analyzer analyzer) {
		return new QueryParser(field, analyzer);
	}

	/**
	 * 关闭IndexReader
	 * 
	 * @param reader
	 */
	public static void closeIndexReader(IndexReader reader) {
		if (null != reader) {
			try {
				reader.close();
				reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭IndexWriter
	 * 
	 * @param writer
	 */
	public static void closeIndexWriter(IndexWriter writer) {
		if (null != writer) {
			try {
				writer.close();
				writer = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭IndexReader和IndexWriter
	 * 
	 * @param reader
	 * @param writer
	 */
	public static void closeAll(IndexReader reader, IndexWriter writer) {
		closeIndexReader(reader);
		closeIndexWriter(writer);
	}

	/**
	 * 删除索引[注意：请自己关闭IndexWriter对象]
	 * 
	 * @param writer
	 * @param field
	 * @param value
	 */
	public static void deleteIndex(IndexWriter writer, String field, String value) {
		try {
			writer.deleteDocuments(new Term[] { new Term(field, value) });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除索引[注意：请自己关闭IndexWriter对象]
	 * 
	 * @param writer
	 * @param query
	 */
	public static void deleteIndex(IndexWriter writer, Query query) {
		try {
			writer.deleteDocuments(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量删除索引[注意：请自己关闭IndexWriter对象]
	 * 
	 * @param writer
	 * @param terms
	 */
	public static void deleteIndexs(IndexWriter writer, Term[] terms) {
		try {
			writer.deleteDocuments(terms);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量删除索引[注意：请自己关闭IndexWriter对象]
	 * 
	 * @param writer
	 * @param querys
	 */
	public static void deleteIndexs(IndexWriter writer, Query[] querys) {
		try {
			writer.deleteDocuments(querys);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除所有索引文档
	 * 
	 * @param writer
	 */
	public static void deleteAllIndex(IndexWriter writer) {
		try {
			writer.deleteAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新索引文档
	 * 
	 * @param writer
	 * @param term
	 * @param document
	 */
	public static void updateIndex(IndexWriter writer, Term term, Document document) {
		try {
			writer.updateDocument(term, document);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新索引文档
	 * 
	 * @param writer
	 * @param term
	 * @param document
	 */
	public static void updateIndex(IndexWriter writer, String field, String value, Document document) {
		updateIndex(writer, new Term(field, value), document);
	}

	/**
	 * 添加索引文档
	 * 
	 * @param writer
	 * @param doc
	 */
	public static void addIndex(IndexWriter writer, Document document) {
		updateIndex(writer, null, document);
	}

	/**
	 * 索引文档查询
	 * 
	 * @param searcher
	 * @param query
	 * @return
	 */
	public static List<Document> query(IndexSearcher searcher, Query query) {
		TopDocs topDocs = null;
		try {
			topDocs = searcher.search(query, Integer.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScoreDoc[] scores = topDocs.scoreDocs;
		int length = scores.length;
		if (length <= 0) {
			return Collections.emptyList();
		}
		List<Document> docList = new ArrayList<Document>();
		try {
			for (int i = 0; i < length; i++) {
				Document doc = searcher.doc(scores[i].doc);
				docList.add(doc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docList;
	}

	/**
	 * 返回索引文档的总数[注意：请自己手动关闭IndexReader]
	 * 
	 * @param reader
	 * @return
	 */
	public static int getIndexTotalCount(IndexReader reader) {
		return reader.numDocs();
	}

	/**
	 * 返回索引文档中最大文档ID[注意：请自己手动关闭IndexReader]
	 * 
	 * @param reader
	 * @return
	 */
	public static int getMaxDocId(IndexReader reader) {
		return reader.maxDoc();
	}

	/**
	 * 返回已经删除尚未提交的文档总数[注意：请自己手动关闭IndexReader]
	 * 
	 * @param reader
	 * @return
	 */
	public static int getDeletedDocNum(IndexReader reader) {
		return getMaxDocId(reader) - getIndexTotalCount(reader);
	}

	/**
	 * 根据docId查询索引文档
	 * 
	 * @param reader
	 *            IndexReader对象
	 * @param docID
	 *            documentId
	 * @param fieldsToLoad
	 *            需要返回的field
	 * @return
	 */
	public static Document findDocumentByDocId(IndexReader reader, int docID,
			Set<String> fieldsToLoad) {
		try {
			return reader.document(docID, fieldsToLoad);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 根据docId查询索引文档
	 * 
	 * @param reader
	 *            IndexReader对象
	 * @param docID
	 *            documentId
	 * @return
	 */
	public static Document findDocumentByDocId(IndexReader reader, int docID) {
		return findDocumentByDocId(reader, docID, null);
	}
	
}
