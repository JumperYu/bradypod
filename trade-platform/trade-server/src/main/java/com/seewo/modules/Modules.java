package com.seewo.modules;

import java.util.LinkedHashMap;
import java.util.Map;

public class Modules {
	
	private static Modules instance;
	private Map<Class<?>, Module> modules; 
	private ClassLoader loader; 

	public static Modules getInstance() {
		return instance;
	}

	public Modules() {
		if (instance != null) {
			throw new IllegalStateException("instance error");
		}
		instance = this;
		init();
	}

	private void init() {
		modules = new LinkedHashMap<Class<?>, Module>();
		loader = this.getClass().getClassLoader();
		loadModules();
		startModules();
	}

	private void loadModules() {
		loadModule(ItemManagerModule.class.getName()); 
//		loadModule(MenuModule.class.getName());
	}

	/**
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
	 */
	public ItemManagerModule getItemManagerModule() {
		return (ItemManagerModule) modules.get(ItemManagerModule.class);
	}
}
