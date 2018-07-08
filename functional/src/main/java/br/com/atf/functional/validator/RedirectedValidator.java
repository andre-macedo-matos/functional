package br.com.atf.functional.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.openqa.selenium.WebDriver;

import br.com.atf.functional.annotation.Redirected;

public class RedirectedValidator implements ConstraintValidator<Redirected, String> {
	
	@Inject private WebDriver driver;
	
	@Override
	public void initialize(Redirected constraintAnnotation) {
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext context) {
		HibernateConstraintValidatorContext hibernateContext = context
				.unwrap(HibernateConstraintValidatorContext.class);
		
		driver.get(url);
		String currentUrl = driver.getCurrentUrl();
		
		if (!url.equals(currentUrl)) {
			hibernateContext.disableDefaultConstraintViolation();
			hibernateContext.buildConstraintViolationWithTemplate("{portal.url.redirect} (" + currentUrl + ").")
				.addConstraintViolation();
			return false;
		}
		
		return true;
	}
	
}
