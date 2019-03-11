package com.byteslounge.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class OtherBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String otherBeanText;

	@PostConstruct
	private void init() {
		otherBeanText = "other text";
	}

	public String getOtherBeanText() {
		return otherBeanText;
	}

}
