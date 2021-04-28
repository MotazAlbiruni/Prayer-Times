package com.motazalbiruni.prayertimes.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.motazalbiruni.prayertimes.ui.MyIntentService;
import com.motazalbiruni.prayertimes.ui.PrayerTimes;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textTodayDate, textSunRise, textSunSet,txt_location,
            currentPrayer, textTodayHigri, textToday, textTimeLeft, textNextPrayer;
    private RecyclerView recyclerViewPrayer;
    private CountDownTimer downTimer;

    private boolean mTimerRunning;
    private List<PrayerTimes> prayerTimesList;
    private Handler handler = new Handler();
    Intent intentServiceSound;

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
        textNextPrayer = root.findViewById(R.id.text_nextPrayer);
        txt_location = root.findViewById(R.id.text_location);

        currentPrayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentServiceSound = new Intent(getContext(), MyIntentService.class);
                intentServiceSound.setAction(MyIntentService.ACTION_FOO);
                intentServiceSound.putExtra(MyIntentService.EXTRA_PARAM1, "play");
                getContext().startService(intentServiceSound);
            }
        });
        txt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentServiceSound != null){
                    getContext().stopService(intentServiceSound);
                }
            }
        });
        recyclerViewPrayer = root.findViewById(R.id.recycler_times);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                currentPrayer.setText(s);
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

                    String fajr = convertTime(timingEntity.getFajr()); //time of fajr
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
                        String currentTime = convertTime(timeNow);
                        Map<String, String> next = findNext(currentTime, fajr, dhuhr, asr, maghrib, isha);
                        String nextTime = next.get("time");
                        startCountDownTimer(currentTime, nextTime);//count in text view
                        textTimeLeft.setText(nextTime);//for next time prayer in text view
                        String next_Prayer = "null";
                        switch (next.get("prayer")) {
                            case "0":
                                next_Prayer = "Fajr";
                                break;
                            case "1":
                                next_Prayer = "Dhuhr";
                                break;
                            case "2":
                                next_Prayer = "Asr";
                                break;
                            case "3":
                                next_Prayer = "Maghrib";
                                break;
                            case "4":
                                next_Prayer = "Isha";
                                break;
                        }
                        textNextPrayer.setText(next_Prayer);
                        String current_Prayer = "null";
                        switch (next_Prayer) {
                            case "Fajr":
                                current_Prayer = "Isha";
                                break;
                            case "Dhuhr":
                                current_Prayer = "Fajr";
                                break;
                            case "Asr":
                                current_Prayer = "Dhuhr";
                                break;
                            case "Maghrib":
                                current_Prayer = "Asr";
                                break;
                            case "Isha":
                                current_Prayer = "Maghrib";
                                break;
                        }
                        currentPrayer.setText(current_Prayer);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    textSunRise.setText(sunRise);
                    textSunSet.setText(sunSet);
                    textToday.setText(today);
                    textTodayDate.setText(todayGregorian);
                    textTodayHigri.setText(todayHijri);

                    prayerTimesList.clear();
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

    public static Map<String, String> findNext(String current, String... times) throws ParseException {
        Map<String, String> map_prayer = new HashMap<>();
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        long currentMillis = fmt.parse(current).getTime();

        long bestMillis = 0, minMillis = 0;
        String bestTime = null, minTime = null;
        int prayer = 0, currentPrayer = 0;
        for (String time : times) {
            long millis = fmt.parse(time).getTime();
            if (millis >= currentMillis && (bestTime == null || millis < bestMillis)) {
                bestMillis = millis;
                bestTime = time;
                currentPrayer = prayer;
            }
            if (minTime == null || millis < minMillis) {
                minMillis = millis;
                minTime = time;
            }
            prayer++;
        }
        map_prayer.put("prayer", currentPrayer + "");
        map_prayer.put("time", (bestTime != null ? bestTime : minTime));
        return map_prayer;
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

    private void startCountDownTimer(String current_time, String next_time) {
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm a");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            long currentMillis = Objects.requireNonNull(fmt.parse(current_time)).getTime();
            long nextMillis = 0;
            if (next_time.contains("am")&current_time.contains("pm")){
                Toast.makeText(getContext(), "am", Toast.LENGTH_SHORT).show();
                long nextMillisTo12 = Objects.requireNonNull(fmt.parse("11:59 pm")).getTime();
                long timeMidNight = Objects.requireNonNull(fmt.parse("12:00 am")).getTime();
                long timeToFajr = Objects.requireNonNull(fmt.parse(next_time)).getTime();
                nextMillis = nextMillisTo12 + (timeToFajr - timeMidNight);
            }else {
                nextMillis = Objects.requireNonNull(fmt.parse(next_time)).getTime();
            }

            long liftMillis = nextMillis - currentMillis;

            downTimer = new CountDownTimer(liftMillis, 1000) {
                @Override
                public void onTick(long millis_UntilFinished) {
                    long secondsUntilFinished = millis_UntilFinished /1000 ;
                    int hour = (int) (secondsUntilFinished) / 3600;
                    int minutes = (int) (secondsUntilFinished -hour*60*60) / 60;
                    int seconds = (int) secondsUntilFinished -hour*3600 - minutes*60;

                    String timeLiftFormat = String.format(Locale.getDefault(),
                            "%02d:%02d:%02d", hour, minutes, seconds);
//                    txt_countDown.setText(timeLiftFormat);
                    textTimeLeft.setText("- "+timeLiftFormat);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getContext(), "prayer", Toast.LENGTH_SHORT).show();
                    mTimerRunning = false;
                }
            }.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mTimerRunning = true;
    }//end startCountDownTimer()
}//end class