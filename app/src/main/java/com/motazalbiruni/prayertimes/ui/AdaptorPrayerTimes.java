package com.motazalbiruni.prayertimes.ui;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.motazalbiruni.prayertimes.R;

import java.util.List;

/*** Created by motaz albiruni on 16/03/2021 */
public class AdaptorPrayerTimes extends RecyclerView.Adapter<AdaptorPrayerTimes.MyViewHolder> {

    private final Context context;
    private List<PrayerTimes> listItems;

    public AdaptorPrayerTimes(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer_times, parent, false);
        return new MyViewHolder(v);
    }

    public void setList(List<PrayerTimes> list) {
        this.listItems = list;
        notifyDataSetChanged();
    }//end setList()

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PrayerTimes item = listItems.get(position);
        holder.prayer_name.setText(item.item_name);
        holder.prayer_time.setText(item.item_time);
    }//end onBindViewHolder()

    @Override
    public int getItemCount() {
        if (listItems == null) {
            return 0;
        }
        return listItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView prayer_name,prayer_time;
        MyViewHolder(View itemView) {
            super(itemView);
            prayer_name = itemView.findViewById(R.id.prayer_name);
            prayer_time = itemView.findViewById(R.id.prayer_time);
        }
    }//end class MyViewHolder
}//end class