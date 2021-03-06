package com.lkd.property.extension;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface LkdProperty {
    
    String value();
    String startTransaction();
    String endTransaction();
        
}
