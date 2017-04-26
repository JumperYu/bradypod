package com.seewo.trade.bean;

import com.fasterxml.jackson.annotation.JsonView;
import com.seewo.trade.commons.ErrorcodeType;
import com.seewo.trade.view.ResultEntityViews;

public class ResultEntity {
	@JsonView(ResultEntityViews.ResultView.class)
	private ErrorcodeType errorcode;
	@JsonView(ResultEntityViews.ResultView.class)
	private String message;
	@JsonView(ResultEntityViews.ResultView.class)
	private Object data;
	
	public ResultEntity(){
		errorcode = ErrorcodeType.SUCCESS;
		message = "";
		data = null;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public ErrorcodeType getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(ErrorcodeType errorcode) {
		this.errorcode = errorcode;
	}

	public void setErrorInfo(ErrorcodeType err, String msg, Object data){
		this.errorcode = err;
		this.message = msg;
		this.data = data;
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param data
	 */
	public ResultEntity(ErrorcodeType errorCode, String message, Object data) {
		super();
		this.errorcode = errorCode;
		this.message = message;
		this.data = data;
	}

}
