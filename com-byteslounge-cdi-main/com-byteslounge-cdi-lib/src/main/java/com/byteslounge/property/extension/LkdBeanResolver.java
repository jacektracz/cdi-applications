/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byteslounge.property.extension;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import javax.enterprise.context.Dependent;
/**
 *
 * @author jacek
 */
public class LkdBeanResolver 
{
    public static <B> B getBeanClassInstance(BeanManager beanManager, Class<B> beanType, Annotation... qualifiers) {
        final B result;
        Set<Bean<?>> beans = beanManager.getBeans(beanType, qualifiers);
        if (beans.isEmpty())
            result = null;
        else {
            final Bean<B> bean = (Bean<B>) beanManager.resolve(beans);
            if (bean == null)
                result = null;
            else {
                final CreationalContext<B> cc = beanManager.createCreationalContext(bean);
                final B reference = (B) beanManager.getReference(bean, beanType, cc);
                Class<? extends Annotation> scope = bean.getScope();
                if (scope.equals(Dependent.class)) {
                    if (beanType.isInterface()) {
                        result = (B) Proxy.newProxyInstance(bean.getBeanClass().getClassLoader(), new Class<?>[] { beanType,
                                Finalizable.class }, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (method.getName().equals("finalize")) {
                                    bean.destroy(reference, cc);
                                }
                                try {
                                    return method.invoke(reference, args);
                                } catch (InvocationTargetException e) {
                                    throw e.getCause();
                                }
                            }
                        });
                    } else
                        throw new IllegalArgumentException("If the resolved bean is dependent scoped then the received beanType should be an interface in order to manage the destruction of the created dependent bean class instance.");
                } else
                    result = reference;
            }
        }
        return result;
    }
    
}
