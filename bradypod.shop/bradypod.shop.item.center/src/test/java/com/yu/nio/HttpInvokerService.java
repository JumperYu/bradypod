package com.yu.nio;


/**
 * 
 * httpinvoker demo
 *
 * @author zengxm
 * @date 2015年9月5日
 *
 */
public interface HttpInvokerService {

	public MyEntity get(int id);
	
	public void insert(MyEntity entity);
}
