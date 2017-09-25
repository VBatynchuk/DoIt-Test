package com.doitua.doittest.activity.signup.di;

import com.doitua.doittest.activity.signup.SignUpActivity;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.injection.DoItAppComponent;

import dagger.Component;

/**
 * Created by batynchuk on 9/21/17.
 */
@ActivityScope
@Component(dependencies = DoItAppComponent.class, modules = SignUpActivityModule.class)
public interface SignUpActivityComponent {

    void injectActivity(SignUpActivity activity);

}
