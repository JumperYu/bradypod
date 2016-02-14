package com.yu.test.concurrent;

/**
 * 整个进程只剩下后台线城时候，进程结束
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年1月28日
 */
public class ThreadDeamon implements Runnable{
	
	@Override
	public void run() {
		while(true){
			// do nothing
		}
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new ThreadDeamon());
		thread.setDaemon(true);// 设置后为守护线城，级别最低
		thread.start();
	}
}
