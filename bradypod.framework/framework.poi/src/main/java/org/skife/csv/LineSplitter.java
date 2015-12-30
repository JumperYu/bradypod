package org.skife.csv;

import java.util.ArrayList;
import java.util.List;

public class LineSplitter {
	private boolean trim = false;
	private char[] quotes = Defaults.QUOTES;
	private char seperator = Defaults.SEPERATOR;
	private char escape = Defaults.ESCAPE_CHARACTER;

	public String[] split(String line) {
		FieldStreamGenerator gen = new FieldStreamGenerator(line, quotes, escape, seperator, trim);
		List<Object> all = new ArrayList<>();
		while (gen.hasNext())
			all.add(gen.next());
		return (String[]) all.toArray(new String[all.size()]);
	}

	/**
	 * Should whitespace be trimmed around fields?
	 * <p>
	 * Defualts to false
	 */
	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	/**
	 * Specify an array of chars that will be treated as quotes, ie, will be
	 * ignored and everything between them is one field. Default is ' and "
	 */
	public void setQuoteCharacters(char[] quotes) {
		this.quotes = quotes;
	}

	/**
	 * Specify the field seperator character, defaults to a comma
	 */
	void setSeperator(char seperator) {
		this.seperator = seperator;
	}

	/**
	 * Specify an escape character within a field, default is \
	 */
	void setEscapeCharacter(char escape) {
		this.escape = escape;
	}
}
