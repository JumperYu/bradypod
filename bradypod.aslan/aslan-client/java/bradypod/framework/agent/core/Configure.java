package bradypod.framework.agent.core;

/**
 * 配置类
 *
 * @author oldmanpushcart@gmail.com
 */
public class Configure {

	private String targetIp; // 目标主机IP
	private int targetPort; // 目标进程号
	private int javaPid; // 对方java进程号
	private int connectTimeout = 6000; // 连接超时时间(ms)
	private String greysCore;
	private String greysAgent;

	public String getTargetIp() {
		return targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	public int getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}

	public int getJavaPid() {
		return javaPid;
	}

	public void setJavaPid(int javaPid) {
		this.javaPid = javaPid;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public String getGreysAgent() {
		return greysAgent;
	}

	public void setGreysAgent(String greysAgent) {
		this.greysAgent = greysAgent;
	}

	public String getGreysCore() {
		return greysCore;
	}

	public void setGreysCore(String greysCore) {
		this.greysCore = greysCore;
	}

	/**
	 * 转换为对象
	 */
	public static Configure toConfigure(String agnetArgs) {
		Configure configure = new Configure();
		configure.setTargetIp("127.0.0.1");
		configure.setTargetPort(4321);
		configure.setConnectTimeout(5000);
		return configure;
	}

}
