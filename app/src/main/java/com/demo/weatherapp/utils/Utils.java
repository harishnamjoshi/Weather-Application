package com.demo.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * WeatherApp
 */
public class Utils {

    public static final String DATEFORMAT = "MMMM dd, yyyy hh:mm:ss";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    public static String formatDateToString(long date) {
        return formatDateToString(DATEFORMAT, date);
    }

    public static String formatDateToString(String format, long date) {
        Date d = new Date(date);
        SimpleDateFormat f = new SimpleDateFormat(format, Locale.getDefault());
        return f.format(date);
    }
}
