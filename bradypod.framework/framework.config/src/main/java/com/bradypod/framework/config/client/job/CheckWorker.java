package com.bradypod.framework.config.client.job;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;

import com.taobao.diamond.md5.MD5;

public class CheckWorker {

	private String path;

	public CheckWorker(String path) {
		this.path = path;
	}

	ThreadLocal<byte[]> localCache = new ThreadLocal<>();

	private byte[] loadDataFromLocal() {
		byte[] localCacheData = null;
		try {
			localCacheData = localCache.get();
			if (localCacheData == null) {
				localCacheData = Files.readAllBytes(Paths.get(path));
				localCache.set(localCacheData);
			}
		} catch (IOException e) {
			// TODO
		}
		return localCacheData;
	}
	
	static final String REMOTE_DATA_URL = "http://localhost/longPoll.do";
	
	private byte[] loadDataFromRemote() {
		return null;
	}

	boolean verify(byte[] localData, byte[] remoteData) {
		String localMd5 = DigestUtils.md5Hex(localData);
		String remoteMd5 = DigestUtils.md5Hex(remoteData);
		if (localMd5.equals(remoteMd5)) {
			return false;
		} else {
			return true;
		}
	}

}