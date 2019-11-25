package com.xcf.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static long currentTimeMillis(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime).getTime();
    }

    public static LocalDateTime toLocaleDateTime(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    public static int getYear(LocalDate date) {
        return date.getYear();
    }

    public static Month month(LocalDate date) {
        return date.getMonth();
    }

    public static LocalDateTime getDayStart(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDateTime getDayEnd(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    public static Date getDayStartToDate(LocalDate date) {
        return toDate(LocalDateTime.of(date, LocalTime.MIN));
    }

    public static Date getDayEndToDate(LocalDate date) {
        return toDate(LocalDateTime.of(date, LocalTime.MAX));
    }

    /**
     * 获取本周的开始时间
     *
     * @param date 当前时间
     */
    public static LocalDateTime getWeekStart(LocalDate date) {
        TemporalAdjuster adjuster = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        LocalDate localDate = date.with(adjuster);
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 获取当月的开始时间
     *
     * @param date 当前时间
     */
    public static LocalDateTime getMonthStart(LocalDate date) {
        LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 是否是同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        String format1 = DateFormatUtil.format(date1, DateFormatUtil.FMT_Y_M_D);
        String format2 = DateFormatUtil.format(date2, DateFormatUtil.FMT_Y_M_D);
        return format1.equals(format2);
    }

    /**
     * 是否是同年同一周
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        //西方周日为一周的第一天，咱得将周一设为一周第一天
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setFirstDayOfWeek(Calendar.MONDAY);

        cal1.setTime(date1);
        cal2.setTime(date2);

        int year = cal1.get(Calendar.YEAR);
        int year1 = cal2.get(Calendar.YEAR);

        int interval = year - year1;
        if (interval == 0) {
            // 同一年
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        } else if (interval == 1) {
            // date1 比 date2大一年
            if (cal1.get(Calendar.MONTH) == 0 && cal2.get(Calendar.MONTH) == 11) {
                return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
            }
        } else if (interval == -1) {
            // date1 比 date2小一年
            if (cal1.get(Calendar.MONTH) == 11 && cal2.get(Calendar.MONTH) == 0) {
                return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
            }
        }
        return false;
    }

    /**
     * 是否是同年同月
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            return false;
        }
        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        return month1 == month2;
    }

    /**
     * 是否是同一天
     */
    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        LocalDate localDate1 = date1.toLocalDate();
        LocalDate localDate2 = date2.toLocalDate();
        return localDate1.compareTo(localDate2) == 0;
    }

    public static void main(String[] args) throws ParseException {
        LocalDate date = LocalDate.parse("2019-10-31 23:59:59", DateTimeFormatter.ofPattern(DateFormatUtil.FMT_DEFAULT));
        LocalDateTime weekStart = getWeekStart(date);
        System.out.println("weekStart = " + weekStart);
        LocalDateTime monthStart = getMonthStart(date);
        System.out.println("monthStart = " + monthStart);
    }
}
