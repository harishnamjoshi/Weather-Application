package com.demo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Weather Application 1.0
 */
public class Current {

    @SerializedName("time")
    private long time;

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("temperature")
    private float temperature;

    @SerializedName("apparentTemperature")
    private float apparentTemperature;

    public long getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getApparentTemperature() {
        return apparentTemperature;
    }
}
