package br.com.atf.functional.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import br.com.atf.functional.annotation.NotFound;

public class NotFoundValidator implements ConstraintValidator<NotFound, String>{

	@Override
	public void initialize(NotFound constraintAnnotation) {
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext context) {
		WebDriver driver = initDriver();
		driver.get(url);
		String pageSource = driver.getPageSource();
		driver.quit();

		return !wasPageNotFound(pageSource);
	}

	private boolean wasPageNotFound(String pageSource) {
		return pageSource.contains("404") 
				|| pageSource.equals("<html><head></head><body></body></html>")
				|| pageSource.contains("ERR_CONNECTION_REFUSED") 
				|| pageSource.contains("INET_E_RESOURCE_NOT_FOUND");
	}

	private WebDriver initDriver() {
		String phatomJsDriver = System.getProperty("user.home") + "\\functional_config\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJsDriver);
		WebDriver driver = new PhantomJSDriver();
		return driver;
	}
}
