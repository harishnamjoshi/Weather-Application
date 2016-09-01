package com.demo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Weather Application 1.0
 *
 * <p>
 *     Modal will hold the weather data for the single day but will be coming from the daily key.
 * </p>
 */
public class Data {

    @SerializedName("time")
    private long time;

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    public long getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }
}
