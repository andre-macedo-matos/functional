package br.com.atf.functional.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;
import br.com.atf.functional.service.PortalInspector;
import br.com.atf.functional.validator.PortalValidator;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("/inspecao")
public class InspectController {

	private Result result;
	private PortalValidator validator;
	private PortalInspector inspector;
	
	@Inject
	public InspectController(Result result, PortalValidator validator, PortalInspector inspector) {
		this.result = result;
		this.validator = validator;
		this.inspector = inspector;
	}

	@Deprecated
	public InspectController() {
		this(null, null, null);
	}

	@Get("")
	public void inspect() {
	}

	@Post("")
	public void inspect(Portal portal) {
		validator.validate(portal);
		validator.onErrorRedirectTo(this).inspect();
		
		List<NavigationElement> elements = inspector.inspectForElements(portal);

		result.include("elements", elements);
		result.redirectTo(this).inspect();
	}

}
