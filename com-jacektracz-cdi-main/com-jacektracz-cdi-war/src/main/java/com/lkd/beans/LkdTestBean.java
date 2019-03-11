package com.lkd.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lkd.property.extension.Property;
import com.lkd.property.extension.LkdLogger;
@Named
@RequestScoped
public class LkdTestBean {

	@Property("property.one")
	private String text;
        
	@Property("property.two")
	private String textTwo;

	public String getText() {
		return text + ":modified";
	}
        //http://localhost:8080/com-byteslounge-cdi-war/home.xhtml
	public String getTextTwo() {
            LkdLogger.writeToLog("getTextTwo::start");
            return textTwo;
	}

}
