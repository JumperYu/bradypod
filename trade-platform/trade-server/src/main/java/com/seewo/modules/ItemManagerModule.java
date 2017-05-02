package com.seewo.modules;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.seewo.modules.api.ItemQueryService;
import com.seewo.modules.api.ReduceInventoryService;

public class ItemManagerModule implements Module {

	private File pluginDir;

	private Monitor monitor;

	private List<File> plugins = new ArrayList<File>();

	private Map<Class<?>, Map<String, Class<?>>> instances = new HashMap<>();
	
	private ScheduledExecutorService executor = null;

	@Override
	public void destroy() {
	}

	@Override
	public void start() {
		pluginDir = new File("plugins");
		monitor = new Monitor();
		executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleWithFixedDelay(monitor, 0, 1, TimeUnit.SECONDS);

		instances.put(ReduceInventoryService.class, null);
		instances.put(ItemQueryService.class, null);
	}

	@Override
	public void stop() {

	}

	class Monitor implements Runnable {

		@Override
		public void run() {
			File[] listFiles = pluginDir.listFiles(new FileFilter() {
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
					String feature = (String) prop.get("feature");

					ModuleClassLoader loader = new ModuleClassLoader();
					loader.addDirectory(new File(pluginFile, "plugin.jar"));

					JarFile jarFile = new JarFile(new File(pluginFile, "plugin.jar"));
					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						JarEntry element = (JarEntry) entries.nextElement();
						String name = element.getName();
						if (name.contains(".class")) {
							System.out.println(name);
							Class<?> instance = loader.loadClass(name.replaceAll("/", ".").replace(".class", ""));
							for (Class<?> clazz : instances.keySet()) {
								if (clazz.isAssignableFrom(instance)) {
									Map<String, Class<?>> featureInstance = instances.get(clazz);
									if (featureInstance == null) {
										featureInstance = new HashMap<>();
									}
									featureInstance.put(feature, instance);
									instances.put(clazz, featureInstance);
								}
							}
						}
					}

					jarFile.close();
					loader.close();

					// Plugin plugin = (Plugin)
					// loader.loadClass(prop.getProperty("pluginClass")).newInstance();
					// plugin.start();
					plugins.add(pluginFile);
					// loader.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("出错了 " + pluginFile);
				}
			}
		}

	}

	// ReduceInventoryService
	@SuppressWarnings("unchecked")
	public <T> T getInstance(String feature, Class<T> clazz) {
		T t = null;
		try {
			
			Map<String, Class<?>> featureInstasnce = instances.get(clazz);
			if (featureInstasnce != null) {
				Class<?> instance = featureInstasnce.get(feature);
				if (instance != null) {
					t = (T) instance.newInstance();
				}
			}
		} catch (Exception e) {

		}
		return t;
	}

	public static void main(String[] args) {
		System.out.println("/123/123".replaceAll("/", "."));
	}
}
