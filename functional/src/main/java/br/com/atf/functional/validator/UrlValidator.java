package br.com.atf.functional.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import br.com.atf.functional.annotation.Url;

public class UrlValidator implements ConstraintValidator<Url, String> {
	
	@Override
	public void initialize(Url constraintAnnotation) {
	}

	@Override
	public boolean isValid(@NotNull String url, ConstraintValidatorContext context) {
		HibernateConstraintValidatorContext hibernateContext = context
				.unwrap(HibernateConstraintValidatorContext.class);
		
		WebDriver driver = setDriver();
		driver.get(url);
		String pageSource = driver.getPageSource();
		String currentUrl = driver.getCurrentUrl();
		driver.quit();
		
		if(isPageUnreachable(pageSource)) {
			hibernateContext.disableDefaultConstraintViolation();
			hibernateContext.buildConstraintViolationWithTemplate("{portal.url.unreachable}").addConstraintViolation();
			return false;
		}
		
		if(!url.equals(currentUrl)) {
			hibernateContext.disableDefaultConstraintViolation();
			hibernateContext.buildConstraintViolationWithTemplate("{portal.url.redirect}").addConstraintViolation();
			return false;
		}
		
		
		return true;
	}

	
	private boolean isPageUnreachable(String pageSource) {
		return pageSource.contains("404") 
				|| pageSource.equals("<html><head></head><body></body></html>")
				|| pageSource.contains("ERR_CONNECTION_REFUSED")
				|| pageSource.contains("INET_E_RESOURCE_NOT_FOUND");
	}

	private WebDriver setDriver() {
		String phatomJsDriver = System.getProperty("user.home") + "\\functional_config\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJsDriver);
		WebDriver driver = new PhantomJSDriver();
		return driver;
	}
}
