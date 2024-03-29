package com.installments.calculator.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractHandler<T> {

    private ApplicationContext applicationContext;

    public AbstractHandler() {
        /**
         * Gets config class to create an Application context
         */
        Class typeParameterClass = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0]);

        /**
         * Check if T has @Configuration annotation,
         * if no throws an exception
         */
        if (!typeParameterClass.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException(typeParameterClass + " is not a @Configuration class");
        }

        /**
         * Create Spring application context
         */
        applicationContext = new AnnotationConfigApplicationContext(typeParameterClass);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
