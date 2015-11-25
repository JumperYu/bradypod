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

public class Indexer {

	public static void main(String[] args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String indexDir = "E://index";
		String dataDir = "E://";
		Indexer indexer = new Indexer(indexDir);
		int numIndexed;
		numIndexed = indexer.index(dataDir, new TextFileFilter());
		indexer.close();
		stopWatch.stop();
		System.out.println("Indexing " + numIndexed + " files took " + stopWatch.getTime()
				+ " millseconds");
	}

	private IndexWriter writer;

	public Indexer(String dir) {
		try {
			Directory directory = FSDirectory.open(Paths.get(dir));
			writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			return path.getName().toLowerCase().endsWith(".txt");
		}
	}

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

	private void indexFile(File file) {
		try {
			System.out.println("Indexing " + file.getCanonicalPath());
			Document doc = getDocument(file);
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
