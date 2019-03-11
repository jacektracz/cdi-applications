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
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.faces.context.FacesContext;

/**
 *
 * @author jacek
 */
    
public class LkdAnnotationsLogger<X> {
    
     public void  logAnnotatetType(AnnotatedType<X>  at, String sFun){
        LkdLogger.writeToLog(sFun + "start");
        LkdLogger.writeToLog(sFun + "AnnotatedType_to_string::" + at.toString());

        if(at.getClass()!= null){
            LkdLogger.writeToLog(sFun + "AnnotatedType::" + at.getClass().getName());
        }
        
        LkdLogger.writeToLog(sFun + "end");
        
    }
   
}
