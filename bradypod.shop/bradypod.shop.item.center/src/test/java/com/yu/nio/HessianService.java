package com.yu.nio;


/**
 * 
 * hessian demo
 *
 * @author zengxm
 * @date 2015年9月5日
 *
 */
public interface HessianService {

	public MyEntity get(int id);
	
	public void insert(MyEntity entity);
}
