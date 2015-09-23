package com.yu.test.ognl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ognl.Ognl;
import ognl.OgnlException;

public class TestOgnl {

	@Test
	public void testAll(String[] args) throws OgnlException {
		TestUser root2 = new TestUser();
		root2.setId(19612);
		root2.setName("sakura");
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("who", "Who am i?");
		try {
			String who1 = (String) Ognl.getValue("#who", context, root2);
			String who2 = (String) Ognl.getValue("#context.who", context, root2);
			Object whoExp = Ognl.parseExpression("#who");
			String who3 = (String) Ognl.getValue(whoExp, context, root2);
			System.out.println(who1);
			System.out.println(who2);
			System.out.println(who3);
			System.out.println(whoExp);
			// who1 who2 who3 返回同样的值， whoExp 重复使用可以提高效率
			String name1 = (String) Ognl.getValue("name", root2);
			String name2 = (String) Ognl.getValue("#root.name", root2);
			System.out.println(name1);
			System.out.println(name2);
			// name1 name2 返回同样的值
		} catch (OgnlException e) {
			// error handling
			e.printStackTrace();
		}
	}
}

class TestUser {
	String name;
	int id;

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
