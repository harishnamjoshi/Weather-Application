package com.demo.weatherapp.dashboard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.weatherapp.R;
import com.demo.weatherapp.model.Current;
import com.demo.weatherapp.utils.IconMapper;
import com.demo.weatherapp.utils.Utils;

/**
 * WeatherApp
 */
public class CurrentWeatherFragment extends Fragment {

    private Current mCurrent;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CurrentWeatherFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CurrentWeatherFragment newInstance(int sectionNumber) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_currentweather, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.summary);
        textView.setText(mCurrent.getSummary());

        TextView date = (TextView) rootView.findViewById(R.id.date);
        date.setText(Utils.formatDateToString(mCurrent.getTime()));

        TextView temperature = (TextView) rootView.findViewById(R.id.temperature);
        temperature.setText(Float.toString(mCurrent.getTemperature())+(char) 0x00B0 );

        ImageView icon = (ImageView) rootView.findViewById(R.id.weather_icon);

        if (IconMapper.iconLookUp.containsKey(mCurrent.getIcon())) {
            icon.setImageResource(IconMapper.iconLookUp.get(mCurrent.getIcon()));
        } else {
            icon.setImageResource(R.drawable.degree);
        }

        return rootView;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }
}
