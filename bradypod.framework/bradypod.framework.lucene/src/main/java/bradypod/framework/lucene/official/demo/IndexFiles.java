package bradypod.framework.lucene.official.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Lucene索引文件
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年12月9日 下午5:17:43
 */
public class IndexFiles {

	public static void main(String[] args) throws IOException {
		// 指定启动参数
		String indexPath = "D://index";
		String filesDir = "C://Users/Administrator/Desktop/sockets";
		boolean created = true;

		// 创建索引
		Directory dir = FSDirectory.open(Paths.get(indexPath));
		IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
		iwc.setOpenMode(created ? OpenMode.CREATE : OpenMode.APPEND);
		IndexWriter writer = new IndexWriter(dir, iwc);

		indexDocs(writer, Paths.get(filesDir));

		writer.close();
		// 结束
	}

	/**
	 * 索引一个路径下的所有文件
	 * 
	 * @param writer
	 *            - IndexWriter
	 * @param path
	 *            - 文件/目录
	 * 
	 * @throws IOException
	 */
	static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		if (Files.isDirectory(path)) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {
					try {
						indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
					} catch (IOException ignore) {
						// don't index files that can't be read.
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
		}
		System.out.println("index files:" + writer.numDocs());
	}

	/**
	 * 添加索引
	 * 
	 * @param writer
	 *            - 写入器
	 * @param file
	 *            - 文件
	 * @param lastModified
	 *            - long mills
	 * @throws IOException
	 */
	static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
		try (InputStream stream = Files.newInputStream(file)) {
			// 创建一行数据
			Document doc = new Document();
			// 添加列
			Field pathField = new TextField("filename", file.getFileName().toString(),
					Field.Store.YES);
			doc.add(pathField);
			doc.add(new LongField("modified", lastModified, Field.Store.NO));
			doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream,
					StandardCharsets.UTF_8))));
			// 由外部指定更新还是创建
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				System.out.println("adding index:" + file.getFileName());
				// 加入索引
				writer.addDocument(doc);
			} else {
				System.out.println("updating index:" + file.getFileName());
				// 更新索引
				writer.updateDocument(new Term("path", file.toString()), doc);
			}
		}// --> end try-resource
	}
}
