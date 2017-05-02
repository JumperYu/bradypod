package com.seewo.module;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.inject.Injector;
import com.seewo.demo.PluginClassLoader;

public class Modules {

	private Map<String, Module> modules = new HashMap<>();

	private List<File> plugins = new ArrayList<File>();

	ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);

	public void start() {

		// 定时去尝试加载外部扩展模块
		executor.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				
				File[] listFiles = new File("").listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.isDirectory();
					}
				});
				
				for (int i = 0; i < listFiles.length; i++) {
					File pluginFile = listFiles[i];
					if (plugins.contains(pluginFile)) {
						continue;
					}
					try {
						Properties prop = new Properties();
						prop.load(new FileInputStream(new File(pluginFile, "conf.properties")));
						PluginClassLoader loader = new PluginClassLoader();
						loader.addDirectory(new File(pluginFile, "module.jar"));

						// 尝试加载Module
						String moduleName = prop.getProperty("moduleName"); // 
						Module module = (Module) loader.loadClass(moduleName).newInstance();
						
						// Class<?> clazz = (Class<?>) loader.loadClass(module.getService()).newInstance();
						// Class<?> clazz = (Class<?>) module.configure();
						
						
						// module config 拿到service modules.get module add plugin

						// 拿到itemModule register feature module 模块技术

						// plugin.start();
						plugins.add(pluginFile);

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("加载" + pluginFile);
					}
				}

			}
		}, 0, 5, TimeUnit.SECONDS);
	}

	public <T> T getInstance(Class<T> clazz) {
		return getInjector().getInstance(clazz);
	}
	
	Injector injector;
	
	public Injector getInjector() {
		if (injector == null) {
			ItemModule itemModule = new ItemModule(null);
			
			// injector = Guice.createInjector(modules.values());
		}
		return injector;
	}
}
