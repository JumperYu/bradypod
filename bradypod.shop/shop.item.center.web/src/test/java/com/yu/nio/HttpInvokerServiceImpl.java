package com.yu.nio;

import java.util.HashMap;

public class HttpInvokerServiceImpl implements HttpInvokerService {

	static HashMap<Integer, MyEntity> params = new HashMap<Integer, MyEntity>();

	@Override
	public MyEntity get(int id) {
		return params.get(id);
	}

	@Override
	public void insert(MyEntity entity) {
		params.put(entity.getId(), entity);
	}

}
