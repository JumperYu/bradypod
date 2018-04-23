package com.seewo.store;

import java.io.File;

/**
 * Created by zxm on 2018/2/9.
 */
public class MessageStoreConfig {

    private String storePathCommitLog = "xxxx";

    private int mapedFileSizeCommitLog;

    public String getStorePathCommitLog() {
        return storePathCommitLog;
    }

    public void setStorePathCommitLog(String storePathCommitLog) {
        this.storePathCommitLog = storePathCommitLog;
    }

    public int getMapedFileSizeCommitLog() {
        return mapedFileSizeCommitLog;
    }

    public void setMapedFileSizeCommitLog(int mapedFileSizeCommitLog) {
        this.mapedFileSizeCommitLog = mapedFileSizeCommitLog;
    }
}
