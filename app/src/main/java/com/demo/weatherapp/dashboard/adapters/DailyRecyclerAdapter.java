package com.demo.weatherapp.dashboard.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.weatherapp.R;
import com.demo.weatherapp.model.Data;
import com.demo.weatherapp.utils.IconMapper;
import com.demo.weatherapp.utils.Utils;

import java.util.List;

/**
 * WeatherApp
 */
public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.DayWeather> {

    private List<Data> mData;

    public DailyRecyclerAdapter(@NonNull List<Data> data) {
        mData = data;
    }

    @Override
    public DayWeather onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_list_row, parent, false);

        return new DayWeather(itemView);
    }

    @Override
    public void onBindViewHolder(DayWeather holder, int position) {
        holder.date.setText(Utils.formatDateToString(mData.get(position).getTime()));
        holder.summary.setText(mData.get(position).getSummary());

        if (IconMapper.iconLookUp.containsKey(mData.get(position).getIcon())) {
            holder.icon.setImageResource(IconMapper.iconLookUp.get(mData.get(position).getIcon()));
        } else {
            holder.icon.setImageResource(R.drawable.degree);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DayWeather extends RecyclerView.ViewHolder {
        public TextView date, summary;

        public ImageView icon;

        public DayWeather(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.item_date);
            summary = (TextView) view.findViewById(R.id.item_summary);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
