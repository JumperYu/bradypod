package com.bradypod.util.sys;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 获取一些jvm的信息
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午5:08:10
 */
public class JvmUtil {

	static RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();

	/**
	 * 获取进程 pid@hostname
	 */
	public static String getJvmInfo() {
		JavaProcess javaProcess = new JavaProcess();
		String name = runtime.getName();
		try {
			String[] vals = name.split("@"); // pid@hostname
			javaProcess.setPid(Integer.parseInt(vals[0]));
			javaProcess.setHostName(vals[1]);
		} catch (Exception e) {
			// ignore
		}
		return javaProcess.toString();
	}
}
