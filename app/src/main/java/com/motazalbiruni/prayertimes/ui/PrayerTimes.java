package com.motazalbiruni.prayertimes.ui;

public class PrayerTimes {

    //fields
    String item_name;
    String item_time;

    //constructor
    public PrayerTimes(String item_name, String item_time) {
        this.item_name = item_name;
        this.item_time = item_time;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_time() {
        return item_time;
    }

    public void setItem_time(String item_time) {
        this.item_time = item_time;
    }

}//end class
