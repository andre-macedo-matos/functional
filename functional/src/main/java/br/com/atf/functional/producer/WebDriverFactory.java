package br.com.atf.functional.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import br.com.caelum.vraptor.environment.Environment;

@RequestScoped
public class WebDriverFactory {
	
	@Inject private Environment environment;
	
	@Produces
	public WebDriver initDriver() {
		System.setProperty("phantomjs.binary.path", environment.getResource("/test/drivers/phantomjsdriver.exe").getPath());
		return new PhantomJSDriver();
	}
	
	public void quitDriver(@Disposes WebDriver driver) {
		driver.quit();
	}

}
