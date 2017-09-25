package com.doitua.doittest.activity.upload;

import android.net.Uri;

import com.doitua.doittest.base.BasePresenter;

/**
 * Created by batynchuk on 9/22/17.
 */

public interface UploadImageActivityPresenter extends BasePresenter {

    void onImagePicked(Uri data);

    void onSendClick(String description, String hashTag);

    void onCoordinatesGot(float lat, float lon);

}
