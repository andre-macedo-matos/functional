package br.com.atf.functional.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import br.com.atf.functional.exception.NotReachablePageException;

public class Portal {

	private String url;
	private List<NavigationElement> navigationElements;

	public Portal(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NavigationElement> getNavigationElements() {
		return navigationElements;
	}

	public void setNavigationElements(List<NavigationElement> elements) {
		this.navigationElements = elements;
	}

	public void inspectElements() throws NotReachablePageException {
		WebDriver driver = initDriver();

		driver.get(this.url);
		
		String pageSource = driver.getPageSource();

		if (isPageReachable(pageSource)) {
			driver.quit();
			throw new NotReachablePageException("A página da url [" + this.url + "] não foi encontrada.");
		}
		List<WebElement> elements = driver.findElements(By.xpath("//form/input|//form/button"));

		this.navigationElements = new ArrayList<>();
		for (WebElement element : elements) {
			String tagName = element.getTagName();
			Map<String, String> attributes = getAttributesOfElement(driver, element);

			NavigationElement navigationElement = new NavigationElement(tagName, attributes);
			this.navigationElements.add(navigationElement);
		}
		
		driver.quit();

	}

	private boolean isPageReachable(String pageSource) {
		return pageSource.contains("404") 
				|| pageSource.equals("<html><head></head><body></body></html>")
				|| pageSource.contains("ERR_CONNECTION_REFUSED")
				|| pageSource.contains("INET_E_RESOURCE_NOT_FOUND");
	}

	private Map<String, String> getAttributesOfElement(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String script = "var items = {}; " + "for (index = 0; index < arguments[0].attributes.length; ++index){"
				+ "items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value " + "}"
				+ "return items;";

		@SuppressWarnings("unchecked")
		Map<String, String> attributes = ((Map<String, String>) executor.executeScript(script, element));
		return attributes;
	}

	private WebDriver initDriver() {
		String phatomJsDriver = System.getProperty("user.home") + "\\functional_config\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJsDriver);

		String edgeDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\MicrosoftWebDriver.exe";
		System.setProperty("webdriver.edge.driver", edgeDriver);

		String chromeDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		
		return new PhantomJSDriver();
	}

}
