package com.yu.util.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 基于mysql模式下的sql生成和实体生成工具
 *
 * @author zengxm
 * @date 2015年7月6日
 *
 */
public class EntityUtil {

	private String table; // 限定性表名

	private String fileName; // 文件名

	private String author = "zengxm"; // 作者

	private String basePackage = ""; // 生成类的基础包名

	private String baseDir = ""; // 生成文件夹目录

	private Connection connection; // 数据源连接

	private String tableName; // 解析得来的表名

	private String tableComment; // 解析得来的表注释

	private List<String> colNames;// 列名数组

	private List<String> colTypes; // 列名类型数组

	private List<String> colComments; // 列注释

	private boolean hasDateColumn = false; // 含有时间类型

	public EntityUtil() {
		this.hasDateColumn = false;
	}

	public EntityUtil(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 初始化参数
	 * 
	 * @param table
	 *            - 限定性表名
	 * @param author
	 *            - 类文件作者
	 * @param basePackage
	 *            - 包名
	 * @param baseDir
	 *            - 基础目录
	 * @param connection
	 *            - 数据源的连接
	 */
	public EntityUtil(String table, String author, String basePackage,
			String baseDir, Connection connection) {
		this.table = table;
		this.author = author;
		this.basePackage = basePackage;
		this.baseDir = baseDir;
	}

	/**
	 * 初始化参数
	 * 
	 * @param table
	 *            - 表明
	 * @param author
	 *            - 作者
	 * @param basePackage
	 *            - 包名
	 * @param baseDir
	 *            - 文件路径
	 */
	public void createJavaFile(String table, String author, String basePackage,
			String baseDir) {
		this.table = table;
		this.author = author;
		this.basePackage = basePackage;
		this.baseDir = baseDir;
		this.hasDateColumn = false;
		execute();
	}

	/**
	 * 
	 * 
	 * @param table
	 *            - 表名
	 * @param fileName
	 *            - 文件名
	 * @param author
	 *            - 作者
	 * @param basePackage
	 *            - 包名
	 * @param baseDir
	 *            - 文件路径
	 */
	public void createJavaFile(String table, String fileName, String author,
			String basePackage, String baseDir) {
		this.table = table;
		this.fileName = fileName;
		this.author = author;
		this.basePackage = basePackage;
		this.baseDir = baseDir;
		this.hasDateColumn = false;

		execute();
	}

	/**
	 * 解析处理(生成实体类主体代码) 根据结果生成Dao 根据结果生成Service
	 */
	private void execute() {

		parseCreateTable();

		if (StringUtils.isBlank(fileName))
			fileName = upperFirstChar(convertToJavaColumn(tableName));

		createEntityFile(); // step-1

		createDaoFile(); // step-2

		createServiceFile(); // step-3

		createXMLTemplate(); // step-4
	}

	/**
	 * 查找建表语句并分析
	 * 
	 * @throws SQLException
	 */
	public void parseCreateTable() {
		String sql = "show create table " + table;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String createSQL = rs.getString(2);
				// 解析
				parse(createSQL);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析SQL
	 * 
	 * @param sql
	 *            - 按行分割的正常SQL建表语句
	 */
	public void parse(String sql) {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("解析SQL不能为空");
		}
		colNames = new ArrayList<String>(); // 列名数组

		colTypes = new ArrayList<String>(); // 列名类型数组

		colComments = new ArrayList<String>(); // 列注释

		// 解析SQL
		String[] lines = sql.split("\n");
		for (int i = 0, len = lines.length; i < len; i++) {
			// 每一行的每一列
			String line = lines[i].trim();
			String[] columns = line.split(" ");
			// 如果含有create的语句
			if (line.indexOf("CREATE") != -1) {
				// 含有建表语句, 倒数第二列为表名
				tableName = columns[columns.length - 2].replaceAll("`", "");
			} else if (line.indexOf("ENGINE") != -1) {
				// 含有引擎的行, 为表注释
				int pos = 0;
				if ((pos = line.indexOf("COMMENT=")) > 0) {
					tableComment = line.substring(pos + "COMMENT=".length())
							.replaceAll("'", "");
				}
			} else if (line.indexOf("KEY") != -1) {

			} else {
				for (int j = 0; j < columns.length; j++) {
					String column = columns[j];
					// 第一列为字段名
					if (j == 0) {
						column = columns[0].replaceAll("`", "");
						column = convertToJavaColumn(column);
						colNames.add(column);
						continue;
					}
					// 第二列为字段类型
					if (j == 1) {
						if (column.indexOf("(") != -1) {
							column = column.substring(0, column.indexOf("("));
						}
						colTypes.add(mysqlType2JavaType(column));
						continue;
					}
					// 字段注释
					if (column.equals("COMMENT")) {
						// 去除逗号, 如果是最后一行则不用
						if (j != columns.length - 1) {
							column = columns[j + 1].substring(0,
									columns[j + 1].length() - 1).replaceAll(
									"'", "");
						} else {
							column = columns[j + 1];
						}
						colComments.add(column);
					}
				}// end for
			}// -->> end if-else
		}// -->> end for
	}

	// 生成entity
	public void createEntityFile() {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + basePackage + ".po" + ";\r\n")
				.append("\r\n")
				.append("import java.io.Serializable;\r\n")
				.append("import org.apache.commons.lang.builder.*;\r\n")
				.append(hasDateColumn ? "import java.util.*;\r\n\r\n" : "")
				.append("/**\r\n")
				.append(" * " + tableComment + "\r\n")
				.append(" *" + "\r\n")
				.append(" * @author " + author + "\r\n")
				.append(" * @date " + new Date() + "\r\n")
				.append(" *" + "\r\n")
				.append(" */" + "\r\n")
				.append("public class " + fileName + " implements Serializable"
						+ " {\r\n").append("\r\n");
		processAllEntityAttrs(sb);
		processAllEntityMethods(sb);
		sb.append("}");
		// 打印
		System.out.println(sb.toString());
		// 写入文件
		File fileDir = new File(baseDir + "/"
				+ basePackage.replaceAll("\\.", "/") + "/po");

		if (!fileDir.exists())
			fileDir.mkdirs();

		File javaFile = new File(fileDir, fileName + ".java");

		try (FileOutputStream fop = new FileOutputStream(javaFile, false);) {
			FileChannel fc = fop.getChannel();
			fc.write(ByteBuffer.wrap(sb.toString().getBytes()));
			fop.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 生成dao
	public void createDaoFile() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + basePackage + ".mapper" + ";\r\n")
				.append("\r\n")
				.append("import com.yu.common.mapper.BaseMapper;\r\n")
				.append("import " + basePackage + ".po." + fileName + ";\r\n")
				.append("/**\r\n")
				.append(" * " + tableComment + "\r\n")
				.append(" *" + "\r\n")
				.append(" * @author " + author + "\r\n")
				.append(" * @date " + new Date() + "\r\n")
				.append(" *" + "\r\n")
				.append(" */" + "\r\n")
				.append("public interface " + fileName
						+ "Mapper extends BaseMapper<" + fileName + ">" + " "
						+ " {\r\n").append("\r\n");
		sb.append("}");
		// 打印
		System.out.println(sb.toString());
		// 写入文件
		File fileDir = new File(baseDir + "/"
				+ basePackage.replaceAll("\\.", "/") + "/mapper");

		if (!fileDir.exists())
			fileDir.mkdirs();

		File javaFile = new File(fileDir, fileName + "Mapper.java");

		try (FileOutputStream fop = new FileOutputStream(javaFile, false);) {
			FileChannel fc = fop.getChannel();
			fc.write(ByteBuffer.wrap(sb.toString().getBytes()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 生成service
	public void createServiceFile() {

		StringBuffer sb = new StringBuffer();
		String service_suffix = ".service";
		String po_suffix = ".po.";
		String mapper_suffix = ".mapper.";
		sb.append("package " + basePackage + service_suffix + ";\r\n")
				.append("\r\n")
				.append("import com.yu.common.service.MyBatisBaseService;\r\n")
				.append("import " + basePackage + po_suffix + fileName
						+ ";\r\n")
				.append("import " + basePackage + mapper_suffix + fileName
						+ "Mapper" + ";\r\n")
				.append("/**\r\n")
				.append(" * " + tableComment + "\r\n")
				.append(" *" + "\r\n")
				.append(" * @author " + author + "\r\n")
				.append(" * @date " + new Date() + "\r\n")
				.append(" *" + "\r\n")
				.append(" */" + "\r\n")
				.append("public class " + fileName
						+ "Service extends MyBatisBaseService<" + fileName
						+ ", " + fileName + "Mapper>" + " " + " {\r\n")
				.append("\r\n");
		sb.append("}");
		// 打印
		System.out.println(sb.toString());
		// 写入文件
		File fileDir = new File(baseDir + "/"
				+ basePackage.replaceAll("\\.", "/") + "/service");

		if (!fileDir.exists())
			fileDir.mkdirs();

		File javaFile = new File(fileDir, fileName + "Service.java");

		try (FileOutputStream fop = new FileOutputStream(javaFile, false);) {
			FileChannel fc = fop.getChannel();
			fc.write(ByteBuffer.wrap(sb.toString().getBytes()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 生成xml
	public void createXMLTemplate() {
		String xml_dir = baseDir + "../resources/sql/";
		Configuration cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(xml_dir));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			// 创建根哈希表
			Map<String, String> root = new HashMap<>();
			root.put("mapper_namespace", basePackage + ".mapper." + fileName + "Mapper");
			root.put("mapper_class", basePackage + ".po." + fileName);
			root.put("mapper_table", tableName);
			root.put(
					"mapper_talbe_cols",
					"task_id, task_desc, task_type, start_time, end_time, state, create_time, create_operator, last_modify, last_operator, job_id");
			root.put(
					"mapper_po_cols",
					"${taskId}, '${taskDesc}', ${taskType}, '${startTime}', '${endTime}', ${state}, now(), '${createOperator}', now(), '${lastOperator}', ${jobId}");
			root.put("mapper_if_condition", "");
			Template temp = cfg.getTemplate("entity_template.ftl");
			Writer out = new OutputStreamWriter(new FileOutputStream(xml_dir
					+ fileName + ".xml", false));
			temp.process(root, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * mysql字段类型转换
	 * 
	 * @param sqlType
	 * @return javaType
	 */
	private String mysqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			// 统一用int
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			// 统一用int
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("int")) {
			// 统一用long
			return "Long";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")
				|| sqlType.equalsIgnoreCase("date")
				|| sqlType.equalsIgnoreCase("timestamp")) {
			// 加多一个标识
			hasDateColumn = true;
			return "Date";
		}

		else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		}
		return null;
	}

	/**
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllEntityAttrs(StringBuffer sb) {

		// 加入序列码
		sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");

		for (int i = 0; i < colNames.size(); i++) {
			sb.append(
					"\tprivate " + colTypes.get(i) + " " + colNames.get(i)
							+ ";").append(" //" + colComments.get(i))
					.append("\r\n");
		}
		sb.append("\r\n");
	}

	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllEntityMethods(StringBuffer sb) {
		for (int i = 0; i < colNames.size(); i++) {
			// public void set(xxx)
			sb.append(
					"\tpublic void set" + upperFirstChar(colNames.get(i)) + "("
							+ colTypes.get(i) + " " + colNames.get(i)
							+ ") {\r\n")
					.append("\t\tthis." + colNames.get(i) + " = "
							+ colNames.get(i) + ";\r\n").append("\t}\r\n")
					.append("\r\n");

			// public void getXXX()
			sb.append(
					"\tpublic " + colTypes.get(i) + " get"
							+ upperFirstChar(colNames.get(i)) + "() {\r\n")
					.append("\t\treturn " + colNames.get(i) + ";\r\n")
					.append("\t}\r\n\r\n");
		}
		sb.append(
				"\t@Override\r\n"
						+ "\tpublic String toString() {\r\n"
						+ "\t\treturn ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);\r\n"
						+ "\t}\r\n").append("\r\n");
	}

	/**
	 * 下划线分词型转换为词组驼峰分词
	 * 
	 * @param sqlColumn
	 * @return
	 */
	private static String convertToJavaColumn(String sqlColumn) {
		char[] ch = sqlColumn.toCharArray();
		char c = 'a';
		if (ch.length > 3) {
			for (int j = 0; j < ch.length; j++) {
				c = ch[j];
				if (c == '_') {
					// [a,z]
					if (ch[j + 1] >= 'a' && ch[j + 1] <= 'z') {
						// 转变为大写
						ch[j + 1] = (char) (ch[j + 1] - 32);
					}
				}
			}
		}
		String str = new String(ch).replaceAll("_", "");
		return str;
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String upperFirstChar(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		// 去除_
		return new String(ch).replaceAll("_", "");
	}

	// 执行样例, 请在另外的地方进行生成
	public static void main(String[] args) throws URISyntaxException {
		// 生成对应的工程src目录下, 根据自身具体情况而定, 以下是我的maven目录
		String mySrcDir = System.getProperty("user.dir")
				+ "\\src\\main\\java\\";
		EntityUtil util = new EntityUtil(ConnectionUtil.getConnection());
		util.createJavaFile("cms.task", "zengxm", "com.yu.entity", mySrcDir);
	}
}
