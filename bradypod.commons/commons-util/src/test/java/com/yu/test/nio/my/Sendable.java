package com.yu.test.nio.my;

import java.io.*;

/**
 * Method definitions used for preparing, sending, and release
 * content.
 *
 * @author Mark Reinhold
 * @author Brad R. Wetmore
 */
interface Sendable {

    void prepare() throws IOException;

    // Sends (some) content to the given channel.
    // Returns true if more bytes remain to be written.
    // Throws IllegalStateException if not prepared.
    //
    boolean send(ChannelIO cio) throws IOException;

    void release() throws IOException;
}
