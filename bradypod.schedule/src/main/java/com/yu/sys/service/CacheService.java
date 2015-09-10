package com.yu.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存示例
 *
 * @author zengxm
 * @date 2015年8月4日
 *
 */
@Service
public class CacheService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Cacheable(value = "default", key = "'CacheService.getMap'")
	public Map<String, String> getMap() {
		log.info("获取map值");
		Map<String, String> val = new HashMap<String, String>();
		val.put("key-1", "1");
		val.put("key-2", "2");
		return val;
	}

	@CachePut(value = "default", key = "'CacheService.getMap'")
	public Map<String, String> putMap() {
		log.info("设置map值");
		Map<String, String> val = new HashMap<String, String>();
		val.put("key-1", "key-1");
		val.put("key-2", "key-2");
		return val;
	}

	@Cacheable(value = "default", key = "'CacheService.getList'")
	public List<String> getList() {
		log.info("获取list值");
		List<String> list = new ArrayList<String>();
		list.add("list");
		return list;
	}

	@CacheEvict(value = "default", allEntries = false, key = "'CacheService.getList'")
	public void updateCache() {
		log.info("更新缓存");
	}

}
