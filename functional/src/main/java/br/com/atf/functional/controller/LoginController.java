package br.com.atf.functional.controller;

import javax.inject.Inject;

import br.com.atf.functional.model.User;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("/login")
public class LoginController {
	
	private final Result result;
	
	@Inject
	public LoginController(Result result) {
		this.result = result;
	}

	@Deprecated
	public LoginController() {
		this(null);
	}

	@Get("")
	public void loginForm() {
	}
	
	@Post("autentica")
	public void authenticate(User user) {
		System.out.println(user.getName() + " - " + user.getPass());
		result.redirectTo(HomeController.class).index();
	}
}
