package com.seewo.trade.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGeneration {
	
	private static AtomicInteger no=new AtomicInteger(1);
	
	public static final int getId(){
		return no.getAndIncrement();
	}
}
