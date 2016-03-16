package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Handler {

	public void handle(SelectionKey sk) throws IOException;

}
