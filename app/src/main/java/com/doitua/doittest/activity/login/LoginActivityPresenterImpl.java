package com.doitua.doittest.activity.login;

import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.SchedulerHelper;

/**
 * Created by batynchuk on 9/22/17.
 */

public class LoginActivityPresenterImpl implements LoginActivityPresenter {

    private LoginActivityView mLoginActivityView;
    private RetrofitService mRetrofitService;
    private PrefHelper mPrefHelper;
    private SchedulerHelper mSchedulerHelper;


    public LoginActivityPresenterImpl(LoginActivityView loginActivityView, RetrofitService retrofitService, PrefHelper prefHelper,
                                      SchedulerHelper schedulerHelper) {
        mLoginActivityView = loginActivityView;
        mRetrofitService = retrofitService;
        mPrefHelper = prefHelper;
        mSchedulerHelper = schedulerHelper;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onLoginClick(String email, String password) {
        mRetrofitService.login(email, password)
                .subscribeOn(mSchedulerHelper.getWorkerScheduler())
                .observeOn(mSchedulerHelper.getMainScheduler())
                .subscribe(loginResponse -> {
                    mPrefHelper.setToken(loginResponse.getToken());
                    mLoginActivityView.startFeedActivity();
                }, t -> mLoginActivityView.showError());

    }
}
