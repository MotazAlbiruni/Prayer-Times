package com.motazalbiruni.prayertimes.roomdatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class PrayerRepository {

    //fields
    private static PrayerRepository prayerRepository;
    private static TimingEntity timingEntity;
    private TimingDao timingDao;

    public PrayerRepository(Application application) {
        timingDao = PrayerDatabase.getDatabase(application).getTimingDao();
    }//end constructor()

    //singleton
    public static synchronized PrayerRepository getRepository(Application application){
        if(prayerRepository == null){
            prayerRepository = new PrayerRepository(application);
        }
        return prayerRepository;
    }//end getRepository

    //getNoteById
    public LiveData<TimingEntity> getNoteById(final int id){
        return timingDao.getTimingById(id);
    }

    //insert
    public void insert(final TimingEntity entity){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                timingDao.insert(entity);
            }
        });
    }
}//end class
