package com.yu.test.nio.my;

/**
 * Exception class used when a request can't be properly parsed.
 *
 * @author Mark Reinhold
 * @author Brad R. Wetmore
 */
class MalformedRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	MalformedRequestException() { }

    MalformedRequestException(String msg) {
        super(msg);
    }

    MalformedRequestException(Exception x) {
        super(x);
    }
}
