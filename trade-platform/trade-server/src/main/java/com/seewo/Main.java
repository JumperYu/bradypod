package com.seewo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.seewo.module.ItemModule;
import com.seewo.po.Item;
import com.seewo.service.ItemQueryService;

public class Main {
	
	
	public static void main(String[] args) {
		
		
		Injector injector = Guice.createInjector(new ItemModule(null));
		
		// 1. list items; ItemModule.get
		
		ItemQueryService itemQueryService = injector.getInstance(ItemQueryService.class);
		
		Item item = itemQueryService.queryItemById(1L);
		
		itemQueryService.listItems();
		
		System.out.println(item);
		
		// 2. query item
		
		// 3. order
		
		// 4. confirm order
		
		// 5. output order
		
	}
	
}
