package com.doitua.doittest.activity.login;

import com.doitua.doittest.base.BasePresenter;

/**
 * Created by batynchuk on 9/22/17.
 */

public interface LoginActivityPresenter extends BasePresenter {

    void onCreate();

    void onLoginClick(String email, String password);

}
