package com.doitua.doittest.model.gif;

import com.google.gson.annotations.SerializedName;

/**
 * Created by batynchuk on 9/25/17.
 */

public class Gif {

    @SerializedName("gif")
    private String gifUrl;

    public Gif() {

    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
