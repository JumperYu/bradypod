package com.seewo.po;

public class Result<T> {

	private String message;

	private boolean success;

	private T data;

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "Result [message=" + message + ", data=" + data + "]";
	}

}
