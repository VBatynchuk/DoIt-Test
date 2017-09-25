package com.doitua.doittest.app;

import android.app.Application;

import com.doitua.doittest.injection.DaggerDoItAppComponent;
import com.doitua.doittest.injection.DoItAppComponent;
import com.doitua.doittest.injection.DoItAppModule;
import com.doitua.doittest.retrofit.RetrofitService;

import javax.inject.Inject;

/**
 * Created by batynchuk on 9/20/17.
 */

public class DoItApp extends Application {

    @Inject
    RetrofitService retrofitService;

    private DoItAppComponent doItAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        injectApplication();
    }

    private void injectApplication() {

        doItAppComponent = DaggerDoItAppComponent
                .builder()
                .doItAppModule(new DoItAppModule(this))
                .build();

        doItAppComponent.injectApplication(this);
    }

    public DoItAppComponent getDoItAppComponent() {
        return doItAppComponent;
    }
}
