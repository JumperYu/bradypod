//LuceneSearcher.java
package bradypod.framework.lucene.book.demo._01;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class LuceneSearcher {
	private JTextField jtfa;
	private JButton jba;
	private JTextField jtfb;
	private static JTextArea jta;
	private JTextField jtfc;
	private JButton jbd;
	private JButton jbe;

	protected void createAndShowGUI() {

		// 设置跨平台外观感觉
		// String lf=UIManager.getCrossPlatformLookAndFeelClassName();

		// GTK
		// String lf="com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

		// System
		// String lf=UIManager.getSystemLookAndFeelClassName();

		// windows
		// String lf="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

		// metal
		// String lf="javax.swing.plaf.metal.MetalLookAndFeel";
		/**
		 * common use try { UIManager.setLookAndFeel(lf); } catch(Exception ce)
		 * { JOptionPane.showMessageDialog(null,"无法设定外观感觉！"); }
		 **/

		// Java感觉
		JFrame.setDefaultLookAndFeelDecorated(true);

		JFrame frame = new JFrame("Tianen Searcher! yutianen@163.com");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		Container con = frame.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel jpup = new JPanel();
		jpup.setLayout(new GridLayout(2, 2));
		jtfa = new JTextField(30);
		jba = new JButton("选择索引的存放路径");
		jba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = fc.showOpenDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) {
					jtfa.setText(fc.getSelectedFile().getPath());
				}
			}
		});

		jtfb = new JTextField(30);
		JButton jbb = new JButton("搜索");
		jbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String indexPath = jtfa.getText();
					String phrase = jtfb.getText();
					LuceneSearcherTool.search(phrase, indexPath);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "搜索失败！", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		jpup.add(jtfa);
		jpup.add(jba);
		jpup.add(jtfb);
		jpup.add(jbb);

		jta = new JTextArea(10, 30);
		JScrollPane jsp = new JScrollPane(jta);

		JPanel jpdown = new JPanel();
		jpdown.setLayout(new FlowLayout());
		jtfc = new JTextField(35);
		jbd = new JButton("设定导出路径");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jbd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = fc.showOpenDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) {
					jtfc.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		jbe = new JButton("导出搜索结果");
		jbe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File f = new File(jtfc.getText());
					FileWriter fw = new FileWriter(f);
					PrintWriter pw = new PrintWriter(fw);
					pw.write(jta.getText());
					pw.flush();
					pw.close();
					JOptionPane.showMessageDialog(null, "写入文件成功！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(null, "写入文件失败！", "提示",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		jpdown.add(jtfc);
		jpdown.add(jbd);
		jpdown.add(jbe);

		con.add(jpup, BorderLayout.NORTH);
		con.add(jsp, BorderLayout.CENTER);
		con.add(jpdown, BorderLayout.SOUTH);

		frame.setSize(200, 100);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws ParseException {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LuceneSearcher().createAndShowGUI();
			}
		});
	}

	/**************************************************************************************************/
	// 使用内部类TianenSearcherTool来实现索引工作
	// 这样，就可以把索引建立的情况反映在文本框里面了
	static class LuceneSearcherTool {
		// 执行搜索 -- 搜索的关键词，索引的路径
		public static void search(String phrase, String indexPath)
				throws IOException, ParseException {
			// 建立搜索器
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
					.get(indexPath)));
			IndexSearcher searcher = new IndexSearcher(reader);

			// 搜索 text 字段
			// 生成Query对象
			QueryParser parser = new QueryParser("text", new SmartChineseAnalyzer());
			Query query = parser.parse(phrase);

			// 获得搜索结果对象
			TopDocs topDocs = searcher.search(query, 100);
			ScoreDoc[] docs = topDocs.scoreDocs;

			// 搜索到的结果数量
			int num = topDocs.totalHits;
			jta.setText("检索到的记录数量：" + num + "\n");
			jta.setText(jta.getText() + "***************************" + "\n\n");

			// 输出搜索结果
			for (int i = 0; i < num; i++) {
				// 获得Document
				Document doc = searcher.doc(docs[i].doc);
				if (doc == null) {
					continue;
				}

				// 获得filename
				String filename = doc.getField("filename").stringValue();

				// uri
				String uri = doc.getField("uri").stringValue();

				// cdate
				String cdate = doc.getField("cdate").stringValue();

				// digest
				String digest = doc.getField("digest").stringValue();

				StringBuffer sb = new StringBuffer();
				sb.append("URI：" + uri + "\n");
				sb.append("filename：" + filename + "\n");
				sb.append("cdate：" + cdate + "\n");
				sb.append("digest：" + digest + "\n");
				sb.append("------------------------------------" + "\n");

				jta.setText(jta.getText() + sb.toString());
			}

			// 关闭搜索器
		}
	}
}
