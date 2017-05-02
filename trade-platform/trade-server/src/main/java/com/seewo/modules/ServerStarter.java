package com.seewo.modules;

import java.util.concurrent.TimeUnit;

import com.seewo.modules.api.ItemQueryService;

public class ServerStarter {
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		Modules server = new Modules();
		ItemManagerModule itemManagerModule = server.getItemManagerModule();

		while (true) {
			ItemQueryService itemQueryService1 = itemManagerModule.getInstance("1001", ItemQueryService.class);

			ItemQueryService itemQueryService2 = itemManagerModule.getInstance("1002", ItemQueryService.class);

			if (itemQueryService1 != null) {
				itemQueryService1.queryItem(123L);
				System.out.println(itemQueryService1.getClass().getClassLoader());
			}
			if (itemQueryService2 != null) {
				itemQueryService2.queryItem(13L);
				System.out.println(itemQueryService2.getClass().getClassLoader());
			}
			TimeUnit.SECONDS.sleep(3);
		} 
	}
}