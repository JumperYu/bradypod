package com.bradypod.server.test;

import java.util.Stack;

import org.junit.Test;

public class TestStack {

	Stack<String> stack = new Stack<>();

	@Test
	public void testStack() {
		stack.push("str-1");
		stack.push("str-2");
		stack.push("str-3");
		stack.push("str-4");
		while (!stack.isEmpty()) {
			// 看栈顶,但不出栈
			// stack.peek();
			System.out.println(stack.pop()); // 出栈
		}
	}

}
