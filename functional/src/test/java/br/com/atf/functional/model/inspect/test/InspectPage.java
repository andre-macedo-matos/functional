package br.com.atf.functional.model.inspect.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.com.atf.functional.model.Portal;

public class InspectPage {
	
	@Test
	public void shouldGetLoginForm() {		
		Portal portal = new Portal("http://localhost:8080/livraria");
		Map<String, List<String>> components = portal.getNavigationElements();
		
		int actualComponentsQuantity = components.size();
		assertEquals(2, actualComponentsQuantity);
	}

	

}
