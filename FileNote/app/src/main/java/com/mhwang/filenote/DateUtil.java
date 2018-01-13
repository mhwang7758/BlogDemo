package com.mhwang.filenote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description :
 * Author :mhwang
 * Date : 2017/5/25
 * Version : V1.0
 */

public class DateUtil {

    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder builder = new StringBuilder();
        builder.append(year);
        builder.append((month >= 10 ) ? month : "0" + month);
        builder.append((day >= 10 ) ? day : "0" + day);
        return builder.toString();
    }

    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

}
