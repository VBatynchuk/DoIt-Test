package com.doitua.doittest.activity.login.di;

import com.doitua.doittest.activity.login.LoginActivity;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.injection.DoItAppComponent;

import dagger.Component;

/**
 * Created by batynchuk on 9/22/17.
 */
@ActivityScope
@Component(dependencies = DoItAppComponent.class, modules = LoginActivityModule.class)
public interface LoginActivityComponent {

    void injectActivity(LoginActivity activity);

}
