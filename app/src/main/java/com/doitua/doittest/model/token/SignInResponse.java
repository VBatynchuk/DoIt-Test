package com.doitua.doittest.model.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by batynchuk on 9/21/17.
 */

public class SignInResponse {

    @Expose
    @SerializedName("creation_time")
    private String creationTime;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("avatar")
    private String avataLink;


    public SignInResponse() {

    }

    public SignInResponse(String creationTime, String token, String avataLink) {
        this.creationTime = creationTime;
        this.token = token;
        this.avataLink = avataLink;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvataLink() {
        return avataLink;
    }

    public void setAvataLink(String avataLink) {
        this.avataLink = avataLink;
    }

    @Override
    public String toString() {
        return "SignInResponse{" +
                "creationTime='" + creationTime + '\'' +
                ", token='" + token + '\'' +
                ", avataLink='" + avataLink + '\'' +
                '}';
    }
}
