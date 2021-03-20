package com.motazalbiruni.prayertimes;

public class JointClass {

    public static String getMonthEn(int number) {
        String month = "";
        switch (number) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
        }
        return month;
    }//end getMonthEn()

    public static String getMonthAr(int number) {
        String month ="";
        switch (number) {
            case 1:
                month = "يناير";
                break;
            case 2:
                month = "فبراير";
                break;
            case 3:
                month = "مارس";
                break;
            case 4:
                month = "أبريل";
                break;
            case 5:
                month = "مايو";
                break;
            case 6:
                month = "يونيو";
                break;
            case 7:
                month = "يوليو";
                break;
            case 8:
                month = "أغسطس";
                break;
            case 9:
                month = "سبتمبر";
                break;
            case 10:
                month = "أكتوبر";
                break;
            case 11:
                month = "نوفمبر";
                break;
            case 12:
                month = "ديسمبر";
                break;
        }
        return month;
    }//end getMonthAr()

    public static String getMonthHigriEn(int number) {
        String month ="";
        switch (number) {
            case 1:
                month = "Al Muharam";
                break;
            case 2:
                month = "Safar";
                break;
            case 3:
                month = "Rabi Al Awwal";
                break;
            case 4:
                month = "Rabi Al Akhir";
                break;
            case 5:
                month = "Jumada Al Ulo";
                break;
            case 6:
                month = "Jumada Al Akhirah";
                break;
            case 7:
                month = "Ragab";
                break;
            case 8:
                month = "Shaban";
                break;
            case 9:
                month = "Ramadan";
                break;
            case 10:
                month = "Shawwal";
                break;
            case 11:
                month = "Du Al Qadah";
                break;
            case 12:
                month = "Du Al Hijjah";
                break;
        }
        return month;
    }//end getMonthHigriEn()

}//end class
