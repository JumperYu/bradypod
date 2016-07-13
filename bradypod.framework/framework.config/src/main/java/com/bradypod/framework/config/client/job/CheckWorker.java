package com.bradypod.framework.config.client.job;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.apache.commons.codec.digest.DigestUtils;

import com.bradypod.framework.config.util.HttpUtil;

public class CheckWorker implements Callable<Void> {

	private String path;

	public CheckWorker(String path) {
		this.path = path;
	}

	public Void call() throws Exception {
		boolean isChanged = false;
		while (!isChanged) {
			isChanged = verify();
		}
		return null;
	};

	ThreadLocal<String> localCache = new ThreadLocal<>();

	private String loadDataFromLocal() {
		try {
			String localCacheData = localCache.get();
			if (localCacheData == null) {
				byte[] fileBytes = Files.readAllBytes(Paths.get(path));
				String localMd5 = DigestUtils.md5Hex(fileBytes);
				localCache.set(localMd5);
			}
		} catch (IOException e) {
			// TODO
		}
		return localCache.get();
	}

	static final String REMOTE_DATA_URL = "http://localhost/longPoll.do";

	// get remote md5 sign
	private String loadDataFromRemote() {
		String ret = HttpUtil.request(REMOTE_DATA_URL, "GET", null, null);
		return ret;
	}

	// verify
	private boolean verify() {
		String localData = loadDataFromLocal();
		String remoteData = loadDataFromRemote();
		if (localData.equals(remoteData)) {
			return false;
		} else {
			return true;
		} // --> end if-else
	}

}