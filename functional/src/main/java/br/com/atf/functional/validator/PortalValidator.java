package br.com.atf.functional.validator;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.atf.functional.model.Portal;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validator;

@RequestScoped
public class PortalValidator {
	
	private Validator validator;
	private Result result;

	@Inject
	public PortalValidator(Validator validator, Result result) {
		this.validator = validator;
		this.result = result;
	}
	
	@Deprecated
	public PortalValidator() {
		this(null,null);
	}
	
	public void validate(Portal portal) {
		validator.validate(portal);
		
		if(isRedirectedError()) {
			result.include("redirectedUrl", getRedirectedUrl());
		}
	}

	private boolean isRedirectedError() {
		List<Message> errors = validator.getErrors();
		for (Message message : errors) {
			String errorMessage = message.getMessage();
			
			return errorMessage.contains("redirecionou");
		}
		
		return false;
	}

	private String getRedirectedUrl() {
		List<Message> errors = validator.getErrors();
		String errorMessage = errors.iterator().next().getMessage();
		
		String allChars = ".*";
		String urlGroup = "(.*)";
		String regex = allChars + "\\(" + urlGroup + "\\)" + allChars;
		String redirectedUrl = errorMessage.replaceAll(regex, "$1");
		
		return redirectedUrl;
	}
	
	public <T> T onErrorRedirectTo(T controller) {
		return validator.onErrorRedirectTo(controller);
	}
}
