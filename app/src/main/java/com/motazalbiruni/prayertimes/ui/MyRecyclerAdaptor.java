package com.motazalbiruni.prayertimes.ui;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.motazalbiruni.prayertimes.R;

import java.util.List;

/*** Created by motaz albiruni on 16/03/2021 */
public class MyRecyclerAdaptor extends RecyclerView.Adapter<MyRecyclerAdaptor.MyViewHolder> {

    private final Context context;
    private List<PrayerTimes> items;

    public MyRecyclerAdaptor(List<PrayerTimes> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer_times, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PrayerTimes item = items.get(position);

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}