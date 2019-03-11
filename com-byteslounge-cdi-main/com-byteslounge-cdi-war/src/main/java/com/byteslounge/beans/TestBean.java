package com.byteslounge.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.byteslounge.property.extension.Property;
import com.byteslounge.property.extension.LkdLogger;
@Named
@RequestScoped
public class TestBean {

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
