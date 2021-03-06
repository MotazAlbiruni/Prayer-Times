package com.motazalbiruni.prayertimes.connecting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerInterface {
    @GET("timingsByAddress")
    Call<TimingsModel> getDateDay(@Query("address") String address);

    @GET("calendarByAddress")
    Call<TimingsModel> getListDate(@Query("address")String address ,@Query("method") int method);

    @GET("calendarByAddress")
    Call<TimingsModel> getListDateByMonth(@Query("address")String address
            ,@Query("method") int method ,@Query("month") int month);

}//end interface
