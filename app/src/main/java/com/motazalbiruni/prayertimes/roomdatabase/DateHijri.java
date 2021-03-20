package com.motazalbiruni.prayertimes.roomdatabase;

public class DateHijri {

    //fields
    String day ;
    int month_number;
    String month_ar;
    String month_en;
    String year;

    public DateHijri(String day, int month_number, String month_ar, String month_en, String year) {
        this.day = day;
        this.month_number = month_number;
        this.month_ar = month_ar;
        this.month_en = month_en;
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public int getMonth_number() {
        return month_number;
    }

    public String getMonth_ar() {
        return month_ar;
    }

    public String getMonth_en() {
        return month_en;
    }

    public String getYear() {
        return year;
    }
}//end class
