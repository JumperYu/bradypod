package com.seewo.module;

public abstract class Module {

	private String moduleName;

	private String service;

	public Module(String moduleName, String service) {
		this.moduleName = moduleName;
		this.service = service;
	}

	public abstract Class<?> configure();
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
