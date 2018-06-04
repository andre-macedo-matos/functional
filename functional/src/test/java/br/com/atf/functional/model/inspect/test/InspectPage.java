package br.com.atf.functional.model.inspect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;

public class InspectPage {
	
	@Test
	public void whenGivenAValidUrlShouldReturnLoginForm() {		
		Portal portal = new Portal("http://localhost:8080/livraria");
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
	
	@Test
	public void whenGivenAnInvalidUrlReturnErrorMessage() {
		Portal portal = new Portal("http://localhost:8080/livaria");
		portal.inspectElements();
		assertEquals(Collections.emptyList(), portal.getNavigationElements());
	}

}
