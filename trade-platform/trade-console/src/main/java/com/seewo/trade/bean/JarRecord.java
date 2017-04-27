package com.seewo.trade.bean;

import com.wjx.loader.PluginClassLoader;

public class JarRecord {
	private int id;
	private String name;
	private String path;
	private PluginClassLoader pluginClassLoader;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public PluginClassLoader getPluginClassLoader() {
		return pluginClassLoader;
	}
	public void setPluginClassLoader(PluginClassLoader pluginClassLoader) {
		this.pluginClassLoader = pluginClassLoader;
	}
	@Override
	public String toString() {
		return "JarRecord [id=" + id + ", name=" + name + ", path=" + path + ", pluginClassLoader=" + pluginClassLoader
				+ "]";
	}
	
}
