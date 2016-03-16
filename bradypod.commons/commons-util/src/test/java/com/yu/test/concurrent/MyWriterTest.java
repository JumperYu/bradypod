package com.yu.test.concurrent;

import org.junit.Test;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;

public class MyWriterTest {
	private MyWriter writer;

	@Test
	public void testThreading() {
		AnnotatedTestRunner runner = new AnnotatedTestRunner();
		// Run all Weaver tests in this class, using MyList as the Class Under
		// Test.
		runner.runTests(this.getClass(), MyWriter.class);
	}

	@ThreadedBefore
	public void before() {
		writer = new MyWriter();
	}

	@ThreadedMain
	public void mainThread() {
		writer.write("MainA");
		writer.write("MainB");
	}

	@ThreadedSecondary
	public void secondThread() {
		writer.write("SecondA");
		writer.write("SecondB");
	}

	@ThreadedAfter
	public void after() {
		System.out.print(writer.toString());
	}
}
