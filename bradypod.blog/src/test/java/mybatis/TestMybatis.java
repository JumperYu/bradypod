package mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import com.yu.user.mapper.AccountMapper;
import com.yu.user.po.Account;

public class TestMybatis {

	private SqlSession session;

	@Before
	public void init() throws IOException {
		String resource = "config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		session = sqlSessionFactory.openSession();
	}

	@After
	public void close() {
		session.close();
	}

	@Test
	public void testSessionFactoryFromPO() throws IOException {
/*		BlogMapper mapper = session.getMapper(BlogMapper.class);
		Blog blog = mapper.selectBlogFromId(1);
		System.out.println(blog);*/
		BlogMapper mapper = session.getMapper(BlogMapper.class);
		Blog blog = new Blog();
		blog.setId(123);
		blog.setContent("123");
		int ret = mapper.fuckItHard(blog);
		System.out.println(ret);
	}

	@Test
	public void testSessionFactoryFromXML() throws IOException {
		BlogMapper mapper = session.getMapper(BlogMapper.class);
		Blog blog = new Blog();
		blog.setId(123);
		blog.setContent("123");
		mapper.fuckItHard(blog);
//		Blog blog = mapper.selectBlogFromContent("哈哈");
//		System.out.println(blog);
		
	}

	@Test
	public void testAccout() {
		AccountMapper mapper = session.getMapper(AccountMapper.class);
		System.out.println(mapper.queryAccountByPassport("zxm"));
	}

	@Test
	public void testAccountByPackage() {
		Account account = (Account) session.selectOne(
				"com.yu.user.mapper.AccountMapper.queryAccountByPassport",
				"haha");
		System.out.println(account);
	}

	@Test
	public void testMoreParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("index", "0");
		params.put("rows", "10");
		List<Account> accounts = session.selectList(
				"com.yu.user.mapper.AccountMapper.queryAll", params);
		System.out.println(accounts);
	}

	// 测试数据类型
	@Test
	public void testBlobType() throws FileNotFoundException, IOException {
		DataMapper dataMapper = session.getMapper(DataMapper.class);

		byte[] out = FileCopyUtils.copyToByteArray(new FileInputStream(
				"e://131.png"));
		Data newData = new Data();
		newData.setData(out);
		int ret = dataMapper.insert(newData);
		System.out.println("--- result:" + ret);

		Data data = dataMapper.selectData(13); 
		byte[] arry = data.getData();
		FileCopyUtils.copy(arry, new File("E://test.png"));
	}
}
