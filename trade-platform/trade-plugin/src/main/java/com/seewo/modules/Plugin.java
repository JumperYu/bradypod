package com.seewo.modules;

public interface Plugin {
	/**  ��������ķ���  */
	public void start();
	/**  ֹͣ������еķ���  */
	public void stop();
	/**  ���ٲ���ķ���   */
	public void destroy();
}