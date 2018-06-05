package br.com.atf.functional.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.atf.functional.model.NavigationElement;
import br.com.atf.functional.model.Portal;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("/inspecao")
public class InspectController {
	
	private final Result result;

	@Inject
	public InspectController(Result result) {
		super();
		this.result = result;
	}
	
	@Deprecated
	public InspectController() {
		this(null);
	}

	@Get("")
	public void inspect() {
	}
	
	@Post("")
	public void inspect(Portal portal) {
		portal.inspectElements();
		List<NavigationElement> elements = portal.getNavigationElements();
		
		result.include("elements", elements);
		result.redirectTo(this).inspect();
	}

}
