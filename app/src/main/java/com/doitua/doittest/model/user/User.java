package com.doitua.doittest.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created by batynchuk on 9/21/17.
 */

public class User {

    @Expose
    @SerializedName("username")
    private String userName;
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private File avatar;


    public User(){

    }

    public User(String userName, String email, String password, File avatar) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
