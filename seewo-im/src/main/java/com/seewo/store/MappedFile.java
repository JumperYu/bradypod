package com.seewo.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zxm on 2018/2/6.
 */
public class MappedFile {

    protected static final Logger log = LoggerFactory.getLogger(MappedFile.class);

    private String filePath;

    private int fileSize;

    private String fileName;

    private File file;

    private FileChannel fileChannel;

    private MappedByteBuffer mappedByteBuffer;

    private boolean firstCreateInQueue = false;

    // offset
    AtomicInteger wrotePosition = new AtomicInteger(0);
    AtomicInteger committedPosition = new AtomicInteger(0);
    AtomicInteger flushedPosition = new AtomicInteger(0);

    /**
     * Not Used
     */
    protected ByteBuffer writeBuffer = null;

    private long fileFromOffset;

    private volatile long storeTimestamp = 0;

    private final static int BLANK_MAGIC_CODE = 0xBBCCDDEE ^ 1880681586 + 8;

    public MappedFile() {
    }

    public MappedFile(final String fileName, final int fileSize) throws IOException {
        init(fileName, fileSize);
    }

    /**
     * 初始化映射文件，并且确保存储目录可生成
     *
     * @param fileName
     * @param fileSize
     * @throws IOException
     */
    private void init(final String fileName, final int fileSize) throws IOException {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.file = new File(fileName);
        this.fileFromOffset = Long.parseLong(this.file.getName());

        // 创建一个和文件大小相等的缓冲
//        this.writeBuffer = ByteBuffer.allocateDirect(fileSize);

//        final long address = ((DirectBuffer) byteBuffer).address();
//        Pointer pointer = new Pointer(address);
//        LibC.INSTANCE.mlock(pointer, new NativeLong(fileSize));

        boolean ok = false;

        // 确保存储目录存在
        ensureDirOK(this.file.getParent());

        try {
            this.fileChannel = new RandomAccessFile(this.file, "rw").getChannel();
            this.mappedByteBuffer = this.fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
            ok = true;
        } catch (FileNotFoundException e) {
            log.error("create file channel " + this.fileName + " Failed. ", e);
            throw e;
        } catch (IOException e) {
            log.error("map file " + this.fileName + " Failed. ", e);
            throw e;
        } finally {
            if (!ok && this.fileChannel != null) {
                this.fileChannel.close();
            }
        }
    }

    public static void ensureDirOK(final String dirName) {
        if (dirName != null) {
            File f = new File(dirName);
            if (!f.exists()) {
                boolean result = f.mkdirs();
                log.info(dirName + " mkdir " + (result ? "OK" : "Failed"));
            }
        }
    }

    public AppendMessageResult appendMessage(MessageInner messageInner) {

        int currentPos = this.wrotePosition.get();

        long wroteOffset = fileFromOffset + currentPos;

        int msgLength = messageInner.getBody().length;

        if (currentPos < this.fileSize) {

            int maxBlank = this.fileSize - currentPos;

            int wroteBytes = 4 + msgLength; // msg size + msg body
            // 临时大小的缓冲区
            ByteBuffer msgStoreItemMemory = ByteBuffer.allocate(wroteBytes);

            ByteBuffer byteBuffer = writeBuffer != null ? writeBuffer.slice() : this.mappedByteBuffer.slice();
            byteBuffer.position(currentPos);

            // 剩余不足的空间用特殊字节填充　
            if (wroteBytes > maxBlank) {
                // reset buffer
//                msgStoreItemMemory.flip();
//                msgStoreItemMemory.limit(maxBlank);

//                // 1 TOTALSIZE
//                msgStoreItemMemory.putInt(maxBlank);
//                // 2 MAGICCODE
//                msgStoreItemMemoy.putInt(BLANK_MAGIC_CODE)


                byte[] blank = new byte[maxBlank];
                Arrays.fill(blank, (byte) 0);

                byteBuffer.put(blank, 0, maxBlank);

                this.wrotePosition.addAndGet(maxBlank);

                return new AppendMessageResult(AppendMessageStatus.END_OF_FILE, wroteOffset, maxBlank, System.currentTimeMillis(), 0);
            } // --> end if END_OF_FILE

            // 1 BODY SIZE
            msgStoreItemMemory.putInt(msgLength);
            // 2 BODY BYTES
            msgStoreItemMemory.put(messageInner.getBody());

            byteBuffer.put(msgStoreItemMemory.array());

            this.wrotePosition.addAndGet(wroteBytes);

            return new AppendMessageResult(AppendMessageStatus.PUT_OK, wroteOffset, wroteBytes, System.currentTimeMillis(), 0);

        }

        return null;
    }

    /**
     * 顺序写入
     */
    public boolean appendMessage(byte[] data) {
        int currentPos = this.wrotePosition.get();

        if (currentPos + data.length <= this.fileSize) {
            try {
                fileChannel.position(currentPos);
                fileChannel.write(ByteBuffer.wrap(data));
            } catch (Throwable e) {
                log.error("Error occurred when append message to mappedFile.", e);
            }
            this.wrotePosition.addAndGet(data.length);
            return true;
        }

        return false;
    }

    private void resetByteBuffer(final ByteBuffer byteBuffer, final int limit) {
        byteBuffer.flip();
        byteBuffer.limit(limit);
    }

