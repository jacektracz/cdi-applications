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
import javax.enterprise.context.SessionScoped;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.faces.context.FacesContext;
import javax.enterprise.context.spi.Context;

public class PropertyExtension<R> implements Extension {

    String sClass = "PropertyExtension::";

    private LkdPropertyResolverBean propertyResolverBean;

    void processAnnotatedType(
                @Observes ProcessAnnotatedType<R> pat
                , BeanManager beanManager) {

        String sFun = sClass + "::processAnnotatedType::";
        LkdLogger.writeToLog(sFun + "start::");

        LkdLogger.logSessionScopedBeans(beanManager, sFun);             
        LkdLogger.logProcessAnnotatedType(pat, sFun);
        
        AnnotatedType<R> at = pat.getAnnotatedType();

        LkdLogger.logAnnotatedType(at, sFun);

        for (AnnotatedMethod<? super R> method : at.getMethods()) {

            LkdLogger.logAnnotatedMethod(method
                    , sFun + "check_method_is_annotated");

            if (method.isAnnotationPresent(PropertyResolver.class)) {

                LkdLogger.logAnnotatedMethod(method
                        , sFun + "method_is_annotated_PropertyResolver");

                LkdLogger.writeToLog(sFun + "LkdPropertyResolverBean_create_start::");
                
                this.propertyResolverBean = new LkdPropertyResolverBean(
                                method
                                , beanManager);
                
                LkdLogger.writeToLog(sFun + "LkdPropertyResolverBean_create_end::");
                
                
                break;
                
            }
        }
        
        LkdLogger.writeToLog(sFun + "end::");

    }
        
        
	void AfterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
                LkdLogger.writeToLog("AfterDeploymentValidation::start::");
		this.propertyResolverBean.initializePropertyResolverBean();
                LkdLogger.writeToLog("AfterDeploymentValidation::end::");
	}

	<X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {
            
                String sFun = sClass + "::processInjectionTarget::";
                LkdLogger.writeToLog(sFun + "start::");
                
                LkdLogger.logProcessInjectionTarget(pit, sFun );
                LkdInjectionTarget<X> lkdwrapper = new LkdInjectionTarget<X>(
                        this.propertyResolverBean
                        ,   pit) ;
                
		pit.setInjectionTarget( lkdwrapper );
                LkdLogger.writeToLog("processInjectionTarget::end::");
	}
}
