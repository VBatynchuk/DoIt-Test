package com.doitua.doittest.activity.login.di;

import com.doitua.doittest.activity.login.LoginActivity;
import com.doitua.doittest.activity.login.LoginActivityPresenter;
import com.doitua.doittest.activity.login.LoginActivityPresenterImpl;
import com.doitua.doittest.activity.login.LoginActivityView;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by batynchuk on 9/22/17.
 */
@Module
public class LoginActivityModule {

    private LoginActivity loginActivity;
    private LoginActivityView loginActivityView;

    public LoginActivityModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginActivityView = loginActivity;
    }


    @ActivityScope
    @Provides
    LoginActivityView provideLoginActivityView() {
        return loginActivityView;
    }


    @ActivityScope
    @Provides
    LoginActivityPresenter provideLoginActivityPresenter(LoginActivityView loginActivityView,
                                                         RetrofitService retrofitService,
                                                         PrefHelper prefHelper) {
        return new LoginActivityPresenterImpl(loginActivityView, retrofitService, prefHelper);
    }
}
