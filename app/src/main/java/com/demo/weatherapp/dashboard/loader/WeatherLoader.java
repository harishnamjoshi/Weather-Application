package com.demo.weatherapp.dashboard.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.demo.weatherapp.BuildConfig;
import com.demo.weatherapp.network.Network;
import com.demo.weatherapp.model.Weather;
import com.google.gson.GsonBuilder;

/**
 * Weather Application 1.0
 *
 * <p>
 *     Loader class which will load the weather data from the network.
 *
 *     Loader will check if weather is already loaded once if yes it will not invoke
 *     the fetch call again unless and until {@link #forceLoad()} is called.
 * </p>
 */
public class WeatherLoader extends AsyncTaskLoader<Weather> {

    /**
     * Cached weather data.
     */
    private Weather mWeather;

    /**
     * Network request url using which weather data can be loaded.
     */
    @NonNull
    private String url;

    /**
     * Key can be used to pass the url inside bundle when calling
     * {@link android.support.v4.app.LoaderManager#initLoader(int, Bundle, LoaderManager.LoaderCallbacks)}
     */
    public static final String URL = BuildConfig.APPLICATION_ID+".URL";

    public WeatherLoader(Context context,@NonNull String url) {
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

    /**
     * Overloaded method which can be used to forceload the weather data in scenario where
     * there is need of forceload with different url.
     *
     * @param url which can be used to load data from the network.
     */
    public void forceLoad(@NonNull String url) {
        this.url = url;
        super.forceLoad();
    }
}
