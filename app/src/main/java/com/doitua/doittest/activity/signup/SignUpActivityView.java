package com.doitua.doittest.activity.signup;

import com.doitua.doittest.base.BaseView;

import java.io.File;

/**
 * Created by batynchuk on 9/20/17.
 */

public interface SignUpActivityView extends BaseView {

    void showError();

    void startFeedActivity();

    void setImage(File file);

}
