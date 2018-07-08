package br.com.atf.functional.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;

@RequestScoped
public class PortalInspector {

	private WebDriver driver;
	
	@Inject
	public PortalInspector(WebDriver driver) {
		this.driver = driver;
	}
	
	@Deprecated
	public PortalInspector() {
		this(null);
	}

	public List<NavigationElement> inspectForElements(Portal portal) {
		this.driver.get(portal.getUrl());
		
		List<WebElement> elements = this.driver.findElements(By.xpath("//form/input|//form/button"));

		List<NavigationElement> navigationElements = new ArrayList<>();
		for (WebElement element : elements) {
			String tagName = element.getTagName();
			Map<String, String> attributes = getAttributesOfElement(element);

			NavigationElement navigationElement = new NavigationElement(tagName, attributes);
			navigationElements.add(navigationElement);
		}

		return navigationElements;
	}

	private Map<String, String> getAttributesOfElement(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) this.driver;
		String script = "var items = {}; " + "for (index = 0; index < arguments[0].attributes.length; ++index){"
				+ "items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value " + "}"
				+ "return items;";

		@SuppressWarnings("unchecked")
		Map<String, String> attributes = ((Map<String, String>) executor.executeScript(script, element));
		return attributes;
	}
}
