package com.arraypay.market.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取sec小時之后的时间
     *
     * @param hours 秒数
     * @return
     */
    public static Date getNewDateByAddSecond(int hours) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 获取时间差
     * @param date1
     * @param date2
     * @return
     */
    public static long getDistanceBetweenTimes(Date date1,Date date2){
        if(date1 == null || date2 == null ){
            return 0;
        }

        return date1.getTime() - date2.getTime();
    }


}
