package com.yu.web;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.yu.common.po.Result;
import com.yu.common.po.ResultCode;

/**
 * 异常处理响应配置
 * 
 * @author zengxm<github.com/JumperYu>
 *
 *         2015年9月9日 上午8:38:06
 */
@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value = {NoSuchRequestHandlingMethodException.class,
			HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeNotSupportedException.class,
			HttpMediaTypeNotAcceptableException.class,
			MissingPathVariableException.class,
			MissingServletRequestParameterException.class,
			ServletRequestBindingException.class,
			ConversionNotSupportedException.class, TypeMismatchException.class,
			HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class,
			MethodArgumentNotValidException.class,
			MissingServletRequestPartException.class, BindException.class,
			NoHandlerFoundException.class, ConstraintViolationException.class,
			Exception.class})
	@ResponseBody
	public Result<String> handleException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();

		if (ex instanceof NoSuchRequestHandlingMethodException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoSuchRequestHandlingMethod(
					(NoSuchRequestHandlingMethodException) ex, headers, status,
					request);
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
			return handleHttpRequestMethodNotSupported(
					(HttpRequestMethodNotSupportedException) ex, headers,
					status, request);
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			return handleHttpMediaTypeNotSupported(
					(HttpMediaTypeNotSupportedException) ex, headers, status,
					request);
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
			return handleHttpMediaTypeNotAcceptable(
					(HttpMediaTypeNotAcceptableException) ex, headers, status,
					request);
		} else if (ex instanceof MissingPathVariableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleMissingPathVariable((MissingPathVariableException) ex,
					headers, status, request);
		} else if (ex instanceof MissingServletRequestParameterException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestParameter(
					(MissingServletRequestParameterException) ex, headers,
					status, request);
		} else if (ex instanceof ServletRequestBindingException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleServletRequestBindingException(
					(ServletRequestBindingException) ex, headers, status,
					request);
		} else if (ex instanceof ConversionNotSupportedException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleConversionNotSupported(
					(ConversionNotSupportedException) ex, headers, status,
					request);
		} else if (ex instanceof TypeMismatchException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleTypeMismatch((TypeMismatchException) ex, headers,
					status, request);
		} else if (ex instanceof HttpMessageNotReadableException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleHttpMessageNotReadable(
					(HttpMessageNotReadableException) ex, headers, status,
					request);
		} else if (ex instanceof HttpMessageNotWritableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleHttpMessageNotWritable(
					(HttpMessageNotWritableException) ex, headers, status,
					request);
		} else if (ex instanceof MethodArgumentNotValidException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMethodArgumentNotValid(
					(MethodArgumentNotValidException) ex, headers, status,
					request);
		} else if (ex instanceof MissingServletRequestPartException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestPart(
					(MissingServletRequestPartException) ex, headers, status,
					request);
		} else if (ex instanceof BindException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleBindException((BindException) ex, headers, status,
					request);
		} else if (ex instanceof NoHandlerFoundException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoHandlerFoundException((NoHandlerFoundException) ex,
					headers, status, request);
		} else if (ex instanceof ConstraintViolationException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleConstraintViolationException(
					(ConstraintViolationException) ex, null, headers, status,
					request);
		} else {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal(ex, null, headers, status, request);
		}
	}

	/**
	 * 服务器出现异常信息
	 */
	private Result<String> handleExceptionInternal(Exception ex, Object object,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("internal error", ex);
		return processResult("handleExceptionInternal");
	}

	/**
	 * 处理Bean验证异常
	 */
	private Result<String> handleConstraintViolationException(
			ConstraintViolationException ex, Object object,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String msg = "bean validate error";
		for (ConstraintViolation<?> constraintViolation : ex
				.getConstraintViolations()) {
			msg = constraintViolation.getMessage();
		}
		return processResult(msg);
	}

	/**
	 * 没有处理器
	 */
	private Result<String> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return processResult("handleNoHandlerFoundException");
	}

	private Result<String> handleMissingServletRequestPart(
			MissingServletRequestPartException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("handleMissingServletRequestPart");
	}

	private Result<String> handleBindException(BindException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return processResult("handleBindException");
	}

	private Result<String> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("handleMethodArgumentNotValid");
	}

	private Result<String> handleHttpMessageNotWritable(
			HttpMessageNotWritableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("handleHttpMessageNotWritable");
	}

	private Result<String> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("handleHttpMessageNotReadable");
	}

	/**
	 * 参数类型不匹配
	 */
	private Result<String> handleTypeMismatch(TypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return processResult("handleTypeMismatch");
	}

	/**
	 * 参数绑定失败
	 */
	private Result<String> handleConversionNotSupported(
			ConversionNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("ConversionNotSupported");
	}

	/**
	 * 参数绑定失败
	 */
	private Result<String> handleServletRequestBindingException(
			ServletRequestBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("RequestBindingException");
	}

	/**
	 * 缺少请求参数
	 */
	private Result<String> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("missing request params");
	}

	/**
	 * 缺少请求参数
	 */

	private Result<String> handleMissingPathVariable(
			MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("MissingPathVariable");
	}

	/**
	 * 请求的方法不存在
	 */
	private Result<String> handleHttpMediaTypeNotAcceptable(
			HttpMediaTypeNotAcceptableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("MediaTypeNotAcceptable");
	}

	/**
	 * 请求的方法不存在
	 */
	private Result<String> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("MediaTypeNotSupported");
	}

	/**
	 * 请求的方法不存在
	 */
	private Result<String> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("RequestMethodNotSupported");
	}

	/**
	 * 请求的方法不存在
	 */
	private Result<String> handleNoSuchRequestHandlingMethod(
			NoSuchRequestHandlingMethodException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return processResult("NoSuchRequestHandlingMethod");
	}
	private static Result<String> processResult(String message) {
		Result<String> result = new Result<>();
		result.setCode(ResultCode.FAILURE);
		result.setMessage(message);
		return result;
	}

	protected Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

}
