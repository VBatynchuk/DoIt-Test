package com.doitua.doittest.activity.upload;

import com.doitua.doittest.base.BaseView;

import java.io.File;

/**
 * Created by batynchuk on 9/22/17.
 */

public interface UploadImageActivityView extends BaseView {

    void setImage(File file);

    void getLastKnownCoordinates();

    void showNoFileSelected();

    void showImageLocationRequired();

    void setSuccessfulResult();

    void showToSmallImage();

    void showUploading();

    void hildeUploading();

}
