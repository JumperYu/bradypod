package com.bradypod.common.po;

/**
 * 全局状态码
 *
 * @author zengxm
 * @date 2015年7月27日
 *
 */
public class ResultCode {

	/**
	 * 失败
	 */
	public static final int FAILURE = 0;

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 参数错误
	 */
	public static final int PARAMETER_ERROR = -10;

	/**
	 * 超时
	 */
	public static final int TIMEOUT = -20;

	/**
	 * 数据库错误
	 */
	public static final int DATABASE_ERROR = -30;

	/**
	 * 记录已存在(唯一)
	 */
	public static final int RECORD_EXISTED = -31;

	/**
	 * 记录不存在
	 */
	public static final int RECORD_NOT_EXIST = -31;

	/**
	 * 未知错误
	 */
	public static final int UNKNOWN_ERROR = -100;

	/**
	 * <-- Begin
	 * @use { ExceptionHandlerController.java }
	 * @author 善财童子<zengxm@ttwz168.net>
	 * @date 2015-09-10
	 */

	/** 内部异常 */
	public static final Integer INTERNAL_EXCEPTION = 1004001;

	/** 内部异常 */
	public static final Integer NO_SUCH_REQUEST_HANDLING_METHOD_EXCEPTION = 1004002;

	/** http请求方法不支持 */
	public static final Integer HTTPREQUEST_METHOD_NOTSUPPORTED_EXCEPTION = 1004003;

	/** http媒体类型不支持 */
	public static final Integer HttpMediaTypeNotSupportedException = 1004004;

	/** 客户端无法支持的媒体类型 */
	public static final Integer HttpMediaTypeNotAcceptableException = 1004005;

	/** 缺少请求路径的变量 */
	public static final Integer MissingPathVariableException = 1004006;

	/** 缺少必须请求参数 */
	public static final Integer MissingServletRequestParameterException = 1004007;

	/** 请求参数无法绑定 */
	public static final Integer ServletRequestBindingException = 1004008;

	/** 转换出错 */
	public static final Integer ConversionNotSupportedException = 1004009;

	/** 类型不匹配 */
	public static final Integer TypeMismatchException = 1004010;

	/** 消息不可读 */
	public static final Integer HttpMessageNotReadableException = 1004011;

	/** 消息不可写 */
	public static final Integer HttpMessageNotWritableException = 1004012;

	/** 方法参数验证不正确 */
	public static final Integer MethodArgumentNotValidException = 1004013;

	/** 缺少请求体 */
	public static final Integer MissingServletRequestPartException = 1004014;

	/** 绑定异常 */
	public static final Integer BindException = 1004015;

	/** End --> */

}
