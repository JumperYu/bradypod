package com.seewo.modules;

import com.seewo.module.Module;

public class MyModule extends Module{
	
	public MyModule(String moduleName, String service) {
		super(moduleName, service);
	}

	@Override
	public Class<?> configure() {
		return ItemQueryServiceImpl.class;
	}

}
