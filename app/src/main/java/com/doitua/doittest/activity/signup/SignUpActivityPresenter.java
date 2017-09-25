package com.doitua.doittest.activity.signup;

import android.net.Uri;

import com.doitua.doittest.base.BasePresenter;

/**
 * Created by batynchuk on 9/20/17.
 */

public interface SignUpActivityPresenter extends BasePresenter {

    void onCreated();

    void onImagePicked(Uri data);

    void onSendClick(String userName, String email, String password);

}
