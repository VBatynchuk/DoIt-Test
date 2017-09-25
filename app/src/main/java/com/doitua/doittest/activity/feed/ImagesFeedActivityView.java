package com.doitua.doittest.activity.feed;

import com.doitua.doittest.base.BaseView;
import com.doitua.doittest.model.image.ImageModel;

import java.util.List;

/**
 * Created by batynchuk on 9/21/17.
 */

public interface ImagesFeedActivityView extends BaseView {

    void showProgress();

    void hideProgress();

    void setItems(List<ImageModel> items);

    void playGif(String url);

    void showGifProgressDialog();

}
