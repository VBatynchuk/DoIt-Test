package com.doitua.doittest.injection;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import com.doitua.doittest.app.DoItApp;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by batynchuk on 9/20/17.
 */
@Singleton
@Component(modules = DoItAppModule.class)
public interface DoItAppComponent {

    void injectApplication(DoItApp doItApp);

    @ApplicationContext
    Context applicationContext();

    RetrofitService retrofitService();

    ContentResolver contentResolver();

    @ImageDir
    File imageDir();

    SharedPreferences sharedPreference();

    PrefHelper prefToken();

}
