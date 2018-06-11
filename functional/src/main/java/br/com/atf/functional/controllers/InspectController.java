package br.com.atf.functional.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("/inspecao")
public class InspectController {

	private Result result;
	private Validator validator;

	@Inject
	public InspectController(Result result, Validator validator) {
		this.result = result;
		this.validator = validator;
	}

	@Deprecated
	public InspectController() {
		this(null, null);
	}

	@Get("")
	public void inspect() {
	}

	@Post("")
	public void inspect(@Valid Portal portal) {
		validator.onErrorRedirectTo(this).inspect();
		
		List<NavigationElement> elements = portal.inspectElements();

		result.include("elements", elements);
		result.redirectTo(this).inspect();
	}

}
