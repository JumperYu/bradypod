package com.bradypod.shop.item.center.remote;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

import com.yu.article.service.BookService;
import com.yu.article.service.BookServiceImpl;

/**
 * 反射
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月22日 上午11:14:18
 */
public class RpcRroxy {

	@SuppressWarnings("unchecked")
	public <T> T create(Class<?> interfaceClass) throws RemoteException {
		final BookService bookService = new BookServiceImpl();

		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						return method.invoke(bookService, args);
					}
				});
	}

	public static void main(String[] args) throws RemoteException {
		RpcRroxy proxy = new RpcRroxy();
		BookService bookService = proxy.create(BookService.class);
		bookService.buyBook();
	}
}
