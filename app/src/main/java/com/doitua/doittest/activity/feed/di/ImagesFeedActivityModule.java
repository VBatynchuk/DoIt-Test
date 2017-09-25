package com.doitua.doittest.activity.feed.di;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.doitua.doittest.R;
import com.doitua.doittest.activity.feed.ImagesFeedActivity;
import com.doitua.doittest.activity.feed.ImagesFeedActivityPresenter;
import com.doitua.doittest.activity.feed.ImagesFeedActivityPresenterIpml;
import com.doitua.doittest.activity.feed.ImagesFeedActivityView;
import com.doitua.doittest.injection.ActivityContext;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by batynchuk on 9/21/17.
 */
@Module
public class ImagesFeedActivityModule {

    private final ImagesFeedActivity imagesFeedActivity;
    private final ImagesFeedActivityView imagesFeedActivityView;

    public ImagesFeedActivityModule(ImagesFeedActivity imagesFeedActivity) {
        this.imagesFeedActivity = imagesFeedActivity;
        this.imagesFeedActivityView = imagesFeedActivity;
    }

    @ActivityContext
    @Provides
    Context provideContext() {
        return imagesFeedActivity;
    }

    @ActivityScope
    @Provides
    ImagesFeedActivityView provideImagesFeedActivityView() {
        return imagesFeedActivityView;
    }

    @ActivityScope
    @Provides
    Dialog provideGifDialog(@ActivityContext Context context) {
        Dialog gifDialog = new Dialog(context);
        gifDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return gifDialog;
    }

    @ActivityScope
    @Provides
    ImagesFeedActivityPresenter provideImagesFeedActivityPresenter(ImagesFeedActivityView imagesFeedActivityView,
                                                                   RetrofitService retrofitService,
                                                                   PrefHelper prefHelper) {
        return new ImagesFeedActivityPresenterIpml(imagesFeedActivityView, retrofitService,
                prefHelper);
    }
}
