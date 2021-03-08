package com.motazalbiruni.prayertimes.roomdatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = TimingEntity.class, version = 1, exportSchema = false)
public abstract class PrayerDatabase extends RoomDatabase {

    //fields
    private static final String DATABASE_NAME = "myDatabase";
    private static Context mcontext;
    private static PrayerDatabase inStance;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

    public static synchronized PrayerDatabase getDatabase(Context context) {
        mcontext = context;
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

                }
            });
        }//end onCreate()

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }//end onOpen
    };

    public abstract TimingDao getTimingDao();
    
}//end class
