package br.com.atf.functional.controllers;

import br.com.atf.functional.model.Portal;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;

@Controller
@Path("/inspecao")
public class InspectController {
	
	@Get("")
	public void inspect() {
	}
	
	@Post("")
	public void inspect(Portal portal) {
		System.out.println(portal.getUrl());
	}

}
