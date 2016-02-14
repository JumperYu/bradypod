package bradypod.framework.lucene.official.demo;

import java.io.IOException;
import java.util.Random;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import bradypod.framework.lucene.LuceneManager;
import bradypod.framework.lucene.LuceneCallback;

public class LuceneDemo {

	public static void main(String[] args) throws IOException,
			InterruptedException {

		final LuceneManager indexUtil = new LuceneManager("E://demo/index");

		final long seconds = 1000;

		// 实时搜搜
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					// wait
					try {
						indexUtil.search(new LuceneCallback<String>() {
							@Override
							public String executeQuery(IndexSearcher searcher) {
								Query query = new FuzzyQuery(new Term("test",
										"中文"));
								try {
									TopDocs topDoc = searcher.search(query,
											1000);
									System.out.println("total hits: "
											+ topDoc.totalHits);
								} catch (IOException e) {
								}
								return null;
							}
						});
						Thread.sleep(seconds);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		// 实时写入
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					// wait
					try {
						IndexWriter writer = indexUtil.getWriter();
						Document document = new Document();
						document.add(new TextField("test", "中文"
								+ new Random().nextInt(100000), Store.YES));
						writer.addDocument(document);
						writer.commit();
						Thread.sleep(seconds);
					} catch (InterruptedException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		// wait for quit
		while (true) {

		}
	}

}
