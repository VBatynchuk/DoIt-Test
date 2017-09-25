package com.doitua.doittest.activity.login;

import android.util.Log;

import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by batynchuk on 9/22/17.
 */

public class LoginActivityPresenterImpl implements LoginActivityPresenter {

    private LoginActivityView mLoginActivityView;
    private RetrofitService mRetrofitService;
    private PrefHelper mPrefHelper;


    public LoginActivityPresenterImpl(LoginActivityView loginActivityView, RetrofitService retrofitService, PrefHelper prefHelper) {
        mLoginActivityView = loginActivityView;
        mRetrofitService = retrofitService;
        mPrefHelper = prefHelper;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onLoginClick(String email, String password) {
        mRetrofitService.login(email, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse.isSuccessful()){
                        mPrefHelper.setToken(loginResponse.body().getToken());
                        mLoginActivityView.startFeedActivity();
                    }
                    Log.e("TAG", "onLoginClick: " + loginResponse.body());
                });

    }
}
