package br.com.atf.functional.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class Portal {

	private String url;
	private List<NavigationElement> navigationElements;
	private WebDriver driver;

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
	
	public void inspectElements() {
		driver = getDriverProperty();
		driver.get(this.url);

		List<WebElement> elements = driver.findElements(By.xpath("//form/input|//form/button"));

		this.navigationElements = new ArrayList<>();
		for (WebElement element : elements) {
			String tagName = element.getTagName();
			Map<String, String> attributes = getAttributesOfElement(element);
			
			NavigationElement navigationElement = new NavigationElement(tagName, attributes);
			this.navigationElements.add(navigationElement);
		}
		
		closeDriver();
	}

	private Map<String, String> getAttributesOfElement(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String script = "var items = {}; "
				+ "for (index = 0; index < arguments[0].attributes.length; ++index){"
				+ "items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value " 
				+ "}"
				+ "return items;";

		@SuppressWarnings("unchecked")
		Map<String, String> attributes = ((Map<String, String>) executor.executeScript(script, element));
		return attributes;
	}

	private WebDriver getDriverProperty() {
		if (System.getProperty("phantomjs.binary.path") != null) {
		initPhantomJs();

		} else if (System.getProperty("webdriver.edge.driver") != null) {
			initEdgeDriver();

		} else if (System.getProperty("webdriver.chrome.driver") == null) {
			initChromeDriver();

		} else {
			throw new RuntimeException("Não foi possível inicializar nenhum driver");
		}
		return driver;
	}

	private void initPhantomJs() {
		String phatomJsDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJsDriver);
		driver = new PhantomJSDriver();
	}

	private void initEdgeDriver() {
		String edgeDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\MicrosoftWebDriver.exe";
		System.setProperty("webdriver.edge.driver", edgeDriver);
		driver = new EdgeDriver();
	}

	private void initChromeDriver() {
		String chromeDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		driver = new ChromeDriver();
	}

	private void closeDriver() {
		driver.quit();
		System.clearProperty("phantomjs.binary.path");
		System.clearProperty("webdriver.edge.driver");
		System.clearProperty("webdriver.chrome.driver");
	}
}
