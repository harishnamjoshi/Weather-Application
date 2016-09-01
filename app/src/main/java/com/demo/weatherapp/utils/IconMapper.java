package com.demo.weatherapp.utils;

import com.demo.weatherapp.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Weather Application 1.0
 */
public class IconMapper {

    public static final Map<String, Integer> iconLookUp = Collections
            .unmodifiableMap(new HashMap<String, Integer>(){
                {
                    put("clear_day", R.drawable.clear_day);
                    put("clear_night", R.drawable.clear_night);
                    put("cloudy", R.drawable.cloudy);
                    put("cloudy_night", R.drawable.cloudy_night);
                    put("degree", R.drawable.degree);
                    put("fog", R.drawable.fog);
                    put("partly_cloudy", R.drawable.partly_cloudy);
                    put("rain", R.drawable.rain);
                    put("refresh", R.drawable.refresh);
                    put("sleet", R.drawable.sleet);
                    put("snow", R.drawable.snow);
                    put("sunny", R.drawable.sunny);
                    put("wind", R.drawable.wind);
                }});
}
