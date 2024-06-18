package com.wuyan.vaccinereservation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于项目中的日期时间格式的转换
 */
public class DateTimeUtils {
    /**
     * 获取Date的年-月-日信息
     */
    public static String DateToYearMonthDay(Date date){
        // 使用 SimpleDateFormat 定义日期和时间格式
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化日期部分
        String formattedDate = dateFormatter.format(date);
        return formattedDate;
    }

    /**
     * 获取Date的时间信息
     * @param date 日期时间对象
     * @return 时间部分
     */
    public static String DateToTime(Date date){

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        String formattedTime = timeFormatter.format(date);
        return formattedTime;
    }
}
