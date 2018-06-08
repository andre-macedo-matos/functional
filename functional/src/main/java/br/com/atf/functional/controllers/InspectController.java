package br.com.atf.functional.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.atf.functional.exception.NotReachablePageException;
import br.com.atf.functional.exception.RedirectPageException;
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

	private final Result result;
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
		validator.onErrorForwardTo(this).inspect();
		String url = portal.getUrl();
		
		try {
			portal.inspectElements();
			
		} catch (NotReachablePageException e) {
			result.include("feedback",
					"Não foi possível acessar " + portal.getUrl() + ". Por favor, verifique o endereço novamente.");
			e.printStackTrace();
			
		} catch (RedirectPageException e) {
			result.include("confirm",
					"O endereço " + url + " foi redirecionado para " + portal.getUrl()  + ". Gostaria de continuar?");
			e.printStackTrace();
		}
		
		List<NavigationElement> elements = portal.getNavigationElements();

		result.include("elements", elements);
		result.redirectTo(this).inspect();
	}

}
