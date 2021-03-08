package com.motazalbiruni.prayertimes.connecting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerInterface {
    @GET("timingsByAddress")
    Call<TimingsModel> getDateDay(@Query("address") String address);
}
