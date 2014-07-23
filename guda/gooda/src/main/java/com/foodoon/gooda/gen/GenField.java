package com.foodoon.gooda.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by foodoon on 2014/6/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenField {

    String cn();

    int order();

    boolean inSearchForm();

    boolean canNull();
}
