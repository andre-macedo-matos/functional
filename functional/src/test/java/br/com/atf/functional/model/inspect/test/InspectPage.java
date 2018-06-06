package br.com.atf.functional.model.inspect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.UnsupportedCommandException;

import br.com.atf.functional.exception.NotReachablePageException;
import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;

public class InspectPage {
	
	@Test
	public void whenGivenAValidUrlShouldReturnLoginForm() throws NotReachablePageException {		
		Portal portal = new Portal("http://localhost:999/livraria");
		portal.inspectElements();
		List<NavigationElement> elements = portal.getNavigationElements();
		 
		int actualAttributesQuantity = elements.size();
		assertEquals(3, actualAttributesQuantity);
		
		for (NavigationElement element : elements) {
			Map<String, String> actualAttributes = element.getAttributes();
			
			boolean doesContainsTags = element.getTagName().equals("input") || element.getTagName().equals("button");
			boolean doesContainsKeys = actualAttributes.containsKey("type");
			boolean doesContainsValues = actualAttributes.containsValue("text") || actualAttributes.containsValue("password") || actualAttributes.containsValue("submit");
			
			assertTrue(doesContainsTags);
			assertTrue(doesContainsKeys);
			assertTrue(doesContainsValues);
		}
	}
	
	@Test(expected=NotReachablePageException.class)
	public void whenGivenAnInvalidUrlReturnException() throws NotReachablePageException {
		Portal portal = new Portal("http://localhost:999/livaria");
		portal.inspectElements();
	}
	
	@Test(expected=UnsupportedCommandException.class)
	public void whenGivenAnEmptydUrlReturnException() throws NotReachablePageException {
		Portal portal = new Portal("");
		portal.inspectElements();
	}
	
}
