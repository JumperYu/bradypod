package com.yu.admin.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.yu.article.po.Article;

public class ArticleValidator implements Validator {

	private final Validator articleValidator;

	public ArticleValidator(Validator addressValidator) {
		if (addressValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is "
					+ "required and must not be null.");
		}
		if (!addressValidator.supports(Article.class)) {
			throw new IllegalArgumentException("The supplied [Validator] must "
					+ "support the validation of [Address] instances.");
		}
		this.articleValidator = addressValidator;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Article.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "summary",
				"field.required");
		Article article = (Article) target;
		try {
			errors.pushNestedPath("title");
			ValidationUtils.invokeValidator(this.articleValidator,
					article.getTitle(), errors);
		} finally {
			errors.popNestedPath();
		}
	}

}