    public SelectMappedBufferResult selectMappedBuffer(int pos) {
        int readPosition = getReadPosition();
        if (pos < readPosition && pos >= 0) {
            if (this.hold()) {
                ByteBuffer byteBuffer = this.mappedByteBuffer.slice();
                byteBuffer.position(pos);
                int size = readPosition - pos;
                ByteBuffer byteBufferNew = byteBuffer.slice();
                byteBufferNew.limit(size);
                return new SelectMappedBufferResult(this.fileFromOffset + pos, byteBufferNew, size, this);
            }
        }

        return null;
    }

    public SelectMappedBufferResult selectMappedBuffer(int pos, int size) {
        int readPosition = getReadPosition();
        if ((pos + size) <= readPosition) {
            if (this.hold()) {
                ByteBuffer byteBuffer = this.mappedByteBuffer.slice();
                byteBuffer.position(pos);
                ByteBuffer byteBufferNew = byteBuffer.slice();
                byteBufferNew.limit(size);
                return new SelectMappedBufferResult(this.fileFromOffset + pos, byteBufferNew, size, this);
            } else {
                log.warn("matched, but hold failed, request pos: " + pos + ", fileFromOffset: "
                        + this.fileFromOffset);
            }
        } else {
            log.warn("selectMappedBuffer request pos invalid, request pos: " + pos + ", size: " + size
                    + ", fileFromOffset: " + this.fileFromOffset);
        }

        return null;
    }

    public static void clean(final ByteBuffer buffer) {
        if (buffer == null || !buffer.isDirect() || buffer.capacity() == 0)
            return;
        invoke(invoke(viewed(buffer), "cleaner"), "clean");
    }

    public boolean destroy() {

        clean(this.mappedByteBuffer);

        try {
            this.fileChannel.close();
            log.info("close file channel " + this.fileName + " OK");

            long beginTime = System.currentTimeMillis();
            boolean result = this.file.delete();
            log.info("delete file[REF:" + 0 + "] " + this.fileName
                    + (result ? " OK, " : " Failed, ") + "W:" + this.getWrotePosition() + " M:"
                    + this.getFlushedPosition() + ", "
                    + UtilAll.computeEclipseTimeMilliseconds(beginTime));
        } catch (Exception e) {
            log.warn("close file channel " + this.fileName + " Failed. ", e);
        }

        return true;
    }

    /**
     * TODO : 需要修改
     */
    public int getReadPosition() {
        return this.writeBuffer == null ? this.wrotePosition.get() : this.committedPosition.get();
    }

    public int getWrotePosition() {
        return this.wrotePosition.get();
    }

    public int getFlushedPosition() {
        return this.flushedPosition.get();
    }

    public boolean hold() {
        return true;
    }

    public boolean isFull() {
        return this.wrotePosition.get() == this.fileSize;
    }

    public int flush(final int flushLeastPages) {
        int value = getReadPosition();

        try {
            //We only append data to fileChannel or mappedByteBuffer, never both.
            if (writeBuffer != null || this.fileChannel.position() != 0) {
                this.fileChannel.force(false);
            } else {
                this.mappedByteBuffer.force();
            }
        } catch (Throwable e) {
            log.error("Error occurred when force data to disk.", e);
        }

        this.flushedPosition.set(value);
//        this.release();

        return this.getFlushedPosition();
    }

    public boolean isAvailable() {
        return true;
    }

    public ByteBuffer sliceByteBuffer() {
        return this.mappedByteBuffer.slice();
    }

    // --> MappedByteBuffer Clean UP

    private static Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = method(target, methodName, args);
                    method.setAccessible(true);
                    return method.invoke(target);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    private static Method method(Object target, String methodName, Class<?>[] args)
            throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }

    private static ByteBuffer viewed(ByteBuffer buffer) {
        String methodName = "viewedBuffer";

        Method[] methods = buffer.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("attachment")) {
                methodName = "attachment";
                break;
            }
        }

        ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
        if (viewedBuffer == null)
            return buffer;
        else
            return viewed(viewedBuffer);
    }

    // --> End of MappedByteBuffer Clean UP

    public static Logger getLog() {
        return log;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileFromOffset() {
        return fileFromOffset;
    }

    public void setFileFromOffset(long fileFromOffset) {
        this.fileFromOffset = fileFromOffset;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileChannel getFileChannel() {
        return fileChannel;
    }

    public void setFileChannel(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public MappedByteBuffer getMappedByteBuffer() {
        return mappedByteBuffer;
    }

    public void setMappedByteBuffer(MappedByteBuffer mappedByteBuffer) {
        this.mappedByteBuffer = mappedByteBuffer;
    }

    public long getStoreTimestamp() {
        return storeTimestamp;
    }

    public void setStoreTimestamp(long storeTimestamp) {
        this.storeTimestamp = storeTimestamp;
    }

    public boolean isFirstCreateInQueue() {
        return firstCreateInQueue;
    }

    public void setFirstCreateInQueue(boolean firstCreateInQueue) {
        this.firstCreateInQueue = firstCreateInQueue;
    }

    public void setWrotePosition(int wrotePosition) {
        this.wrotePosition.set(wrotePosition);
    }

    public void setFlushedPosition(int flushedPosition) {
        this.flushedPosition.set(flushedPosition);
    }

    public void setWriteBuffer(ByteBuffer writeBuffer) {
        this.writeBuffer = writeBuffer;
    }

    public void setCommittedPosition(int committedPosition) {
        this.committedPosition.set(committedPosition);
    }
}
