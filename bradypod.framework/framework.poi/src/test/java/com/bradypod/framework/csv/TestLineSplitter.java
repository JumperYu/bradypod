package com.bradypod.framework.csv;

import org.skife.csv.LineSplitter;

public class TestLineSplitter {
	private LineSplitter splitter;

	public void setUp() throws Exception {
		splitter = new LineSplitter();
		splitter.setTrim(true);
	}

	public void testGetFirstField() throws Exception {
		String line = "a, 2, E";
		splitter.split(line);
	}

	public void testGetMiddleField() throws Exception {
		String line = "a, 2, E";
		splitter.split(line);
	}

	public void testGetLastField() throws Exception {
		String line = "a, 2, E";
		splitter.split(line);
	}

	public void testSimpleQuotes() throws Exception {
		String line = "'a', 2, E";
		splitter.setQuoteCharacters(new char[] { '\'' });
		splitter.split(line);
	}

	public void testCommaInQuote() throws Exception {
		String line = "'a,b', 2, E";
		splitter.setQuoteCharacters(new char[] { '\'' });
		splitter.split(line);
	}

	public void testQuoteInMiddle() throws Exception {
		String line = "a','b, 2, E";
		splitter.setQuoteCharacters(new char[] { '\'' });
		splitter.split(line);
	}

	public void testEscapeCharacter() throws Exception {
		String line = "a\\,b, 2, E";
		splitter.setQuoteCharacters(new char[] { '\'' });
		splitter.split(line);
	}

	public void testEscapeQuotes() throws Exception {
		String line = "a\\',\\'b, 2, E";
		splitter.setQuoteCharacters(new char[] { '\'' });
		splitter.split(line);
	}
}
