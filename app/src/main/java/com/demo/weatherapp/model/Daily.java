package com.demo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Weather Application 1.0
 */
public class Daily {

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("data")
    private ArrayList<Data> data;

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<Data> getData() {
        return data;
    }
}
