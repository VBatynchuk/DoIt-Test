package com.doitua.doittest.model.image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by batynchuk on 9/24/17.
 */

public class ImageParameters {

    @SerializedName("longitude")
    private float longitude;
    @SerializedName("latitude")
    private float latitude;
    @SerializedName("address")
    private String address;
    @SerializedName("weather")
    private String weather;

    public ImageParameters(){

    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "ImageParameters{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
