package com.doitua.doittest.activity.upload.di;

import android.content.ContentResolver;
import android.content.Context;

import com.doitua.doittest.activity.upload.UploadImageActivity;
import com.doitua.doittest.activity.upload.UploadImageActivityPresenter;
import com.doitua.doittest.activity.upload.UploadImageActivityPresenterImpl;
import com.doitua.doittest.activity.upload.UploadImageActivityView;
import com.doitua.doittest.injection.ActivityContext;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.injection.CacheDir;
import com.doitua.doittest.injection.ImageDir;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.PermissionChecker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;

import dagger.Module;
import dagger.Provides;

/**
 * Created by batynchuk on 9/22/17.
 */
@Module
public class UploadImageActivityModule {

    private final UploadImageActivityView uploadImageActivityView;
    private final UploadImageActivity uploadImageActivity;

    public UploadImageActivityModule(UploadImageActivity uploadImageActivity) {
        this.uploadImageActivityView = uploadImageActivity;
        this.uploadImageActivity = uploadImageActivity;
    }

    @ActivityContext
    @Provides
    Context provideContext() {
        return uploadImageActivity;
    }

    @ActivityScope
    @Provides
    UploadImageActivityView provideUploadImageActivityView() {
        return uploadImageActivityView;
    }

    @CacheDir
    @ActivityScope
    @Provides
    File provideCacheDir(@ActivityContext Context context) {
        return context.getCacheDir();
    }

    @ActivityScope
    @Provides
    PermissionChecker providePermissionChecker(@ActivityContext Context context) {
        return new PermissionChecker(context);
    }

    @ActivityScope
    @Provides
    FusedLocationProviderClient provideFusedLocationProviderClient(@ActivityContext Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @ActivityScope
    @Provides
    UploadImageActivityPresenter provideUploadImageActivityPresenter(UploadImageActivityView uploadImageActivityView,
                                                                     RetrofitService retrofitService,
                                                                     PrefHelper prefHelper,
                                                                     ContentResolver contentResolver,
                                                                     @CacheDir File cacheDir) {
        return new UploadImageActivityPresenterImpl(uploadImageActivityView,
                retrofitService, prefHelper, contentResolver, cacheDir);
    }
}
