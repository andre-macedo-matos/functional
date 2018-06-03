package br.com.atf.functional.model.inspect.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import br.com.atf.functional.model.Portal;

public class InspectPage {
	
	@Test
	public void whenGivenAValidUrlShouldReturnLoginForm() {		
		Portal portal = new Portal("http://localhost:8080/livraria");
		Map<WebElement, Object> components = portal.getNavigationElements();
		
		int actualAttributesQuantity = components.size();
		assertEquals(2, actualAttributesQuantity);
	}

	

}
