package com.motazalbiruni.prayertimes.roomdatabase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "timing_day")
public class TimingEntity {
    //fields
    @PrimaryKey(autoGenerate = true)
    int id;
    String day;
    String dateHijri;
    String dateReadable;
    String fajr;
    String sunRise;
    String dhuhr;
    String asr;
    String sunSet;
    String maghrib;
    String isha;
    String imsak;

    //constructor
    public TimingEntity(int id, String day, String dateHijri, String dateReadable,
                        String fajr, String sunRise, String dhuhr, String asr,
                        String sunSet, String maghrib, String isha, String imsak) {
        this.id = id;
        this.day = day;
        this.dateHijri = dateHijri;
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
    public TimingEntity(String day, String dateHijri, String dateReadable,
                        String fajr, String sunRise, String dhuhr, String asr,
                        String sunSet, String maghrib, String isha, String imsak) {
        this.day = day;
        this.dateHijri = dateHijri;
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

    public String getDay() {
        return day;
    }

    public String getDateHijri() {
        return dateHijri;
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
