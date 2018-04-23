package com.seewo.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zxm on 2018/2/8.
 */
public class DefaultMessageStore implements MessageStore {

    private static final Logger log = LoggerFactory.getLogger(DefaultMessageStore.class);

    public DefaultMessageStore() {
    }

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void destroy() {

    }
}
