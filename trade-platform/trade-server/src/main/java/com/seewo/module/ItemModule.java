package com.seewo.module;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.seewo.module.Module;
import com.seewo.po.Settings;
import com.seewo.service.ItemQueryService;
import com.seewo.service.impl.ItemQueryServiceImpl;

public class ItemModule extends AbstractModule{
	
	private final Settings settings;
	
	private Map<String, Module> features = new HashMap<>();
	
	public ItemModule(Settings settings) {
		this.settings = settings;
	}
	
	public void register(String feature, Module module) {
		Class<?> clazz = module.configure();
		try {
			clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		features.put(feature, module);
	}
	
	@Override
	protected void configure() {
		// bind interface to instance(features)
		bind(ItemQueryService.class).to(ItemQueryServiceImpl.class);
		
	}
	
}
