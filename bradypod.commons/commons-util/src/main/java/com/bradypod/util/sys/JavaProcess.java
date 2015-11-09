package com.bradypod.util.sys;

/**
 * 包装进程信息
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午5:16:42
 */
public class JavaProcess {

	private int pid; // 进程端口
	private String hostName; // 主机名
	private String hostIp; // 主机ip

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	@Override
	public String toString() {
		return "JavaProcess [pid=" + pid + ", hostName=" + hostName + ", hostIp=" + hostIp + "]";
	}

}
