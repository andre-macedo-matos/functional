package br.com.atf.functional.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import br.com.atf.functional.annotation.Redirected;

public class RedirectedValidator implements ConstraintValidator<Redirected, String> {

	@Override
	public void initialize(Redirected constraintAnnotation) {
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext context) {
		HibernateConstraintValidatorContext hibernateContext = context
				.unwrap(HibernateConstraintValidatorContext.class);
		
		WebDriver driver = initDriver();
		driver.get(url);
		String currentUrl = driver.getCurrentUrl();
		driver.quit();
		
		if (!url.equals(currentUrl)) {
			hibernateContext.disableDefaultConstraintViolation();
			hibernateContext.buildConstraintViolationWithTemplate("{portal.url.redirect} (" + currentUrl + ").")
				.addConstraintViolation();
			return false;
		}
		
		return true;
	}
	
	private WebDriver initDriver() {
		String phatomJsDriver = System.getProperty("user.home") + "\\functional_config\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJsDriver);
		WebDriver driver = new PhantomJSDriver();
		return driver;
	}

}
