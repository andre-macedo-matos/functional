package br.com.atf.functional.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.openqa.selenium.WebDriver;

import br.com.atf.functional.annotation.NotFound;

public class NotFoundValidator implements ConstraintValidator<NotFound, String>{
	
	@Inject private WebDriver driver;

	@Override
	public void initialize(NotFound constraintAnnotation) {
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext context) {
		driver.get(url);
		String pageSource = driver.getPageSource();
		
		return !wasPageNotFound(pageSource);
	}

	private boolean wasPageNotFound(String pageSource) {
		return pageSource.contains("404") 
				|| pageSource.equals("<html><head></head><body></body></html>")
				|| pageSource.contains("ERR_CONNECTION_REFUSED") 
				|| pageSource.contains("INET_E_RESOURCE_NOT_FOUND");
	}
	
}
