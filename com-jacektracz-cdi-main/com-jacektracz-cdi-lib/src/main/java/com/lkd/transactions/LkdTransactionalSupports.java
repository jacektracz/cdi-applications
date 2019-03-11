package com.lkd.transactions;

import com.lkd.property.extension.*;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface LkdTransactionalSupports {
    
    String transactionName();
    String startTransaction();
    String endTransaction();
        
}
