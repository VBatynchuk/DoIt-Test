package com.doitua.doittest.activity.feed.di;

import com.doitua.doittest.activity.feed.ImagesFeedActivity;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.injection.DoItAppComponent;

import dagger.Component;

/**
 * Created by batynchuk on 9/21/17.
 */
@ActivityScope
@Component(dependencies = DoItAppComponent.class, modules = ImagesFeedActivityModule.class)
public interface ImagesFeedActivityComponent {

    void injectActivity(ImagesFeedActivity activity);

}
