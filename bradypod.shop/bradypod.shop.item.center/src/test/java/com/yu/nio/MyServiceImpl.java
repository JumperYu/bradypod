package com.yu.nio;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService(name = "MyService", serviceName = "MyWebService", targetNamespace = "http://MyService/client")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MyServiceImpl implements MyService {

	@Override
	public String echo(String msg) {
		return "hello " + msg;
	}

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9527/MyService", new MyServiceImpl());
		System.out.println("service started!");
	}

}
