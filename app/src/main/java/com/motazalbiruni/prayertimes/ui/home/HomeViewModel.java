package com.motazalbiruni.prayertimes.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.motazalbiruni.prayertimes.connecting.PrayerClient;
import com.motazalbiruni.prayertimes.connecting.TimingsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    //fields
    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        PrayerClient.getConnectingData().getAddressDay("cairo").enqueue(new Callback<TimingsModel>() {
            @Override
            public void onResponse(Call<TimingsModel> call, Response<TimingsModel> response) {
                int code = response.body().getCode();
                mText.setValue(code+"");
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
}//end class