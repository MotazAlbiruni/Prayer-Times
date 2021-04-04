package com.motazalbiruni.prayertimes.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "timing_day")
public class TimingEntity {
    //fields
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "week_day")
    WeekdayRoom weekDay;
    @ColumnInfo(name = "date_hijri")
    DateHijri dateHijri;
    @ColumnInfo(name = "date_gregorian")
    DateGregorian dateGregorian;
    String dateReadable;
    String fajr;
    String sunRise;
    String dhuhr;
    String asr;
    String sunSet;
    String maghrib;
    String isha;
    String imsak;

    @Ignore
    public TimingEntity() {
    }

    //constructor
    public TimingEntity(int id, WeekdayRoom weekDay, DateHijri dateHijri, DateGregorian dateGregorian,String dateReadable,
                        String fajr, String sunRise, String dhuhr, String asr,
                        String sunSet, String maghrib, String isha, String imsak) {
        this.id = id;
        this.weekDay = weekDay;
        this.dateHijri = dateHijri;
        this.dateGregorian = dateGregorian;
        this.dateReadable = dateReadable;
        this.fajr = fajr;
        this.sunRise = sunRise;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.sunSet = sunSet;
        this.maghrib = maghrib;
        this.isha = isha;
        this.imsak = imsak;
    }//end constructor()

    @Ignore
    public TimingEntity(WeekdayRoom weekDay, DateHijri dateHijri, DateGregorian dateGregorian,String dateReadable,
                        String fajr, String sunRise, String dhuhr, String asr,
                        String sunSet, String maghrib, String isha, String imsak) {
        this.weekDay = weekDay;
        this.dateHijri = dateHijri;
        this.dateGregorian = dateGregorian;
        this.dateReadable = dateReadable;
        this.fajr = fajr;
        this.sunRise = sunRise;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.sunSet = sunSet;
        this.maghrib = maghrib;
        this.isha = isha;
        this.imsak = imsak;
    }//end constructor()

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeekdayRoom getWeekDay() {
        return weekDay;
    }

    public DateHijri getDateHijri() {
        return dateHijri;
    }

    public DateGregorian getDateGregorian() {
        return dateGregorian;
    }

    public String getDateReadable() {
        return dateReadable;
    }

    public String getFajr() {
        return fajr;
    }

    public String getSunRise() {
        return sunRise;
    }

    public String getDhuhr() {
        return dhuhr;
    }

    public String getAsr() {
        return asr;
    }

    public String getSunSet() {
        return sunSet;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public String getImsak() {
        return imsak;
    }

}//end class
