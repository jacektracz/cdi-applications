package com.lkd.property.resolver;

import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;

import com.lkd.beans.OtherBean;
import com.lkd.property.extension.LkdLogger;
import com.lkd.property.extension.PropertyLocale;
import com.lkd.property.extension.PropertyResolver;

@ApplicationScoped
public class LkdProperties {

        String sClass = "Properties";
        
	@PropertyResolver
	public String resolveProperty(
                        @PropertyLocale Locale locale
                        ,   String key
                        ,   BeanManager beanManager
                        ,   OtherBean other) {
            
            String sFun = sClass + "::resolveProperty";
            String sOut = "";
            LkdLogger.writeToLog(sFun + "start::");


            if (key.equals("property.one")) {
                sOut = "ONE " + other.getOtherBeanText();
            } else if (key.equals("property.two")) {
                sOut = "TWO " + other.getOtherBeanText();
            }

            sOut = "NONE " + other.getOtherBeanText();

            LkdLogger.writeToLog(sFun + "end::");

            return sOut;
                
	}

}
