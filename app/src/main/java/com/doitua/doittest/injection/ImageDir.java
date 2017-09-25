package com.doitua.doittest.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by batynchuk on 9/23/17.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageDir {
}
