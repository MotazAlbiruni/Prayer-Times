package com.motazalbiruni.prayertimes.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motazalbiruni.prayertimes.R;
import com.motazalbiruni.prayertimes.roomdatabase.DataConverter;
import com.motazalbiruni.prayertimes.roomdatabase.TimingEntity;
import com.motazalbiruni.prayertimes.ui.AdaptorPrayerTimes;
import com.motazalbiruni.prayertimes.ui.PrayerTimes;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textTodayDate, textSunRise, textSunSet,
            currentPrayer, textTodayHigri, textToday, textTimeLeft;
    private RecyclerView recyclerViewPrayer;

    private List<PrayerTimes> prayerTimesList;

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

        recyclerViewPrayer = root.findViewById(R.id.recycler_times);

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPrayer.setLayoutManager(linearLayoutManager);
        AdaptorPrayerTimes recyclerAdaptor = new AdaptorPrayerTimes(getContext());

        prayerTimesList = new ArrayList<>();

        Date date = new Date();
        String dateToday = (String) DateFormat.format("dd-MM-yyyy", date.getTime());
        String timeNow = (String) DateFormat.format("HH:mm", date.getTime());

        homeViewModel.getTimingByDate(dateToday).observe(getViewLifecycleOwner(), new Observer<TimingEntity>() {
            @Override
            public void onChanged(TimingEntity timingEntity) {
                if (timingEntity != null) {
                    String sunRise = convertTime(timingEntity.getSunRise());//time of sunRise
                    String sunSet = convertTime(timingEntity.getSunSet());//time of sunSet

                    final String fajr = convertTime(timingEntity.getFajr()); //time of fajr
                    String dhuhr = convertTime(timingEntity.getDhuhr());//time of dhuhr
                    String asr = convertTime(timingEntity.getAsr());//time of asr
                    String maghrib = convertTime(timingEntity.getMaghrib()); //time of maghrib
                    String isha = convertTime(timingEntity.getIsha()); //time of isha
                    //week day
                    String today = timingEntity.getWeekDay().getArWeekDay();
                    //hijri date
                    String todayHijri = String.format("%s %s %s"
                            , timingEntity.getDateHijri().getDay()
                            , timingEntity.getDateHijri().getMonth_ar()
                            , timingEntity.getDateHijri().getYear());
                    //gregorian date
                    String todayGregorian = String.format("%s %s %s"
                            , timingEntity.getDateGregorian().getDay()
                            , timingEntity.getDateGregorian().getMonth_ar()
                            , timingEntity.getDateGregorian().getYear());

                    try {
                        String s = convertTime(timeNow);
                        String next = findNext(s, fajr, dhuhr, asr, maghrib, isha);
                        textTimeLeft.setText(next);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    textSunRise.setText(sunRise);
                    textSunSet.setText(sunSet);
                    textToday.setText(today);
                    textTodayDate.setText(todayGregorian);
                    textTodayHigri.setText(todayHijri);

                    prayerTimesList.add(new PrayerTimes("Fajr", fajr));
                    prayerTimesList.add(new PrayerTimes("Dhuhr", dhuhr));
                    prayerTimesList.add(new PrayerTimes("Asr", asr));
                    prayerTimesList.add(new PrayerTimes("maghrib", maghrib));
                    prayerTimesList.add(new PrayerTimes("Isha", isha));

                    recyclerAdaptor.setList(prayerTimesList);
                    recyclerViewPrayer.setAdapter(recyclerAdaptor);

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

    public String convertTime(String time) {
        String timeConvert = "";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(time);
            timeConvert = new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return timeConvert;
    }//end convertTime()

}//end class