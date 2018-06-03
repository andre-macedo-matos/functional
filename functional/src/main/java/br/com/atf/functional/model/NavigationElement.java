package br.com.atf.functional.model;

import java.util.Map;

public class NavigationElement {

	private String tagName;
	private Map<String, String> attributes;

	public NavigationElement(String tagName, Map<String, String> attributes) {
		this.tagName = tagName;
		this.attributes = attributes;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

}
