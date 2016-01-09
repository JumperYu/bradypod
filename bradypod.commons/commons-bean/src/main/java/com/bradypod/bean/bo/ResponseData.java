package com.bradypod.bean.bo;

/**
 * 业务传输对象
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年1月9日
 */
public class ResponseData<T> {

	private boolean isSuccess = false; // default is false

	private String message;

	private T data; // POJO or PageData

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
