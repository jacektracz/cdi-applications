/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byteslounge.property.extension;
import java.io.Console;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.enterprise.inject.spi.ProcessBean;
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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
/**
 *
 * @author jacek
 */
public class LkdLogger {
 
    public static void writeToLog(String plogitem){
                
        try{
            FileWriter fw = new FileWriter("C:\\lkd\\ht\\apps_jee_cdi\\src\\app\\logs\\outputfile.txt",true);
            PrintWriter out = new PrintWriter(fw); 
            out.println(plogitem);
            out.close();
        }catch (Exception ex){
            
        }
    }
    
    public static void writeToLogException(String plogitem, Exception pex){
                
        try{
            writeToLog(pex.getMessage());
            writeToLog(pex.getStackTrace().toString());
            writeToLog(plogitem);
        }catch (Exception ex){
            
        }
    }
        
    public LkdLogger()
    {
            try 
            {  
            }
            catch (Exception e) 
            {                  
            }                  
    }    
    
    public static <T> void  logProcessAnnotatedType(ProcessAnnotatedType<T>  at, String psFun){
        
        String sFun = psFun + "::logProcessAnnotatetType::";
        
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.logObject(at, psFun);
        
        LkdLogger.writeToLog(sFun + "log::end");
        
    }
    
    public static <T> void  logAnnotatedMethod(AnnotatedMethod<T>  at, String psFun){
        
        String sFun = psFun + "::logAnnotatedMethod::";
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.logObject(at, psFun);
        
        logMethod(at.getJavaMember(), sFun);
        
        LkdLogger.writeToLog(sFun + "log::end");
    }
    
    public static <T> void  logAnnotatedParameter(
            AnnotatedParameter<T>  at
            , String psFun){
        
        String sFun = psFun + "::logAnnotatedParameter::";
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.logObject(at, psFun);

        LkdLogger.writeToLog(sFun + "log::end");
    }
        
    public static <T> void  logProcessInjectionTarget(ProcessInjectionTarget<T>  at, String psFun){
        
        String sFun = psFun + "::logProcessInjectionTarget::";
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.logObject(at, psFun);        
        LkdLogger.writeToLog(sFun + "log::end");
    }
    
    public static void logSessionScopedBeans(BeanManager beanManager , String sFunParent){
        String sFun = sFunParent + "::logSessionScopedBeans::";
        try{
            LkdLogger.writeToLog(sFun + "start::" );

            LkdLogger.writeToLog(sFun + "SessionScoped::" );
            LkdLogger.writeToLog(sFun + "end::" );
        }catch(Exception ex){
            LkdLogger.writeToLogException(sFun + "exception::" ,ex);    
        }                    
    }

    public static void  logClass(Class<?>  at, String psFun){
        
        String sFun = psFun + "::logClass::";
        LkdLogger.writeToLog(sFun + "log::start");        
        LkdLogger.logObject(at, psFun);        
        LkdLogger.writeToLog(sFun + "getName::" + at.getName());
        LkdLogger.writeToLog(sFun + "getSimpleName::" + at.getSimpleName());    
        
        LkdLogger.writeToLog(sFun + "log::end");
    }
    
    public static void  logObject(Object  at, String psFun){
        
        String sFun = psFun + "::logObject::";
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.writeToLog(sFun + "to-string" + at.toString());
        Class<?> atclass = at.getClass();
        if(atclass!= null){
            LkdLogger.writeToLog(sFun + "at-class::getCanonicalName:" +atclass.getCanonicalName());
            LkdLogger.writeToLog(sFun + "at-class::getName:" +atclass.getName());
            LkdLogger.writeToLog(sFun + "at-class::getTypeName:" +atclass.getTypeName());
            LkdLogger.writeToLog(sFun + "at-class::toGenericString:" +atclass.toGenericString());
        }
        LkdLogger.writeToLog(sFun + "log::end");
    }
        
    public static void  logInjectionPoint(InjectionPoint  at, String psFun){
        
        String sFun = psFun + "::logInjectionPoint::";
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.logObject(at,psFun);
        LkdLogger.writeToLog(sFun + "log::end");
    }
    
    public static void  logMethod(Method  at, String psFun){
        
        String sFun = psFun + "::logMethod::";
        LkdLogger.writeToLog(sFun + "log::start");
        LkdLogger.logObject(at, psFun);                
        LkdLogger.writeToLog(sFun + "getName::" + at.getName());
        LkdLogger.writeToLog(sFun + "toGenericString::" + at.toGenericString());        
        LkdLogger.writeToLog(sFun + "log::end");
    }
    
    public static <X>void  logAnnotatedType(AnnotatedType<X>  at, String psFun){
        String sFun = psFun + "::logAnnotatedType::";
        LkdLogger.writeToLog(sFun + "start");        
        LkdLogger.logObject(at, psFun);                
        LkdLogger.writeToLog(sFun + "getJavaClass::" + at.getJavaClass().getName());                
        logClass(at.getJavaClass(),sFun);                
        LkdLogger.writeToLog(sFun + "end");
        
    }
    
    public static <X>void  logBaseType(Type  at, String psFun){
        
        String sFun = psFun + "::logBaseType::";
        LkdLogger.writeToLog(sFun + "start");        
        LkdLogger.logObject(at,psFun);
        LkdLogger.writeToLog(sFun + "getTypeName::" + at.getTypeName());                
        LkdLogger.writeToLog(sFun + "end");
        
    }
    public static <X> void logCreationalContext(CreationalContext<X>  at, String psFun){
        String sFun = psFun + "::logCreationalContext::";
        LkdLogger.writeToLog(sFun + "start");        
        
        LkdLogger.logObject(at, psFun);                        
        
        
        LkdLogger.writeToLog(sFun + "end");
    }
   
}
