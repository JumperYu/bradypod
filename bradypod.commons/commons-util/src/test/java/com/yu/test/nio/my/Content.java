package com.yu.test.nio.my;

/**
 * An Sendable interface extension that adds additional
 * methods for additional information, such as Files
 * or Strings.
 *
 * @author Mark Reinhold
 * @author Brad R. Wetmore
 */
interface Content extends Sendable {

    String type();

    // Returns -1 until prepare() invoked
    long length();

}
