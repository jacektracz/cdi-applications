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
public class LkdInjectionTarget<X> implements InjectionTarget<X> {
    
        String sClass = "LkdInjectionTarget::";
        
        private LkdPropertyResolverBean prb;
        ProcessInjectionTarget<X> processInjectionTarget;
        public InjectionTarget<X> injectionTarget;
        public AnnotatedType<X> annotatedType ;
    
        public LkdInjectionTarget(
                LkdPropertyResolverBean pbean
                ,   ProcessInjectionTarget<X> p_pit)
        {            
            String sMethod = sClass + "LkdInjectionTarget::";
            LkdLogger.writeToLog(sMethod + "start::");
            
            this.prb = pbean;
            this.processInjectionTarget = p_pit;
            this.injectionTarget = processInjectionTarget.getInjectionTarget();
            this.annotatedType = processInjectionTarget.getAnnotatedType();  
            
            LkdLogger.writeToLog(sMethod + "injectionTarget" + this.processInjectionTarget.toString());
            
            LkdLogger.writeToLog(sMethod + "end:");
        }
        
        @Override
        public X produce(CreationalContext<X> ctx) {
                String sMethod = sClass + "produce::";
                LkdLogger.writeToLog(sMethod + "start::");            
            
                return injectionTarget.produce(ctx);
        }

        @Override
        public void dispose(X instance) {
            String sMethod = sClass + "dispose::";
            LkdLogger.writeToLog(sMethod + "start::");            
            injectionTarget.dispose(instance);
            LkdLogger.writeToLog(sMethod + "end::");            
        }

        @Override
        public Set<InjectionPoint> getInjectionPoints() {
            String sMethod = sClass + "getInjectionPoints::";
            LkdLogger.writeToLog(sMethod + "start::");                        
            Set<InjectionPoint> ips = getInjectionTarget().getInjectionPoints();
            for( InjectionPoint ip :ips){
                
                if(ip.getClass() != null){
                    LkdLogger.writeToLog( "injection_points_class:" + ip.getClass().getName());                    
                }
                if(ip.getBean() != null){
                    LkdLogger.writeToLog( "injection_points_class:" + ip.getBean().getName());
                }
                if(ip.getMember() != null){
                    LkdLogger.writeToLog( "injection_points_getmember:" + ip.getMember().getName());
                }
            }
            LkdLogger.writeToLog("getInjectionPoints::end::");
            return ips;
        }

        // The container calls inject() method when injectionTarget's performing field
        // injection and calling bean initializer methods.
        // Our custom wrapper will also check for fields annotated with
        // @Property and resolve them by invoking the Property Resolver
        // method
        
        @Override
        public void inject(X instance, CreationalContext<X> ctx) {
            
            String sMethod = sClass + "inject::";                
            LkdLogger.writeToLog(sMethod + "start::");
            LkdLogger.logCreationalContext(ctx, sMethod); 
            
            injectionTarget.inject(instance, ctx);

            for (Field field : annotatedType.getJavaClass().getDeclaredFields()) {

                LkdLogger.writeToLog("field_for_injecter__start::"); 
                LkdFieldInjecter<X> injecter = new LkdFieldInjecter<X>(
                        this.prb);
                injecter.injectField(instance, field, ctx);                        
                LkdLogger.writeToLog("field_for_injecter__end::" );                        
            }
            
            LkdLogger.writeToLog("inject::end::");
        }

        @Override
        public void postConstruct(X instance) {
                injectionTarget.postConstruct(instance);
        }

        @Override
        public void preDestroy(X instance) {
                injectionTarget.preDestroy(instance);
        }
        private InjectionTarget<X> getInjectionTarget(){
            return injectionTarget;
        }
}

