package com.motazalbiruni.prayertimes.connecting;

public class TimingsModel {

    private Data data;
    private int code;
    private String status;

    public TimingsModel(Data data, int code, String status) {
        this.data = data;
        this.code = code;
        this.status = status;

    }

    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Data{

        private Timings timings;
        private Date date;

        public Data(Timings timings, Date date) {
            this.timings = timings;
            this.date = date;
        }

        public Timings getTimings() {
            return timings;
        }

        public void setTimings(Timings timings) {
            this.timings = timings;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }//end Class Data

    public class Timings {
        String Fajr;
        String Sunrise;
        String Dhuhr;
        String Asr;
        String Sunset;
        String Maghrib;
        String Isha;
        String Imsak;
        String Midnight;

        public Timings(String fajr, String sunrise, String dhahr, String asr,
                       String sunset, String maghrib, String isha, String imsak, String midnight) {
            Fajr = fajr;
            Sunrise = sunrise;
            Dhuhr = dhahr;
            Asr = asr;
            Sunset = sunset;
            Maghrib = maghrib;
            Isha = isha;
            Imsak = imsak;
            Midnight = midnight;
        }

        public String getFajr() {
            return Fajr;
        }

        public void setFajr(String fajr) {
            Fajr = fajr;
        }

        public String getSunrise() {
            return Sunrise;
        }

        public void setSunrise(String sunrise) {
            Sunrise = sunrise;
        }

        public String getDhuhr() {
            return Dhuhr;
        }

        public void setDhuhr(String dhahr) {
            Dhuhr = dhahr;
        }

        public String getAsr() {
            return Asr;
        }

        public void setAsr(String asr) {
            Asr = asr;
        }

        public String getSunset() {
            return Sunset;
        }

        public void setSunset(String sunset) {
            Sunset = sunset;
        }

        public String getMaghrib() {
            return Maghrib;
        }

        public void setMaghrib(String maghrib) {
            Maghrib = maghrib;
        }

        public String getIsha() {
            return Isha;
        }

        public void setIsha(String isha) {
            Isha = isha;
        }

        public String getImsak() {
            return Imsak;
        }

        public void setImsak(String imsak) {
            Imsak = imsak;
        }

        public String getMidnight() {
            return Midnight;
        }

        public void setMidnight(String midnight) {
            Midnight = midnight;
        }
    }//end Class Timings

    public class Date {
        String readable;
        Hijri hijri;

        public Date(String readable, Hijri hijri) {
            this.readable = readable;
            this.hijri = hijri;
        }

        public String getReadable() {
            return readable;
        }

        public void setReadable(String readable) {
            this.readable = readable;
        }

        public Hijri getHijri() {
            return hijri;
        }

        public void setHijri(Hijri hijri) {
            this.hijri = hijri;
        }

        public class Hijri {
            String day;
            String year;
            Weekday weekday;
            Month month;

            public Hijri(Weekday weekday, Month month, String day,String year) {
                this.weekday = weekday;
                this.month = month;
                this.day = day;
                this.year = year;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public Weekday getWeekday() {
                return weekday;
            }

            public void setWeekday(Weekday weekday) {
                this.weekday = weekday;
            }

            public Month getMonth() {
                return month;
            }

            public void setMonth(Month month) {
                this.month = month;
            }

            public class Weekday {
                String ar;

                public Weekday(String ar) {
                    this.ar = ar;
                }

                public String getAr() {
                    return ar;
                }

                public void setAr(String ar) {
                    this.ar = ar;
                }
            }//end class weekday
            public class Month {
                String number;
                String ar;


                public Month(String number, String ar) {
                    this.number = number;
                    this.ar = ar;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getAr() {
                    return ar;
                }

                public void setAr(String ar) {
                    this.ar = ar;
                }

            }//end class Month

        }//end Class Hijri

    }//end Class Date

}//end class TimingsModel