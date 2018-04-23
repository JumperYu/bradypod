package com.seewo.store;

/**
 * Created by zxm on 2018/2/10.
 */
public class AppendMessageResult {

    // Where to start writing
    private long wroteOffset;
    // Write Bytes
    private int wroteBytes;
    // Message storage timestamp
    private long storeTimestamp;
    // Consume queue's offset(step by one)
    private long logicsOffset;

    private AppendMessageStatus appendMessageStatus;

    public AppendMessageResult(AppendMessageStatus appendMessageStatus, long wroteOffset, int wroteBytes, long storeTimestamp, long logicsOffset) {
        this.appendMessageStatus = appendMessageStatus;
        this.wroteOffset = wroteOffset;
        this.wroteBytes = wroteBytes;
        this.storeTimestamp = storeTimestamp;
        this.logicsOffset = logicsOffset;
    }

    public long getWroteOffset() {
        return wroteOffset;
    }

    public void setWroteOffset(long wroteOffset) {
        this.wroteOffset = wroteOffset;
    }

    public int getWroteBytes() {
        return wroteBytes;
    }

    public void setWroteBytes(int wroteBytes) {
        this.wroteBytes = wroteBytes;
    }

    public long getStoreTimestamp() {
        return storeTimestamp;
    }

    public void setStoreTimestamp(long storeTimestamp) {
        this.storeTimestamp = storeTimestamp;
    }

    public long getLogicsOffset() {
        return logicsOffset;
    }

    public void setLogicsOffset(long logicsOffset) {
        this.logicsOffset = logicsOffset;
    }

    public AppendMessageStatus getAppendMessageStatus() {
        return appendMessageStatus;
    }

    public void setAppendMessageStatus(AppendMessageStatus appendMessageStatus) {
        this.appendMessageStatus = appendMessageStatus;
    }
}
