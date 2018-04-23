package com.seewo.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * Created by zxm on 2018/2/18.
 */
public class ConsumeQueue {

    private static final Logger log = LoggerFactory.getLogger(ConsumeQueue.class);

    private static final int CQ_STORE_UNIT_SIZE = 20;

    private MappedFileQueue mappedFileQueue;

    private ByteBuffer byteBuffer;

    private String topic;
    private int queueId;
    private String storePath;
    private int mappedFileSize;

    public ConsumeQueue(String topic, int queueId, String storePath, int mappedFileSize) {
        this.topic = topic;
        this.queueId = queueId;
        this.storePath = storePath;
        this.mappedFileSize = mappedFileSize;

        this.byteBuffer = ByteBuffer.allocate(CQ_STORE_UNIT_SIZE);

        String queueDir = this.storePath
                + File.separator + topic
                + File.separator + queueId;

        this.mappedFileQueue = new MappedFileQueue(queueDir, mappedFileSize);
    }

    public boolean load() {
        boolean result = this.mappedFileQueue.load();
        log.info("load consume queue " + this.topic + "-" + this.queueId + " " + (result ? "OK" : "Failed"));

        return result;
    }

    public boolean putMessagePositionInfo(final long offset, final int size, final long tagsCode) {

        this.byteBuffer.flip();
        this.byteBuffer.limit(CQ_STORE_UNIT_SIZE);
        this.byteBuffer.putLong(offset); // 8
        this.byteBuffer.putInt(size); // 4
        this.byteBuffer.putLong(tagsCode); // 8

        MappedFile mappedFile = this.mappedFileQueue.getLastMappedFile(0);

        if (mappedFile != null) {
            return mappedFile.appendMessage(this.byteBuffer.array());
        }

        return false;
    }


    public long getLastOffset() {
        long lastOffset = -1;

        int logicFileSize = this.mappedFileSize;

        MappedFile mappedFile = this.mappedFileQueue.getLastMappedFile();
        if (mappedFile != null) {

            int position = mappedFile.getWrotePosition() - CQ_STORE_UNIT_SIZE;
            if (position < 0)
                position = 0;

            ByteBuffer byteBuffer = mappedFile.sliceByteBuffer();
            byteBuffer.position(position);
            for (int i = 0; i < logicFileSize; i += CQ_STORE_UNIT_SIZE) {
                long offset = byteBuffer.getLong();
                int size = byteBuffer.getInt();
                byteBuffer.getLong();

                if (offset >= 0 && size > 0) {
                    lastOffset = offset + size;
                } else {
                    break;
                }
            }
        }

        return lastOffset;
    }

}
