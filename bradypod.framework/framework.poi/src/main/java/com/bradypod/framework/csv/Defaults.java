package com.bradypod.framework.csv;

import org.skife.csv.CSVReader;

interface Defaults {

	char COMMA = ',';
	char TAB = '\t';
	char SINGLE_QUOTE = '\'';
	char DOUBLE_QUOTE = '"';
	char BACKSLASH = '\\';

	char[] QUOTES = new char[] { SINGLE_QUOTE, DOUBLE_QUOTE };
	char[] LINE_SEPERATOR = System.getProperty("line.separator").toCharArray();
	char SEPERATOR = CSVReader.COMMA;
	char ESCAPE_CHARACTER = CSVReader.BACKSLASH;
}
