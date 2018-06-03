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
	private List<NavigationElement> elements;

	public Portal(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NavigationElement> getElements() {
		return elements;
	}
	
	public void setElements(List<NavigationElement> elements) {
		this.elements = elements;
	}
	
	public void getNavigationElements() {
		WebDriver driver = getDriverProperty();
		driver.get(this.url);

		List<WebElement> elements = driver.findElements(By.xpath("//form/input"));

		this.elements = new ArrayList<>();
		for (WebElement element : elements) {
			String tagName = element.getTagName();
			Map<String, String> attributes = getAttributesOfElement(element, driver);
			
			NavigationElement navigationElement = new NavigationElement(tagName, attributes);
			this.elements.add(navigationElement);
		}
		
		driver.quit();
	}

	private Map<String, String> getAttributesOfElement(WebElement element, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String script = "var items = {}; " + "for (index = 0; index < arguments[0].attributes.length; ++index){"
				+ "items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value " + "};"
				+ "return items;";

		Map<String, String> attributes = ((Map<String, String>) executor.executeScript(script, element));
		return attributes;
	}

	private WebDriver getDriverProperty() {
		WebDriver driver = null;
		if (System.getProperty("phantomjs.binary.path") != null) {
			driver = initPhantomJs();

		} else if (System.getProperty("webdriver.edge.driver") != null) {
			driver = initEdgeDriver();

		} else if (System.getProperty("webdriver.chrome.driver") == null) {
			driver = initChromeDriver();

		} else {
			throw new RuntimeException("Não foi possível inicializar nenhum driver");
		}
		return driver;
	}

	private WebDriver initPhantomJs() {
		String phatomJsDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phatomJsDriver);
		return new PhantomJSDriver();
	}

	private WebDriver initEdgeDriver() {
		String edgeDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\MicrosoftWebDriver.exe";
		System.setProperty("webdriver.edge.driver", edgeDriver);
		return new EdgeDriver();
	}

	private WebDriver initChromeDriver() {
		String chromeDriver = System.getProperty("user.dir") + "\\src\\main\\resources\\DRIVERS\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		return new ChromeDriver();
	}

}
