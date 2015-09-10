package com.yu.util.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 
 * Description:JSR303 Validator
 *
 * @author 善财童子<zengxm@ttwg168.net>
 * @date 2015年9月10日 下午2:58:53
 */
public class BeanValidators {

	private volatile static Validator validator;

	/**
	 * 验证对象是否合法并且抛出异常
	 * 
	 * @param object
	 *            - 需要验证的Java Bean
	 * @param groups
	 *            - 可不传入
	 * @throws ConstraintViolationException
	 *             - 统一异常包装
	 */
	public static void validateWithException(Object object, Class<?>... groups)
			throws ConstraintViolationException {
		Set<ConstraintViolation<Object>> constraintViolations = getValidator()
				.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}

	/**
	 * 使用Hibernate的实现
	 * 
	 * @return - javax.validation.Validator
	 */
	public static Validator getValidator() {
		if (validator == null) {
			synchronized (BeanValidators.class) {
				if (validator == null) {
					ValidatorFactory factory = Validation
							.buildDefaultValidatorFactory();
					validator = factory.getValidator();
				}
			}
		}
		return validator;
	}

	/** 更多扩展请自行实现 */
}