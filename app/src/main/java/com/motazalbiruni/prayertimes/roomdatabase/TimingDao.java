package com.motazalbiruni.prayertimes.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimingDao {

    @Query("select * from timing_day")
    LiveData<List<TimingEntity>> getAllTiming();

    @Query("select * from timing_day where id= :id")
    LiveData<TimingEntity> getTimingById(int id);

    @Query("select * from timing_day where dateReadable= :readable")
    LiveData<TimingEntity> getTimingByDate(String readable);

    @Insert
    void insert(TimingEntity entity);

    @Update
    void update(TimingEntity entity);

    @Delete
    void delete(TimingEntity entity);

    @Query("delete from timing_day")
    void deleteAll();

    @Query("delete from timing_day where id= :id")
    void deleteById(int id);

    @Query("select count(distinct id) from timing_day")
    int isEmptyDatabase();

}//end interface
