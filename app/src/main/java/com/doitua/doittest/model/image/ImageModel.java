package com.doitua.doittest.model.image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by batynchuk on 9/22/17.
 */
public class ImageModel {

    @SerializedName("id")
    private int id;
    @SerializedName("parameters")
    private ImageParameters imageParametersList;
    @SerializedName("smallImagePath")
    private String smallImageUrl;
    @SerializedName("bigImagePath")
    private String bigImageUrl;

    public ImageModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageParameters getImageParametersList() {
        return imageParametersList;
    }

    public void setImageParametersList(ImageParameters imageParametersList) {
        this.imageParametersList = imageParametersList;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "id=" + id +
                ", imageParametersList=" + imageParametersList +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", bigImageUrl='" + bigImageUrl + '\'' +
                '}';
    }
}
