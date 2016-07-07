package com.bradypod.framework.config.job;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 1.start this job 2.scan 30 secs until it change 3.stop and return is changed
 * 
 * @author xiangmin.zxm
 *
 */
public class CheckingFileJob implements Callable<Boolean> {

	private boolean isBroken = false; // is interrupted

	private boolean isChanged = false; // is changed

	private Path path; // file path

	static ThreadLocal<String> holder = new ThreadLocal<>(); // thread context

	static final int DEFAULT_JOB_SECONDS = 30000;

	public CheckingFileJob(Path path) {
		this.path = path;
	}

	final Timer timer = new Timer(false);

	@Override
	public Boolean call() {

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				setBroken(true);
			}
		}, DEFAULT_JOB_SECONDS);

		while (!isBroken && !isChanged) {
			isChanged = scanForChange();
		} // --> end while is broken or changed

		timer.cancel();

		return isChanged;
	}

	/**
	 * @return - if change return true
	 */
	public boolean scanForChange() {
		try {
			byte[] bytes = Files.readAllBytes(path);
			String md5Hex = DigestUtils.md5Hex(bytes);
			if (holder.get() == null) {
				holder.set(md5Hex);
				return false;
			}
			if (holder.get().equals(md5Hex)) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// set
	public void setBroken(boolean isBroken) {
		this.isBroken = isBroken;
	}

}
