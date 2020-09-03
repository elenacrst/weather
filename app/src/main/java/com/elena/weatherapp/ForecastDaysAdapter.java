package com.elena.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elena.weatherapp.model.ForecastDay;
import com.elena.weatherapp.views.DetailActivity;

import java.util.List;

public class ForecastDaysAdapter extends RecyclerView.Adapter<ForecastDaysAdapter.ViewHolder> {
    private List<ForecastDay> mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View itemView;
        TextView dayText, dayTempText, nightTempText;
        ImageView dayImage, nightImage;
        public ViewHolder(View v) {
            super(v);
            itemView = v;
            dayText = v.findViewById(R.id.text_day);
            dayTempText = v.findViewById(R.id.text_day_temp);
            nightTempText = v.findViewById(R.id.text_night_temp);
            dayImage = v.findViewById(R.id.image_day);
            nightImage = v.findViewById(R.id.image_night);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ForecastDaysAdapter(List<ForecastDay> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ForecastDaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_day, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.dayText.setText(ForecastUtils.getDay(position));
        holder.nightTempText.setText(ForecastUtils.getCelsiusTempFromKelvin(mDataset.get(position).getTemperature().getNightTemperature()));
        holder.dayTempText.setText(ForecastUtils.getCelsiusTempFromKelvin(mDataset.get(position).getTemperature().getDayTemperature()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openDetailActivityIntent = new Intent(mContext, DetailActivity.class);
                ForecastDay day = mDataset.get(holder.getAdapterPosition());
                openDetailActivityIntent.putExtra(DetailActivity.EXTRA_TEMPERATURE, day.getTemperature());
                openDetailActivityIntent.putExtra(DetailActivity.EXTRA_DAY_INDEX, holder.getAdapterPosition());
                openDetailActivityIntent.putExtra(DetailActivity.EXTRA_PRESSURE, day.getPressure());
                openDetailActivityIntent.putExtra(DetailActivity.EXTRA_HUMIDITY , day.getHumidityPercentage());
                openDetailActivityIntent.putExtra(DetailActivity.EXTRA_WEATHER_CONDITION, day.getCondition()[0].getCondition());
                openDetailActivityIntent.putExtra(DetailActivity.EXTRA_CLOUDS, day.getCloudsPercentage());

                mContext.startActivity(openDetailActivityIntent);
            }
        });
        if (PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("day", false)){
            holder.dayImage.setVisibility(View.VISIBLE);
            holder.nightImage.setVisibility(View.GONE);
            holder.dayTempText.setVisibility(View.VISIBLE);
            holder.nightTempText.setVisibility(View.GONE);
        }else{
            holder.dayImage.setVisibility(View.GONE);
            holder.nightImage.setVisibility(View.VISIBLE);
            holder.dayTempText.setVisibility(View.GONE);
            holder.nightTempText.setVisibility(View.VISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}