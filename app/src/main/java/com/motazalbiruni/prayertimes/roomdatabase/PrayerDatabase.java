package com.motazalbiruni.prayertimes.roomdatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.motazalbiruni.prayertimes.connecting.PrayerClient;
import com.motazalbiruni.prayertimes.connecting.TimingsModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Database(entities = TimingEntity.class, version = 1, exportSchema = false)
public abstract class PrayerDatabase extends RoomDatabase {

    //fields
    private static final String DATABASE_NAME = "myDatabase";
    private static Context mContext;
    private static PrayerDatabase inStance;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

    public static synchronized PrayerDatabase getDatabase(Context context) {
        mContext = context;
        if (inStance == null) {
            inStance = Room.databaseBuilder(context.getApplicationContext(),
                    PrayerDatabase.class, DATABASE_NAME).allowMainThreadQueries()
                    .addCallback(mCallback).build();
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
                    getData();
                }
            });
        }//end onCreate()

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }//end onOpen
    };

    public abstract TimingDao getTimingDao();

    private static void getData(){
        PrayerClient.getConnectingData().getListDay("cairo").enqueue(new retrofit2.Callback<TimingsModel>() {
            @Override
            public void onResponse(Call<TimingsModel> call, Response<TimingsModel> response) {
                List<TimingsModel.Data> dataList = response.body().getData();
                for (TimingsModel.Data data: dataList){
                    String fajr = data.getTimings().getFajr().replace("(EET)","");
                    String dhuhr = data.getTimings().getDhuhr().replace("(EET)","");
                    String asr = data.getTimings().getAsr().replace("(EET)","");
                    String maghrib = data.getTimings().getMaghrib().replace("(EET)","");
                    String isha = data.getTimings().getIsha().replace("(EET)","");
                    String sunrise = data.getTimings().getSunrise().replace("(EET)","");
                    String sunset = data.getTimings().getSunset().replace("(EET)","");
                    String imsak = data.getTimings().getImsak().replace("(EET)","");
                    String readable = data.getDate().getReadable();//25-3-2000
                    String day = data.getDate().getHijri().getDay();//6
                    int number = data.getDate().getHijri().getMonth().getNumber();//8
                    String year = data.getDate().getHijri().getYear();//1448
                    String arWeekDay = data.getDate().getHijri().getWeekday().getAr();

                    TimingEntity entity = new TimingEntity(arWeekDay,day+number+year,readable,fajr,sunrise
                    ,dhuhr,asr,sunset,maghrib,isha,imsak);

                    inStance.getTimingDao().insert(entity);
                }
            }

            @Override
            public void onFailure(Call<TimingsModel> call, Throwable t) {

            }
        });
    }//end getData()
    
}//end class
