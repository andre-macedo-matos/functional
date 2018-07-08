package br.com.atf.functional.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

@RequestScoped
public class WebDriverFactory {
	
	@Produces
	public WebDriver initDriver() {
		String phatomJSDriver = System.getProperty("user.home") + "\\functional_config\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJSDriver);
		return new PhantomJSDriver();
	}
	
	public void quitDriver(@Disposes WebDriver driver) {
		driver.quit();
	}

}
