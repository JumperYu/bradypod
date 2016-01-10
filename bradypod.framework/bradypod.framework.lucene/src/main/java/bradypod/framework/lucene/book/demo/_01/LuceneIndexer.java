//LuceneIndexer.java
package bradypod.framework.lucene.book.demo._01;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import bradypod.framework.lucene.book.demo.tool.FileList;
import bradypod.framework.lucene.book.demo.tool.FileText;

public class LuceneIndexer {
	private JTextField jtfa;
	private JButton jba;
	private JTextField jtfb;
	private JButton jbc;
	private static JTextArea jta;

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

		JFrame frame = new JFrame("Tianen Indexer! yutianen@163.com");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		Container con = frame.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel jpup = new JPanel();
		jpup.setLayout(new GridLayout(3, 2));
		jtfa = new JTextField(30);
		jba = new JButton("选择被索引的文件存放路径");
		jba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = fc.showOpenDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) {
					jtfa.setText(fc.getSelectedFile().getPath());
					jbc.setEnabled(true);
				}
			}
		});
		jtfb = new JTextField(30);
		JButton jbb = new JButton("选择索引的存放路径");
		jbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = fc.showOpenDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) {
					jtfb.setText(fc.getSelectedFile().getPath());
					jbc.setEnabled(true);
				}
			}
		});
		JLabel jl = new JLabel("");
		jbc = new JButton("建立索引");
		jbc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					LuceneIndexerTool.index(jtfa.getText(), jtfb.getText());
					// jbc.setEnabled(false);
				} catch (Exception ee) {
					ee.printStackTrace();
					jbc.setEnabled(true);
					JOptionPane.showMessageDialog(null, "索引创建失败！");
					System.out.println(ee.getMessage());
				}
			}
		});
		jpup.add(jtfa);
		jpup.add(jba);
		jpup.add(jtfb);
		jpup.add(jbb);
		jpup.add(jl);
		jpup.add(jbc);

		jta = new JTextArea(10, 60);
		JScrollPane jsp = new JScrollPane(jta);

		con.add(jpup, BorderLayout.NORTH);
		con.add(jsp, BorderLayout.CENTER);

		frame.setSize(200, 100);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new LuceneIndexer().createAndShowGUI();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "程序加载失败！");
				}
			}
		});

	}

	// 使用内部类LuceneIndexerTool来实现索引工作
	// 这样，就可以把索引建立的情况反映在文本框里面了
	static class LuceneIndexerTool {
		// 创建索引 -- 被索引的文件的路径，索引的路径
		public static void index(String filesPath, String indexPath)
				throws IOException {
			// 建立索引器
			IndexWriter writer = new IndexWriter(FSDirectory.open(Paths
					.get(indexPath)), new IndexWriterConfig(
					new SmartChineseAnalyzer()));

			// 递归遍历文件目录来建立索引
			String s[] = FileList.getFiles(filesPath);
			int len = s.length;
			for (int i = 0; i < len; i++) {
				File f = new File(s[i]);
				String ext = getExt(f);
				if (ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("html")) {
					Document doc = new Document();

					// filename
					String filename = f.getName();
					Field field = new StringField("filename", filename,
							Field.Store.YES);
					doc.add(field);

					// uri local
					String uri = f.getPath();
					field = new StringField("uri", uri, Field.Store.YES);
					doc.add(field);

					// cdate
					Date dt = new Date(f.lastModified());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E ");
					String cdate = sdf.format(dt);
					field = new StringField("cdate", cdate, Field.Store.YES);
					doc.add(field);

					// size
					double si = f.length();
					String size = "";

					if (si > 1024) {
						size = String.valueOf(Math.floor(si / 1024)) + " K";
					} else {
						size = String.valueOf(si) + " Bytes";
					}
					field = new StringField("size", size, Field.Store.YES);
					doc.add(field);

					// text
					String text = FileText.getText(f);
					field = new TextField("text", text, Field.Store.YES);
					doc.add(field);

					// digest
					String digest = "";
					if (text.length() > 200) {
						digest = text.substring(0, 200);
					} else {
						digest = text;
					}

					field = new TextField("digest", digest, Field.Store.YES);
					doc.add(field);

					writer.addDocument(doc);

					jta.setText(jta.getText() + "已经归入索引： " + f + "\n");
				}
			}

			// 关闭索引器
			writer.close();

			JOptionPane.showMessageDialog(null, "索引建立完毕", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}

		public static String getExt(File f) {
			String s = f.getName();
			try {
				s = s.substring(s.lastIndexOf(".") + 1);
			} catch (Exception e) {
				s = "";
			}
			return s;
		}
	}
}
