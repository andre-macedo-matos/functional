package br.com.atf.functional.model.inspect.test.producer;

import java.io.IOException;

import javax.enterprise.inject.Produces;

import br.com.caelum.vraptor.environment.DefaultEnvironment;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.environment.EnvironmentType;

public class EnvironmentFactory {
	
	@Produces
	public Environment environmentFactory() throws IOException {
		return new DefaultEnvironment(EnvironmentType.TEST);
	}
}
