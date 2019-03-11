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
public class LkdResolverCreator<R>{
    
    String sClass = "LkdInjectionPoint::";
    private LkdPropertyResolverBean prb;
    private BeanManager beanManager;
    
    public LkdPropertyResolverBean getPrb(){
        String sFun = sClass + "::getPrb::";
        LkdLogger.writeToLog(sFun + "start::");
        LkdLogger.writeToLog(sFun + "end::");
        return this.prb;
    }
    
    public LkdResolverCreator(BeanManager pbeanManager ){
        String sFun = sClass + "::LkdInjectedBeansFactoryCreator::";
        LkdLogger.writeToLog(sFun + "start::");
        
        this.beanManager = pbeanManager;                
        
        LkdLogger.writeToLog(sFun + "end::");
    }
    
    public  void createPropertyResolverBean(AnnotatedType<R> at)
    {
        
        String sFun = sClass + "::createPropertyResolverBean::";
        LkdLogger.writeToLog(sFun + "start::");
        
        for (AnnotatedMethod<? super R> method : at.getMethods()) {            
            LkdLogger.logAnnotatedMethod(
                    method
                    , sFun + "check_method_is_annotated");
            if (method.isAnnotationPresent(PropertyResolver.class)) {

                LkdLogger.logAnnotatedMethod(method
                        , sFun + "method_is_annotated_PropertyResolver");
                
                LkdLogger.writeToLog(sFun + "LkdPropertyResolverBean_create_start::");                
                
                this.prb = new LkdPropertyResolverBean(
                                method
                                , this.beanManager);                
                LkdLogger.writeToLog(sFun + "LkdPropertyResolverBean_create_end::");                
                break;                
            }
        }
        
        LkdLogger.writeToLog(sFun + "end::");
        
    }
    
    
   
}
