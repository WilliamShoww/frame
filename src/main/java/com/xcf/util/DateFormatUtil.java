package com.xcf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    public static final String FMT_YM = "yyyyMM";
    public static final String FMT_Y_M = "yyyy-MM";

    public static final String FMT_YMD = "yyyyMMdd";
    public static final String FMT_Y_M_D = "yyyy-MM-dd";

    public static final String FMT_YMD_HMS = "yyyyMMddHHmmss";
    public static final String FMT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_UTC_Y_M_D_H_M_S = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FMT_UTC_Y_M_D_H_M_S_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String FMT_YMD_HMS_S = "yyyyMMddHHmmssSSS";
    public static final String FMT_Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.SSS";


    public static final String FMT_HMS = "HHmmss";
    public static final String FMT_H_M_S = "HH:mm:ss";

    public static final String FMT_HMS_S = "HHmmssSSS";
    public static final String FMT_H_M_S_S = "HH:mm:ss.SSS";

    public static final String FMT_CHINESE_Y_M_D = "yyyy年MM月dd日";
    public static final String FMT_CHINESE_Y_M_D_H_M_S = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FMT_CHINESE_Y_M_D_H_M_S_S = "yyyy年MM月dd日 HH:mm:ss.SSS";

    public static final String FMT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String format) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date parse(String date, String format) {
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
