package com.demo.weatherapp.dashboard.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.weatherapp.R;
import com.demo.weatherapp.dashboard.adapters.DailyRecyclerAdapter;
import com.demo.weatherapp.model.Daily;

/**
 * Weather Application 1.0
 */
public class DailyWeatherFragment extends Fragment{

    private Daily mDaily;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public DailyWeatherFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DailyWeatherFragment newInstance(int sectionNumber) {
        DailyWeatherFragment fragment = new DailyWeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dailyweather, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.daily_recyeclerview);
        DailyRecyclerAdapter adapter = new DailyRecyclerAdapter(mDaily.getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void setDaily(Daily daily) {
        mDaily = daily;
    }
}
