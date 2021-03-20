package com.motazalbiruni.prayertimes.roomdatabase;

import androidx.room.TypeConverter;
import com.google.gson.Gson;

public class DataConverter {

    @TypeConverter
    public static DateGregorian getDateGregorian(String list) {
        return new Gson().fromJson(list, DateGregorian.class);
    }

    @TypeConverter
    public static String fromDateGregorian(DateGregorian gregorian) {
        Gson gson = new Gson();
        return gson.toJson(gregorian);
    }

    @TypeConverter
    public static WeekdayRoom getWeekdayRoom(String list) {
        return new Gson().fromJson(list, WeekdayRoom.class);
    }

    @TypeConverter
    public static String fromWeekdayRoom(WeekdayRoom dayRoom) {
        Gson gson = new Gson();
       return gson.toJson(dayRoom);
    }

    @TypeConverter
    public static DateHijri getDateHijri(String list) {
        return new Gson().fromJson(list, DateHijri.class);
    }

    @TypeConverter
    public static String fromDateHijri(DateHijri dateHijri) {
        Gson gson = new Gson();
        return gson.toJson(dateHijri);
    }

}//end class
