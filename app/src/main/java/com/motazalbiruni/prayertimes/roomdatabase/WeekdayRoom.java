package com.motazalbiruni.prayertimes.roomdatabase;

public class WeekdayRoom {

    //fields
    String arWeekDay;
    String enWeekDay;

    public WeekdayRoom(String arWeekDay, String enWeekDay) {
        this.arWeekDay = arWeekDay;
        this.enWeekDay = enWeekDay;
    }

    public String getArWeekDay() {
        return arWeekDay;
    }

    public String getEnWeekDay() {
        return enWeekDay;
    }
}//end class
