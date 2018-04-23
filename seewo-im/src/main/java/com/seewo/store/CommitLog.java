package com.seewo.store;

import java.nio.ByteBuffer;

/**
 * Created by zxm on 2018/2/9.
 */
public class CommitLog {

    private MappedFileQueue mappedFileQueue;

    private MessageStoreConfig messageStoreConfig;

    public CommitLog(MessageStoreConfig messageStoreConfig) {
        this.messageStoreConfig = messageStoreConfig;
        this.mappedFileQueue = new MappedFileQueue(messageStoreConfig.getStorePathCommitLog(),
                messageStoreConfig.getMapedFileSizeCommitLog());
    }

    public void load() {
        this.mappedFileQueue.load();
    }

    public PutMessageResult putMessage(final MessageInner msg) {

        MappedFile mappedFile = this.mappedFileQueue.getLastMappedFile();

        if (null == mappedFile || mappedFile.isFull()) {
            mappedFile = this.mappedFileQueue.getLastMappedFile(0); // Mark: NewFile may be cause noise
        }

        AppendMessageResult appendMessageResult = mappedFile.appendMessage(msg);

        switch (appendMessageResult.getAppendMessageStatus()) {
            case END_OF_FILE:
                mappedFile = this.mappedFileQueue.getLastMappedFile(0);
                appendMessageResult = mappedFile.appendMessage(msg);
                break;
            case PUT_OK:
                break;
            default:
                return null;
        }

        return new PutMessageResult(appendMessageResult.getWroteOffset(), appendMessageResult.getWroteBytes());
    }

    public MessageInner getMessage(long offset, int size) {
        int mappedFileSize = this.messageStoreConfig.getMapedFileSizeCommitLog();

        MappedFile mappedFile = this.mappedFileQueue.findMappedFileByOffset(offset, offset == 0);

        if (mappedFile != null) {
            int pos = (int) (offset % mappedFileSize);
            SelectMappedBufferResult selectMappedBufferResult = mappedFile.selectMappedBuffer(pos, size);

            ByteBuffer readedBuf = selectMappedBufferResult.getByteBuffer();
            int msgLen = readedBuf.getInt();
            byte[] buf = new byte[msgLen];

            readedBuf.get(buf);

            MessageInner messageInner = new MessageInner();
            messageInner.setBody(buf);
            return messageInner;
        }

        return null;
    }
}
