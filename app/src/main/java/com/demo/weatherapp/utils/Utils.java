package com.demo.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Weather Application 1.0
 *
 * <p>
 *     Application utility methods.
 * </p>
 */
public class Utils {

    /**
     * Format which can be used throughout the application to maintian the uniqueness.
     */
    public static final String DATEFORMAT = "MMMM dd, yyyy hh:mm:ss";

    /**
     * Method will check if the network is connected or not and will produce the appropriate result.
     * This method will check data as well as wi-fi network connectivity.
     *
     * @param context of type Application context.
     *
     * @return true if there is network avialability.
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    /**
     * Formats the long value to the date string using the {@link #DATEFORMAT}. Internally this will
     * invoke {@link #formatDateToString(String, long)}.
     *
     * This method will not check for the valid long value. So if passed it will
     * produce in appropriate result.
     *
     * @param date long value.
     *
     * @return date long value.
     */
    public static String formatDateToString(long date) {
        return formatDateToString(DATEFORMAT, date);
    }

    /**
     * Formats the long value to the date string using the string passed as a paramter.
     * This method will not check the validity of format passed.
     *
     * @param format valid string date format.
     *
     * @param date long value.
     *
     * @return date long value.
     */
    public static String formatDateToString(@NonNull String format, long date) {
        Date d = new Date(date);
        SimpleDateFormat f = new SimpleDateFormat(format, Locale.getDefault());
        return f.format(date);
    }
}
