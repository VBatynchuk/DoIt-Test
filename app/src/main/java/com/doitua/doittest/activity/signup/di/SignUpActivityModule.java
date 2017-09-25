package com.doitua.doittest.activity.signup.di;

import android.content.ContentResolver;
import android.content.Context;

import com.doitua.doittest.activity.signup.SignUpActivity;
import com.doitua.doittest.activity.signup.SignUpActivityPresenter;
import com.doitua.doittest.activity.signup.SignUpActivityView;
import com.doitua.doittest.activity.signup.SignUpActivityPresenterImpl;
import com.doitua.doittest.injection.ActivityContext;
import com.doitua.doittest.injection.ActivityScope;
import com.doitua.doittest.injection.ImageDir;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.PermissionChecker;

import java.io.File;

import dagger.Module;
import dagger.Provides;

/**
 * Created by batynchuk on 9/21/17.
 */
@Module
public class SignUpActivityModule {

    private final SignUpActivity signUpActivity;
    private final SignUpActivityView signUpActivityView;

    public SignUpActivityModule(SignUpActivity activity) {
        signUpActivity = activity;
        signUpActivityView = activity;
    }

    @ActivityContext
    @Provides
    Context provideContext() {
        return signUpActivity;
    }

    @ActivityScope
    @Provides
    SignUpActivityView provideLoginActivityView() {
        return signUpActivityView;
    }


    @ActivityScope
    @Provides
    PermissionChecker providePermissionChecker(@ActivityContext Context context) {
        return new PermissionChecker(context);
    }

    @ActivityScope
    @Provides
    SignUpActivityPresenter provideSignUpActivityPresenter(SignUpActivityView signUpActivityView,
                                                           RetrofitService retrofitService,
                                                           ContentResolver contentResolver,
                                                           @ImageDir File imageDir,
                                                           PrefHelper prefHelper) {
        return new SignUpActivityPresenterImpl(signUpActivityView, retrofitService,
                contentResolver, imageDir, prefHelper);
    }

}
