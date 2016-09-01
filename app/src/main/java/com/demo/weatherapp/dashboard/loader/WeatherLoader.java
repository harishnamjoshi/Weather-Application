package com.demo.weatherapp.dashboard.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.demo.weatherapp.BuildConfig;
import com.demo.weatherapp.network.Network;
import com.demo.weatherapp.model.Weather;
import com.google.gson.GsonBuilder;

/**
 * Weather Application 1.0
 */
public class WeatherLoader extends AsyncTaskLoader<Weather> {

    public static final String TEMP =
            "https://api.forecast.io/forecast/7dea2423dd5c383c01a842be1b2e6bb1/37.8267,-122.423";

    private Weather mWeather;

    private String url;

    public static final String URL = BuildConfig.APPLICATION_ID+".URL";

    public WeatherLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public Weather loadInBackground() {
        String response = Network.execute(url, Network.GET);
        return TextUtils.isEmpty(response) ? null :
                new GsonBuilder().create().fromJson(response, Weather.class);
    }

    @Override
    protected void onStartLoading() {
        if (mWeather == null || takeContentChanged()) {
            forceLoad();
        } else {
            deliverResult(mWeather);
        }
    }

    @Override
    public void deliverResult(Weather data) {
        if (data != null) {
            mWeather = data;
        }
        super.deliverResult(data);
    }

    public void forceLoad(String url) {
        this.url = url;
        super.forceLoad();
    }
}
