/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byteslounge.property.extension;

import java.lang.annotation.Annotation;
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
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import java.lang.reflect.Method;
/**
 *
 * @author jacek
 */

public class LkdPropertyResolverBean<R> {

        private final AnnotatedMethod<? super R> prMethod;
        private final BeanManager beanManager;
        private Object prInstance;
        private List<InjectionPoint> prPoints;
        private final boolean isLocale;

        String sClass = "PropertyResolverBean::";

        public LkdPropertyResolverBean(
                        AnnotatedMethod<? super R> prMethod,
                        BeanManager beanManager) {

            String sFun = sClass + "PropertyResolverBean::";

            LkdLogger.writeToLog(sFun + "start::" );
            this.prMethod = prMethod;

            LkdLogger.writeToLog(sFun + "prMethod::" + prMethod.toString());

            this.beanManager = beanManager;
            this.isLocale = checkLocaleParameter();
            
            LkdLogger.writeToLog(sFun + "end::" );
        }

        public void initializePropertyResolverBean() {

            String sFun = sClass + "initializePropertyResolverBean::";

            LkdLogger.writeToLog(sFun + "start::" );
            
            Method javaMethod = prMethod.getJavaMember();
            
            Class<?> declaringBeansClass = javaMethod.getDeclaringClass();

            Set<Bean<?>> beans = beanManager.getBeans(declaringBeansClass);

            final Bean<?> propertyResolverBean = beanManager.resolve(beans);

            CreationalContext<?> creationalContext = beanManager
                            .createCreationalContext(propertyResolverBean);

            Class<?> declaringClass = javaMethod.getDeclaringClass();
            
            LkdLogger.logClass(declaringClass, sFun + "declaringClass");
            
            this.prInstance = beanManager.getReference(
                            propertyResolverBean, 
                            declaringClass
                            ,creationalContext);
            
            LkdLogger.logObject(prInstance, sFun + "prInstance");

            this.prPoints = new ArrayList<>();

            // We assume that the first parameter is the property to be resolved
            int startIndex = 1;
            if (this.isLocale) {
                    // If we have the additional locale property then the first
                    // couple of parameters are the locale and the property key
                    // (first is the locale; second is the property key)
                    startIndex = 2;
            }
            
            CollectInjectionPoints(
                    startIndex
                    ,   propertyResolverBean);
            
            LkdLogger.writeToLog(sFun + "end::" );
        }
        
        public void CollectInjectionPoints(
                int startIndex
                ,final Bean<?> propertyResolverBean){

            String sFun = sClass + "CollectInjectionPoints::";
            LkdLogger.writeToLog(sFun + "start::" );
            int currentIndex = 0;
            for (final AnnotatedParameter<? super R> parameter : prMethod
                            .getParameters()) {

                LkdLogger.logAnnotatedParameter(parameter, sFun +  "parameter");
                if (currentIndex++ < startIndex) {
                    continue;
                }
                
                LkdLogger.writeToLog(sFun + "injection_point_created::" );

                LkdInjectionPoint<R> ip = new LkdInjectionPoint<>(
                    parameter
                    ,this.beanManager
                    ,propertyResolverBean);

                LkdLogger.writeToLog(sFun + "injection_point_added_to_points::" );
                this.prPoints.add( ip );

            }
            
            LkdLogger.writeToLog(sFun + "end::" );
            
        }

        public String resolveProperty(
                        String key
                        , CreationalContext<?> ctx)
                        throws IllegalAccessException
                        , IllegalArgumentException,
                        InvocationTargetException {

            String sFun = sClass + "resolveProperty::";

            LkdLogger.writeToLog(sFun + "start::" );

            List<Object> parameters = new ArrayList<>();

            // If the Locale property is present, it must be the first
            // parameter in the Property Resolver method
            if (isLocale) {
                parameters.add(
                            FacesContext.getCurrentInstance()
                            .getViewRoot()
                            .getLocale());
            }

            // The property key is the next parameter
            parameters.add(key);

            // Resolve any eventual additional parameter to be injected by the
            // CDI container
            for (InjectionPoint parameter : prPoints) {              
                
                LkdLogger.logInjectionPoint(parameter, sFun + "injectionPoint");
                Object injectableRef = beanManager.getInjectableReference(
                        parameter
                        ,ctx);
                
                parameters.add(injectableRef);
                
            }
            
            Method javaMethod = prMethod.getJavaMember();
            
            String sProperty =  (String) javaMethod.invoke(
                        prInstance
                        , parameters.toArray());
            
            LkdLogger.writeToLog(sFun + "sProperty::" + sProperty);
            
            LkdLogger.writeToLog(sFun + "end::" );
            return sProperty;

        }

        public boolean checkLocaleParameter() {
            String sFun = sClass + "checkLocaleParameter::";

            LkdLogger.writeToLog(sFun + "start::" );
            boolean bret = false;
            for (Annotation[] annotations : prMethod.getJavaMember().getParameterAnnotations()) {
                    for (Annotation annotation : annotations) {
                            if (annotation.annotationType()
                                            .equals(PropertyLocale.class)) {
                                    bret =  true;
                            }
                    }
            }
            
            LkdLogger.writeToLog(sFun + "end::" + bret);
            
            return bret;
        }

}