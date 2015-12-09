package com.bradypod.search.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.lang.time.StopWatch;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndexer {

	public static void main(String[] args) {
		// 开始时间
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		// 指定索引目录和读取目录
		String indexDir = "E://index";
		String dataDir = "E://work/after89/bradypod/bradypod.commons/commons-util/src/main/java/com/bradypod/util/redis";
		// 创建索引
		LuceneIndexer indexer = new LuceneIndexer(indexDir);
		int numIndexed = indexer.index(dataDir, new TextFileFilter());
		indexer.close();
		// 结束时间
		stopWatch.stop();
		System.out.println("Indexing " + numIndexed + " files took " + stopWatch.getTime()
				+ " millseconds");
	}

	private IndexWriter writer;

	/**
	 * Constructer
	 * 
	 * @param dir
	 */
	public LuceneIndexer(String dir) {
		try {
			Directory directory = FSDirectory.open(Paths.get(dir));
			writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int index(String dir, FileFilter fileFilter) {
		File[] files = new File(dir).listFiles();
		for (File file : files) {
			if (fileFilter == null || fileFilter.accept(file)) {
				indexFile(file);
			}
		}
		return writer.numDocs();
	}

	private static class TextFileFilter implements FileFilter {
		@Override
		public boolean accept(File path) {
			return path.getName().toLowerCase().endsWith(".java");
		}
	}

	private void indexFile(File file) {
		try {
			System.out.println("Indexing " + file.getName());
			Document doc = getDocument(file);
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为这个文件添加索引
	 * 
	 * @param file
	 */
	protected Document getDocument(File file) {
		Document doc = new Document();
		try {

			doc.add(new TextField("contents", new FileReader(file)));
			doc.add(new TextField("filename", file.getName(), Store.YES));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 关闭writer
	 */
	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
