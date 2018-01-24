package com.doitua.doittest.activity.feed;

import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.model.image.ImageResponseList;
import com.doitua.doittest.retrofit.RetrofitService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by batynchuk on 9/21/17.
 */

public class ImagesFeedActivityPresenterIpml implements ImagesFeedActivityPresenter {

    private final RetrofitService mRetrofitService;
    private final ImagesFeedActivityView mImagesFeedActivityView;
    private final PrefHelper mPrefHelper;

    private Single<ImageResponseList> mResponseObservable;
    private Consumer<ImageResponseList> mResponseConsumer;

    public ImagesFeedActivityPresenterIpml(ImagesFeedActivityView imagesFeedActivityView,
                                           RetrofitService retrofitService,
                                           PrefHelper prefHelper) {
        mRetrofitService = retrofitService;
        mImagesFeedActivityView = imagesFeedActivityView;
        mPrefHelper = prefHelper;
    }

    @Override
    public void onCreated() {

        mImagesFeedActivityView.showProgress();

        mResponseObservable = mRetrofitService.getAllImages(mPrefHelper.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        mResponseConsumer = imageModelResponse -> {
            mImagesFeedActivityView.setItems(imageModelResponse.getImageModelList());
            mImagesFeedActivityView.hideProgress();
        };

        mResponseObservable.subscribe(mResponseConsumer);

    }

    @Override
    public void onUpdate() {
        mResponseObservable.subscribe(mResponseConsumer);
    }


    @Override
    public void onPlayGifClicked() {

        mImagesFeedActivityView.showGifProgressDialog();

        mRetrofitService.getGif(mPrefHelper.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBodyResponse ->
                        mImagesFeedActivityView.playGif(responseBodyResponse.getGifUrl()));
    }

}
