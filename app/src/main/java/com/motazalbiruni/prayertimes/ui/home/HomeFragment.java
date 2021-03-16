package com.motazalbiruni.prayertimes.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.motazalbiruni.prayertimes.R;
import com.motazalbiruni.prayertimes.roomdatabase.TimingEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textTodayDate, textSunRise ,textSunSet,
            currentPrayer, textTodayHigri,textToday, textTimeLeft;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        currentPrayer = root.findViewById(R.id.text_currentPrayer);
        textSunRise = root.findViewById(R.id.text_sunRiseTime);
        textSunSet = root.findViewById(R.id.text_sunSetTime);
        textTodayDate = root.findViewById(R.id.text_todayDate);
        textTodayHigri = root.findViewById(R.id.text_todayHigri);
        textToday = root.findViewById(R.id.text_today);
        textTimeLeft = root.findViewById(R.id.text_timeLeft);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                currentPrayer.setText(s);
            }
        });

        return root;
    }//end onCreateView()

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date date = new Date();
        String timeToday = (String) DateFormat.format("dd-MM-yyyy", date.getTime());

        try {
            String s = convertTime("15:15");
            String next = findNext(s, "4:21 AM", "12:1 PM", "3:32 PM", "6:30 PM", "8:4 PM");
            textTimeLeft.setText(next);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        homeViewModel.getTimingByDate(timeToday).observe(getViewLifecycleOwner(), new Observer<TimingEntity>() {
            @Override
            public void onChanged(TimingEntity timingEntity) {
                if (timingEntity != null){
                    String sunRise = convertTime(timingEntity.getSunRise());
                    String sunSet = convertTime(timingEntity.getSunSet()) ;
                    textSunRise.setText(sunRise);
                    textSunSet.setText(sunSet);
                    textToday.setText(timingEntity.getDay());
                    textTodayDate.setText(timingEntity.getDateReadable());
                    textTodayHigri.setText(timingEntity.getDateHijri());
                    textTodayDate.setText(timingEntity.getDateReadable());

                }
            }
        });
    }//end onViewCreated()

    public static String findNext(String current, String... times) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        long currentMillis = fmt.parse(current).getTime();

        long bestMillis = 0, minMillis = 0;
        String bestTime = null, minTime = null;

        for (String time : times) {
            long millis = fmt.parse(time).getTime();
            if (millis >= currentMillis && (bestTime == null || millis < bestMillis)) {
                bestMillis = millis;
                bestTime = time;
            }
            if (minTime == null || millis < minMillis) {
                minMillis = millis;
                minTime = time;
            }
        }
        return (bestTime != null ? bestTime : minTime);
    }//end find Next()

    public String convertTime(String time){
        String timeConvert = "";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(time);
           timeConvert =  new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return timeConvert;
    }//end convertTime()

}//end class