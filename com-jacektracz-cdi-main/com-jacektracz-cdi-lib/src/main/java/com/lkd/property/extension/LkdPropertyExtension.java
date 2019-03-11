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

public class LkdPropertyExtension<R> implements Extension {

    String sClass = "PropertyExtension::";

    private LkdPropertyResolverBean prb;
    private BeanManager beanManager;
            
    void processAnnotatedType(
                @Observes ProcessAnnotatedType<R> pat
                , BeanManager pbeanManager) {

        String sFun = sClass + "::processAnnotatedType::";
        LkdLogger.writeToLog(sFun + "start::");
        
        this.beanManager = pbeanManager;
        
        LkdLogger.logSessionScopedBeans(this.beanManager, sFun);             
        
        LkdLogger.logProcessAnnotatedType(pat, sFun);
        
        LkdLogger.writeToLog(sFun + "AnnotatedType__extracted__start::");
        
        AnnotatedType<R> at = pat.getAnnotatedType();
        
        LkdLogger.writeToLog(sFun + "AnnotatedType__extracted__end::");
        
        LkdLogger.logAnnotatedType( at, sFun);
        
        LkdResolverCreator<R> prFactory = new LkdResolverCreator<>(
                this.beanManager);
        
        prFactory.createPropertyResolverBean(at);
        this.prb = prFactory.getPrb();
        
        LkdLogger.writeToLog(sFun + "end::");

    }
        
    void AfterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
        LkdLogger.writeToLog("AfterDeploymentValidation::start::");
        this.prb.initializePropertyResolverBean();
        LkdLogger.writeToLog("AfterDeploymentValidation::end::");
    }

    <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {

        String sFun = sClass + "::processInjectionTarget::";
        LkdLogger.writeToLog(sFun + "start::");

        LkdLogger.logProcessInjectionTarget(pit, sFun );
        
        LkdInjectionTarget<X> lkdwrapper = new LkdInjectionTarget<X>(
                this.prb
                ,   pit) ;

        pit.setInjectionTarget( lkdwrapper );
        
        LkdLogger.writeToLog("processInjectionTarget::end::");
    }
    
}
