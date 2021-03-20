package com.motazalbiruni.prayertimes.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.motazalbiruni.prayertimes.connecting.PrayerClient;
import com.motazalbiruni.prayertimes.connecting.TimingsModel;
import com.motazalbiruni.prayertimes.roomdatabase.PrayerRepository;
import com.motazalbiruni.prayertimes.roomdatabase.TimingEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    //fields
    private MutableLiveData<String> mText;
    private PrayerRepository repository;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        repository = PrayerRepository.getRepository(application );

        PrayerClient.getConnectingData().getListDay("cairo",5).enqueue(new Callback<TimingsModel>() {
            @Override
            public void onResponse(Call<TimingsModel> call, Response<TimingsModel> response) {
                String status = response.body().getStatus();
                String s = response.body().getData().get(18).getDate().getHijri().getMonth().getAr();
                mText.setValue(s+"");
            }

            @Override
            public void onFailure(Call<TimingsModel> call, Throwable t) {
                mText.setValue("Failure");
            }
        });

    }//end constructor()

    public LiveData<String> getText() {
        return mText;
    }

    //getNoteById
    public LiveData<TimingEntity> getTimingById(int id){
        return repository.getNoteById(id);
    }

    //getNoteByDate
    public LiveData<TimingEntity> getTimingByDate(String date){
        return repository.getNoteByDate(date);
    }

    //insert
    public void insert(TimingEntity timingEntity){
        repository.insert(timingEntity);
    }
}//end class