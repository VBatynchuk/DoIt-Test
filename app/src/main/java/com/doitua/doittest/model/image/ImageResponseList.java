package com.doitua.doittest.model.image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by batynchuk on 9/24/17.
 */

public class ImageResponseList {


    @SerializedName("images")
    private List<ImageModel> imageModelList;

    public ImageResponseList() {
    }

    public List<ImageModel> getImageModelList() {
        return imageModelList;
    }

    public void setImageModelList(List<ImageModel> imageModelList) {
        this.imageModelList = imageModelList;
    }
}
