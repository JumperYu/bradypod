package com.seewo.trade.commons;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.seewo.trade.bean.JarRecord;

@Component
public class DB {
	public ConcurrentHashMap<Integer, JarRecord> jarMap=new ConcurrentHashMap<>();
}
