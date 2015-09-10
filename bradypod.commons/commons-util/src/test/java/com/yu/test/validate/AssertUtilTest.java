package com.yu.test.validate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.mutable.MutableInt;
import org.junit.Test;

import com.yu.util.validate.AssertUtil;

public class AssertUtilTest {

	@Test
	public void testAssertNotEmpty() {
		List<MutableInt> list = new ArrayList<MutableInt>();
		AssertUtil.notEmpty(list);
	}

	@Test
	public void testAssertHashLength() {
		String emptyString = "   ";
		AssertUtil.hasLength(emptyString);
	}

	@Test
	public void testAssertHashText() {
		String emptyString = "   ";
		AssertUtil.hasText(emptyString);
	}

	@Test
	public void testAssertNotNull() {
		Object[] object = new Object[] {};
		AssertUtil.notEmpty(object, "数组为空");
	}
}
