package com.demo.weatherapp.dashboard.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.weatherapp.dashboard.fragments.CurrentWeatherFragment;
import com.demo.weatherapp.dashboard.fragments.DailyWeatherFragment;
import com.demo.weatherapp.model.Weather;

/**
 * WeatherApp
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Weather mWeather;

    public SectionsPagerAdapter(FragmentManager fm, @NonNull Weather weather) {
        super(fm);
        mWeather = weather;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                CurrentWeatherFragment cur = CurrentWeatherFragment.newInstance(position + 1);
                cur.setCurrent(mWeather.getCurrently());
                fragment = cur;
                break;

            case 1:
                DailyWeatherFragment daily = DailyWeatherFragment.newInstance(position + 1);
                daily.setDaily(mWeather.getDaily());
                fragment = daily;
                break;
        }

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Current";
            case 1:
                return "Daily";
        }
        return null;
    }
}