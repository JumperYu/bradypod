package com.bradypod.framework.csv;

class FieldMunger {

	private char escapeCharacter = Defaults.ESCAPE_CHARACTER;
	private char seperator = Defaults.SEPERATOR;
	private char[] quotes = Defaults.QUOTES;

	CharSequence munge(CharSequence field) {
		final StringBuffer buffer = new StringBuffer();
		char c = 0;
		for (int i = 0; i < field.length(); i++) {
			c = field.charAt(i);
			if (isQuote(c) || c == seperator) {
				buffer.append(escapeCharacter);
				buffer.append(c);
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	char getSeperator() {
		return seperator;
	}

	void setSeperator(char seperator) {
		this.seperator = seperator;
	}

	void setEscapeCharacter(char escapeCharacter) {
		this.escapeCharacter = escapeCharacter;
	}

	void setQuotes(char[] quotes) {
		this.quotes = quotes;
	}

	private boolean isQuote(char c) {
		for (int i = 0; i < quotes.length; i++) {
			if (quotes[i] == c)
				return true;
		}
		return false;
	}
}
