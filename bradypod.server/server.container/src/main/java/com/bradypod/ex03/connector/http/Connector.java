package com.bradypod.ex03.connector.http;

/**
 * 连接器很重要
 * 
 * @author xiangmin.zxm
 *
 */
public interface Connector {
	
	public Container getContainer();
	
	public void setContainer(Container container);
	
    public boolean getEnableLookups();
    
    public HttpRequest createRequest();
    
    public HttpResponse createResponse();
    
    public void initialize();
}
