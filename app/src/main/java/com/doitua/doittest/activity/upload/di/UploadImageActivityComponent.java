package com.doitua.doittest.activity.upload.di;

import com.doitua.doittest.activity.upload.UploadImageActivity;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.injection.DoItAppComponent;

import dagger.Component;

/**
 * Created by batynchuk on 9/22/17.
 */
@ActivityScope
@Component(dependencies = DoItAppComponent.class, modules = UploadImageActivityModule.class)
public interface UploadImageActivityComponent {

    void injectActivity(UploadImageActivity activity);

}
