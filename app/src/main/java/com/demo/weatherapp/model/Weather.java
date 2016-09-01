package com.demo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * WeatherApp
 */
public class Weather {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("offset")
    private double offset;

    @SerializedName("currently")
    private Current currently;

    @SerializedName("daily")
    private Daily daily;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public double getOffset() {
        return offset;
    }

    public Current getCurrently() {
        return currently;
    }

    public Daily getDaily() {
        return daily;
    }
}
