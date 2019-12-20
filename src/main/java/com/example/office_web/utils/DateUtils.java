package com.example.office_web.utils;

import java.util.Calendar;

public class DateUtils {

    public static String getNowDateStr(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);

        System.out.println(year + "-" + month + "-" + day );
        return year+"/"+month+"/"+day;
    }


    public static void main(String[] args){
        getNowDateStr();
    }
}
