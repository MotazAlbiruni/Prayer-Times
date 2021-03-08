package com.motazalbiruni.prayertimes.connecting;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrayerClient {
    private static final String URL_BASE="http://api.aladhan.com/v1/";
    private PrayerInterface prayerInterface;
    private static PrayerClient instance;

    public PrayerClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        prayerInterface = retrofit.create(PrayerInterface.class);
    }//end PostClient()

    public static PrayerClient getConnectingData() {
        if (instance==null)
            instance = new PrayerClient();
        return instance;
    }//end getInstance()

    public Call<TimingsModel> getAddressDay(String d){
        return prayerInterface.getDateDay(d);
    }
}
