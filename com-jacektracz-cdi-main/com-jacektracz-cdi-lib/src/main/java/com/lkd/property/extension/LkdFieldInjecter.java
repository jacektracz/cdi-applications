/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lkd.property.extension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.faces.context.FacesContext;

/**
 *
 * @author jacek
 */
public class LkdFieldInjecter<X>  {
    
        String sClass = "LkdFieldInjecter::";
        private LkdPropertyResolverBean prb;
        
        public LkdFieldInjecter(LkdPropertyResolverBean p ){
            
            prb = p;
            
        }
        
        public void injectField(X instance, Field field, CreationalContext<X> ctx) {
            
            String sMethod = sClass + "injectField::";

            LkdLogger.writeToLog(sMethod + "field::" + field.getName());

            Property annotation = field.getAnnotation(Property.class);
            
            LkdLogger.logAnnotation(annotation, sMethod);
            
            if (annotation != null) {

                    String key = annotation.value();
                    
                    LkdLogger.writeToLog("annotation::key::" + key);
                                        
                    field.setAccessible(true);

                    try {

                            String sprbean =  this.prb.resolveProperty(key, ctx);
                            
                            LkdLogger.writeToLog("annotation::resolvesProperty::" + sprbean);
                            String fieldInstance = instance.toString();

                            LkdLogger.writeToLog("annotation::firldInstance::" + fieldInstance);
                            
                            field.set(instance, sprbean);
                            

                    } catch (Exception ex){
                        LkdLogger.writeToLogException(sMethod, ex);
                    }
            }
            LkdLogger.writeToLog(sMethod  + "end::");
        }
}

