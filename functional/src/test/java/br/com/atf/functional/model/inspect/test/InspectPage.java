package br.com.atf.functional.model.inspect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
'
import br.com.atf.functional.exception.UnreachablePageException;
import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;

public class InspectPage {

	@Test
	public void whenGivenAValidUrlShouldReturnLoginForm() {
		Portal portal = new Portal("http://localhost:999/livraria/login?0");
		List<NavigationElement> elements = portal.inspectElements();

		int actualAttributesQuantity = elements.size();
		assertEquals(3, actualAttributesQuantity);

		for (NavigationElement element : elements) {
			Map<String, String> actualAttributes = element.getAttributes();

			boolean doesContainsTags = element.getTagName().equals("input") || element.getTagName().equals("button");
			boolean doesContainsKeys = actualAttributes.containsKey("type");
			boolean doesContainsValues = actualAttributes.containsValue("text")
					|| actualAttributes.containsValue("password") || actualAttributes.containsValue("submit");

			assertTrue(doesContainsTags);
			assertTrue(doesContainsKeys);
			assertTrue(doesContainsValues);
		}
	}

	@Test(expected=UnreachablePageException.class)
	public void whenGivenAnInvalidUrlReturnException() {
		Portal portal = new Portal("http://localhost:999/livra");
		portal.inspectElements();
	}
	
	@Test
	public void whenGivenAnEmptydUrlReturnException() {
		Portal portal = new Portal("");
		portal.inspectElements();
	}
	
	@Test//(expected=RedirectPageException.class)
	public void whenGivenAValidUrlWichIsRedirectReturnException() {

		Portal portal = new Portal("http://localhost:999/livraria");
		portal.inspectElements();

	}

}
