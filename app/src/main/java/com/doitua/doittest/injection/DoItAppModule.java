package com.doitua.doittest.injection;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.doitua.doittest.app.DoItApp;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitBuilder;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.Constants;
import com.doitua.doittest.util.SchedulerHelper;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by batynchuk on 9/20/17.
 */
@Module
public class DoItAppModule {

    private final DoItApp doItApp;

    public DoItAppModule(DoItApp doItApp) {
        this.doItApp = doItApp;
    }

    @Provides
    DoItApp provideDoitApp() {
        return doItApp;
    }

    @ApplicationContext
    @Provides
    Context provideContext() {
        return doItApp;
    }

    @Singleton
    @Provides
    RetrofitBuilder provideRetrofitBuilder() {
        return new RetrofitBuilder();
    }

    @Singleton
    @Provides
    RetrofitService provideRetrofitService(RetrofitBuilder retrofitBuilder) {
        return retrofitBuilder.build().create(RetrofitService.class);
    }

    @Singleton
    @Provides
    ContentResolver provideContentResolver(@ApplicationContext Context context) {
        return context.getContentResolver();
    }

    @ImageDir
    @Singleton
    @Provides
    File provideImageDir(@ApplicationContext Context context) {
        return new File(context.getFilesDir(), Constants.IMAGE_DIRECTORY_NAME);
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    PrefHelper providePrefToken(SharedPreferences sharedPreferences) {
        return new PrefHelper(sharedPreferences);
    }

    @Singleton
    @Provides
    SchedulerHelper provideSchedulerHelper(){
        return new SchedulerHelper(Schedulers.io(), AndroidSchedulers.mainThread());
    }

}
