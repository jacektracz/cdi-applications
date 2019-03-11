/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byteslounge.property.extension;

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
public class LkdInjectionPoint<R> implements InjectionPoint {
    
    String sClass = "LkdInjectionPoint::";
    
    private AnnotatedParameter<? super R> parameter;
    private BeanManager beanManager;
    private  Bean<?> propertyResolverBean ;
    
    public LkdInjectionPoint(     
                AnnotatedParameter<? super R> p_parameter
                , BeanManager p_beanManager
                ,  Bean<?> p_propertyResolverBean )
    {
        String sMethod = sClass + "LkdInjectionPoint::";
        
        LkdLogger.writeToLog(sMethod + "start::");
        
        this.parameter = p_parameter;
        this.beanManager = p_beanManager;
        this.propertyResolverBean = p_propertyResolverBean;
        
        LkdLogger.writeToLog(sMethod + "end::");
        
    }
    
    @Override
    public Type getType() {
            return parameter.getBaseType();
    }

    @Override
    public Set<Annotation> getQualifiers() {
        String sMethod = sClass + "getQualifiers::";
        
        LkdLogger.writeToLog(sMethod + "start::");
        
        Set<Annotation> qualifiers = new HashSet<Annotation>();
        for (Annotation annotation : parameter.getAnnotations()) {
            if (beanManager.isQualifier(
                    annotation.annotationType())) {
                
                    qualifiers.add(annotation);
                    
            }
        }
        
        LkdLogger.writeToLog(sMethod + "end::");
        
        return qualifiers;
    }

    @Override
    public Bean<?> getBean() {
        String sMethod = sClass + "getBean::";
        
        LkdLogger.writeToLog(sMethod + "start::");
        LkdLogger.writeToLog(sMethod + "end::");
        return propertyResolverBean;
    }

    @Override
    public Member getMember() {
        String sMethod = sClass + "getMember::";
        
        LkdLogger.writeToLog(sMethod + "start::");
        LkdLogger.writeToLog(sMethod + "end::");        
        return parameter.getDeclaringCallable()
                        .getJavaMember();
    }

    @Override
    public Annotated getAnnotated() {
        String sMethod = sClass + "getAnnotated::";        
        LkdLogger.writeToLog(sMethod + "start::");
        LkdLogger.writeToLog(sMethod + "end::");
        return parameter;
    }

    @Override
    public boolean isDelegate() {
            return false;
    }

    @Override
    public boolean isTransient() {
            return false;
    }

}
