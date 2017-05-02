package com.seewo.demo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PluginManagerModule implements Module {
	private File pluginDir;

	private Monitor monitor;
	
	private List<File> plugins = new ArrayList<File>();

	private ScheduledExecutorService executor = null;

	@Override
	public void destroy() {
	}

	@Override
	public void start() {
		System.out.println("锟斤拷锟侥ｏ拷锟斤拷锟斤拷锟斤拷锟�");
		pluginDir = new File("plugins");
		monitor = new Monitor();
		executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleWithFixedDelay(monitor, 0, 5, TimeUnit.SECONDS);
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
				if(plugins.contains(pluginFile)){
					continue;
				}
				try {
					Properties prop = new Properties();
					prop.load(new FileInputStream(new File(pluginFile,
							"conf.properties")));
					PluginClassLoader loader = new PluginClassLoader();
					loader.addDirectory(new File(pluginFile,"plugin.jar"));
					Plugin plugin = (Plugin) loader.loadClass(
							prop.getProperty("pluginClass")).newInstance();
					plugin.start();
					plugins.add(pluginFile);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("锟睫凤拷锟斤拷锟截诧拷锟斤拷锟�" + pluginFile);
				}
			}
		}

	}

}
