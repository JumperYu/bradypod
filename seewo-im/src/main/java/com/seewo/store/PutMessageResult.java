package com.seewo.store;

/**
 * Created by zxm on 2018/2/9.
 */
public class PutMessageResult {

    private long offset;

    private int size;   // 消息

    public PutMessageResult(long offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
