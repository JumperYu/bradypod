package com.bradypod.search.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class SearchFiles {

	private SearchFiles() {
	}

	/** Simple command-line based search demo. */
	public static void main(String[] argxs) throws Exception {
		String index = "D://index";
		String field = "contents";
		String queryString = "java";

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		QueryParser parser = new QueryParser(field, analyzer);

		Query query = parser.parse(queryString);
		System.out.println("Searching for: " + query.toString(field));

		//searcher.search(query, 100);

		doPagingSearch(searcher, query, 1, false, true);

		reader.close();
	}

	/**
	 * This demonstrates a typical paging search scenario, where the search
	 * engine presents pages of size n to the user. The user can then go to the
	 * next page if interested in the next hits.
	 * 
	 * When the query is executed for the first time, then only enough results
	 * are collected to fill 5 result pages. If the user wants to page beyond
	 * this limit, then the query is executed another time and all hits are
	 * collected.
	 * 
	 */
	public static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage,
			boolean raw, boolean interactive) throws IOException {

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		for (int i = 0; i < hits.length; i++) {
			if (raw) { // output raw format
				System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
				continue;
			}

			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null) {
				System.out.println((i + 1) + ". " + path);
				String title = doc.get("title");
				if (title != null) {
					System.out.println("   Title: " + doc.get("title"));
				}
			} else {
				System.out.println((i + 1) + ". " + "No path for this document");
			}

		}
	}
}
