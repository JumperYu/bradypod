package com.seewo.demo;

import java.util.LinkedHashMap;
import java.util.Map;

public class WMZServer {
	private static WMZServer instance;
	private Map<Class<?>, Module> modules; // ����ڷ������ĸ��ָ�������ģ��
	private ClassLoader loader; // �������

	public static WMZServer getInstance() {
		return instance;
	}

	public WMZServer() {
		if (instance != null) {
			throw new IllegalStateException("�������Ѿ�����");
		}
		instance = this;
		init(); // ��ʼ��������
	}

	private void init() {
		modules = new LinkedHashMap<Class<?>, Module>();
		loader = this.getClass().getClassLoader();
		loadModules(); // ���ظ���ģ��
		startModules(); // ��������ģ��ķ���
	}

	private void loadModules() {
		loadModule(PluginManagerModule.class.getName()); // ���ز��ģ��
		loadModule(MenuModule.class.getName()); // �˵�ģ��
	}

	/**
	 * ��������ģ��ķ���
	 */
	private void startModules() {
		for (Module module : modules.values()) {
			boolean started = false;
			try {
				module.start();
			} catch (Exception e) {
				if (started && module != null) {
					module.stop();
					module.destroy();
				}
			}
		}
	}

	private void loadModule(String module) {
		try {
			Class<?> modClass = loader.loadClass(module);
			Module mod = (Module) modClass.newInstance();
			this.modules.put(modClass, mod);
		} catch (Exception e) {
		}
	}

	/**
	 * ��ȡ�˵�ģ��ķ���
	 */
	public MenuModule getMenuModule() {
		return (MenuModule) modules.get(MenuModule.class);
	}

	/**
	 * ��ȡ�������ģ��ķ���
	 */
	public PluginManagerModule getPluginManagerModule() {
		return (PluginManagerModule) modules.get(PluginManagerModule.class);
	}
}
