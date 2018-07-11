package br.com.atf.functional.validator;

import java.util.List;

import javax.inject.Inject;

import org.openqa.selenium.WebDriver;

import br.com.atf.functional.model.Portal;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validator;

public class PortalValidator {
	
	private Validator validator;
	private Result result;
	private WebDriver driver;

	@Inject
	public PortalValidator(Validator validator, Result result, WebDriver driver) {
		this.validator = validator;
		this.result = result;
		this.driver = driver;
	}
	
	@Deprecated
	public PortalValidator() {
		this(null,null, null);
	}
	
	public void validate(Portal portal) {
		validator.validate(portal);
		
		List<Message> errors = validator.getErrors();
		for (Message message : errors) {
			String errorMessage = message.getMessage();
			if(isRedirectedError(errorMessage)) {
				String redirectedUrl = getRedirectedUrl(portal.getUrl());
				
				result.include("errorMessage", errorMessage);
				result.include("redirectedUrl", redirectedUrl);
			}
		}
	}

	private boolean isRedirectedError(String errorMessage) {
		return errorMessage.contains("redirecionou");
	}
	
	private String getRedirectedUrl(String url) {
		driver.get(url);
		return driver.getCurrentUrl();
	}
	
	public <T> T onErrorRedirectTo(T controller) {
		return validator.onErrorRedirectTo(controller);
	}
	
	
	

}
