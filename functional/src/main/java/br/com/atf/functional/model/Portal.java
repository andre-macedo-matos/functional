package br.com.atf.functional.model;

import javax.enterprise.context.RequestScoped;
import javax.validation.GroupSequence;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.atf.functional.annotation.NotFound;
import br.com.atf.functional.annotation.Redirected;
import br.com.atf.functional.group.HibernateGroup;
import br.com.atf.functional.group.UrlGroup;

@RequestScoped
@GroupSequence({Portal.class, HibernateGroup.class, UrlGroup.class})
public class Portal {
	
	@NotEmpty(message = "{portal.url.empty}", groups = HibernateGroup.class)
	@NotFound(groups = UrlGroup.class)
	@Redirected(groups = UrlGroup.class)
	private String url;

	public Portal(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
