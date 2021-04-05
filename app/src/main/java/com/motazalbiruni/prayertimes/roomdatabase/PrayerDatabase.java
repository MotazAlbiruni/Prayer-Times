package com.motazalbiruni.prayertimes.roomdatabase;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.motazalbiruni.prayertimes.JointClass;
import com.motazalbiruni.prayertimes.connecting.PrayerClient;
import com.motazalbiruni.prayertimes.connecting.TimingsModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Database(entities = TimingEntity.class, version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class PrayerDatabase extends RoomDatabase {

    //fields
    private static final String DATABASE_NAME = "myDatabase";
    private static Context mContext;
    private static PrayerDatabase inStance;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);
    private static SharedPreferences preferencesPrayerTimes;

    public static synchronized PrayerDatabase getDatabase(Context context) {
        mContext = context;
        if (inStance == null) {
            inStance = Room.databaseBuilder(context.getApplicationContext(),
                    PrayerDatabase.class, DATABASE_NAME).allowMainThreadQueries()
                    .addCallback(mCallback).build();
            preferencesPrayerTimes = mContext.getSharedPreferences("timePrayer", Context.MODE_PRIVATE);
        }
        return inStance;
    }//end getDatabase()

    public static RoomDatabase.Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor = preferencesPrayerTimes.edit();
                    editor.putInt("update", 1);
                    editor.apply();
                    getData();
                }
            });
        }//end onCreate()

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    getData();
                }
            });
        }//end onOpen
    };

    public abstract TimingDao getTimingDao();

    private static void getData() {

        PrayerClient.getConnectingData().getListDay("cairo", 5)
                .enqueue(new retrofit2.Callback<TimingsModel>() {
                    @Override
                    public void onResponse(Call<TimingsModel> call, Response<TimingsModel> response) {
                        List<TimingsModel.Data> dataList = response.body().getData();
                        SharedPreferences.Editor editor = preferencesPrayerTimes.edit();

                        for (TimingsModel.Data data : dataList) {
                            //prayer times
                            String fajr = data.getTimings().getFajr().replace("(EET)", "");
                            String dhuhr = data.getTimings().getDhuhr().replace("(EET)", "");
                            String asr = data.getTimings().getAsr().replace("(EET)", "");
                            String maghrib = data.getTimings().getMaghrib().replace("(EET)", "");
                            String isha = data.getTimings().getIsha().replace("(EET)", "");
                            String sunrise = data.getTimings().getSunrise().replace("(EET)", "");
                            String sunset = data.getTimings().getSunset().replace("(EET)", "");
                            String imsak = data.getTimings().getImsak().replace("(EET)", "");
                            //date gregorian
                            String readable = data.getDate().getGregorian().getDate();//25-3-2000
                            String day_number = data.getDate().getGregorian().getDay();//6
                            int month_number = data.getDate().getGregorian().getMonth().getNumber();//-08
                            String monthEn = JointClass.getMonthEn(month_number);//to get name of month en
                            String monthAr = JointClass.getMonthAr(month_number);//to get name of month ar
                            String year_gregorian = data.getDate().getGregorian().getYear();//2021
                            DateGregorian dateGregorian = new DateGregorian(day_number, month_number, monthAr, monthEn, year_gregorian);
                            //date hijri
                            String day_hijri = data.getDate().getHijri().getDay();//6
                            int month_hijri_number = data.getDate().getHijri().getMonth().getNumber();//8
                            String month_hijri_en = JointClass.getMonthHigriEn(month_hijri_number);//Ragab
                            String month_hijri_ar = data.getDate().getHijri().getMonth().getAr();//رجب
                            String year_higri = data.getDate().getHijri().getYear();//1448
                            DateHijri dateHijri = new DateHijri(day_hijri, month_hijri_number, month_hijri_ar, month_hijri_en, year_higri);
                            //weekday
                            String arWeekDay = data.getDate().getHijri().getWeekday().getAr();
                            String enWeekDay = data.getDate().getGregorian().getWeekday().getEn();
                            WeekdayRoom weekday = new WeekdayRoom(arWeekDay, enWeekDay);

                            TimingEntity entity = new TimingEntity(weekday
                                    , dateHijri, dateGregorian, readable,
                                    fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, imsak);

                            inStance.getTimingDao().insert(entity);
                            int update = preferencesPrayerTimes.getInt("update", -1);
                            if (update != -1) {
                                inStance.getTimingDao().update(entity);
                            }

                            editor.putInt("currentMonth", month_number);
                            editor.apply();

                        }//end for
                    }//end onResponse()

                    @Override
                    public void onFailure(Call<TimingsModel> call, Throwable t) {
                        SharedPreferences.Editor editor = preferencesPrayerTimes.edit();
                        editor.putInt("update", -1);
                        editor.apply();
                    }
                });

        int month_counter = preferencesPrayerTimes.getInt("currentMonth", 1);
        int update_counter = preferencesPrayerTimes.getInt("update", -1);

        if (update_counter == 1) {
            for (int i = 0; i<12 ; i++) {
                month_counter++;
                PrayerClient.getConnectingData().getListDayByMonth("cairo",5,month_counter)
                        .enqueue(new retrofit2.Callback<TimingsModel>() {
                            @Override
                            public void onResponse(Call<TimingsModel> call, Response<TimingsModel> response) {
                                List<TimingsModel.Data> dataList = response.body().getData();
                                SharedPreferences.Editor editor = preferencesPrayerTimes.edit();

                                for (TimingsModel.Data data : dataList) {
                                    //prayer times
                                    String fajr = data.getTimings().getFajr().replace("(EET)", "");
                                    String dhuhr = data.getTimings().getDhuhr().replace("(EET)", "");
                                    String asr = data.getTimings().getAsr().replace("(EET)", "");
                                    String maghrib = data.getTimings().getMaghrib().replace("(EET)", "");
                                    String isha = data.getTimings().getIsha().replace("(EET)", "");
                                    String sunrise = data.getTimings().getSunrise().replace("(EET)", "");
                                    String sunset = data.getTimings().getSunset().replace("(EET)", "");
                                    String imsak = data.getTimings().getImsak().replace("(EET)", "");
                                    //date gregorian
                                    String readable = data.getDate().getGregorian().getDate();//25-3-2000
                                    String day_number = data.getDate().getGregorian().getDay();//6
                                    int month_number = data.getDate().getGregorian().getMonth().getNumber();//-08
                                    String monthEn = JointClass.getMonthEn(month_number);//to get name of month en
                                    String monthAr = JointClass.getMonthAr(month_number);//to get name of month ar
                                    String year_gregorian = data.getDate().getGregorian().getYear();//2021
                                    DateGregorian dateGregorian = new DateGregorian(day_number, month_number, monthAr, monthEn, year_gregorian);
                                    //date hijri
                                    String day_hijri = data.getDate().getHijri().getDay();//6
                                    int month_hijri_number = data.getDate().getHijri().getMonth().getNumber();//8
                                    String month_hijri_en = JointClass.getMonthHigriEn(month_hijri_number);//Ragab
                                    String month_hijri_ar = data.getDate().getHijri().getMonth().getAr();//رجب
                                    String year_higri = data.getDate().getHijri().getYear();//1448
                                    DateHijri dateHijri = new DateHijri(day_hijri, month_hijri_number, month_hijri_ar, month_hijri_en, year_higri);
                                    //weekday
                                    String arWeekDay = data.getDate().getHijri().getWeekday().getAr();
                                    String enWeekDay = data.getDate().getGregorian().getWeekday().getEn();
                                    WeekdayRoom weekday = new WeekdayRoom(arWeekDay, enWeekDay);

                                    TimingEntity entity = new TimingEntity(weekday
                                            , dateHijri, dateGregorian, readable,
                                            fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, imsak);

                                    inStance.getTimingDao().insert(entity);
                                    int update = preferencesPrayerTimes.getInt("update", -1);
                                    if (update != -1) {
                                        inStance.getTimingDao().update(entity);
                                    }
                                }
                            }//end onResponse()
                            @Override
                            public void onFailure(Call<TimingsModel> call, Throwable t) {

                            }//end onFailure()
                        });
            }//end for month
        }


    }//end getData()

}//end class
