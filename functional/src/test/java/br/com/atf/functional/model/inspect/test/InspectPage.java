package br.com.atf.functional.model.inspect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;

public class InspectPage {

	private Validator validator;
	
	@Before
	public void setup() {
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

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
	
	@Test
	public void whenNotGivenUrlReturnErrorMessage() {
		Portal portal = new Portal(null);
		Set<ConstraintViolation<Portal>> constraintViolations = validator.validate(portal);
		
		int quantityOfErrors = constraintViolations.size();
		String actualMessage = constraintViolations.iterator().next().getMessage();
		String expectedMessage = "Por favor, informe um endereço para realizar inspeção.";
		
		assertEquals(1, quantityOfErrors);
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	public void whenGivenEmptyUrlReturnErrorMessage() {
		Portal portal = new Portal("");
		Set<ConstraintViolation<Portal>> constraintViolations = validator.validate(portal);
		
		int quantityOfErrors = constraintViolations.size();
		String actualMessage = constraintViolations.iterator().next().getMessage();
		String expectedMessage = "Por favor, informe um endereço para realizar inspeção.";
		
		assertEquals(1, quantityOfErrors);
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	public void whenGivenAnInvalidUrlReturnErrorMessage() {
		String url = "http://localhost:999/livra";
		Portal portal = new Portal(url);
		Set<ConstraintViolation<Portal>> constraintViolations = this.validator.validate(portal);
		
		int quantityOfErrors = constraintViolations.size();
		String actualMessage = constraintViolations.iterator().next().getMessage();
		String expectedMessage = "Não foi possível acessar " + url + ". Por favor, verifique o endereço novamente.";
		
		assertEquals(1, quantityOfErrors);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void whenGivenAValidUrlWhichIsRedirectReturnErrorMessage() {
		String url = "http://localhost:999/livraria";
		Portal portal = new Portal(url);
		Set<ConstraintViolation<Portal>> constraintViolations = validator.validate(portal);
		
		int quantityOfErrors = constraintViolations.size();
		String expected = "O endereço " + url + " redirecionou para outro endereço (" + url + "/login?1).";
		String validatorMessage = constraintViolations.iterator().next().getMessage();
		
		assertEquals(1, quantityOfErrors);
		assertEquals(expected, validatorMessage);
	}

}
