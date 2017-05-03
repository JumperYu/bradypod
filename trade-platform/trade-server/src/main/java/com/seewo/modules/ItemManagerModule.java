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

import com.google.common.collect.Maps;
import com.seewo.modules.api.DiscountService;
import com.seewo.modules.api.ItemQueryService;
import com.seewo.modules.api.LogisticsService;
import com.seewo.modules.api.ReduceInventoryService;
import com.seewo.modules.impl.DefaultDiscountServiceImpl;
import com.seewo.modules.impl.DefaultLogisticsServiceImpl;
import com.seewo.modules.impl.DefaultReduceInventoryServiceImpl;

public class ItemManagerModule implements Module {

	private File pluginDir;

	private Monitor monitor;

	private List<File> plugins = new ArrayList<File>();

	private Map<Class<?>, Map<String, Class<?>>> instances = new HashMap<>();

	private ScheduledExecutorService executor = null;

	public static final String PATH = "/data/plugins";

	public static final String MODULE_NAME = "module.jar";

	public static final String CONIFG_NAME = "conf.properties";

	@Override
	public void destroy() {
	}

	@Override
	public void start() {
		pluginDir = new File(PATH);

		instances.put(ItemQueryService.class, null);
		
		Map<String, Class<?>> map = Maps.newHashMap();
		map.put("default", DefaultReduceInventoryServiceImpl.class);
		
		instances.put(ReduceInventoryService.class, map);
		
		map = Maps.newHashMap();
		map.put("default", DefaultDiscountServiceImpl.class);
		
		instances.put(DiscountService.class, map);
		
		map = Maps.newHashMap();
		map.put("default", DefaultLogisticsServiceImpl.class);
		
		instances.put(LogisticsService.class, map);

		loadPlugin();

		monitor = new Monitor();
		executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleWithFixedDelay(monitor, 0, 1, TimeUnit.SECONDS);
	}

	@Override
	public void stop() {

	}

	class Monitor implements Runnable {

		@Override
		public void run() {
			loadPlugin();
		}

	}

	public void loadPlugin() {
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
				prop.load(new FileInputStream(new File(pluginFile, CONIFG_NAME)));
				String feature = (String) prop.get("feature");

				ModuleClassLoader loader = new ModuleClassLoader();
				loader.addDirectory(new File(pluginFile, MODULE_NAME));

				JarFile jarFile = new JarFile(new File(pluginFile, MODULE_NAME));
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry element = (JarEntry) entries.nextElement();
					String name = element.getName();
					if (name.contains(".class")) {
						System.out.println(name);
						System.out.println(name.replaceAll("/", ".").replace(".class", ""));
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
				plugins.add(pluginFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("出错了 " + pluginFile);
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
				if (!featureInstasnce.containsKey(feature)) {
					feature = "default";
				}
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
