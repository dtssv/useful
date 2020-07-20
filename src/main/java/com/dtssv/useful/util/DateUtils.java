package com.dtssv.useful.util;

import com.dtssv.useful.common.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 时间工具类，Date，LocalDateTime，LocalDate
 * LocalTime类型数据的格式化、比较、等常用方法
 * @author zhaoyafei
 * @version 1.0
 */
public class DateUtils {

    private static final ConcurrentMap<String,SimpleDateFormat> PATTERN_MAP = new ConcurrentHashMap();

    public static final ConcurrentMap<Class,String> LOCAL_PATTERN = new ConcurrentHashMap();

    static {
        LOCAL_PATTERN.put(LocalDateTime.class,Constant.DATE_DEFAULT_PATTERN);
        LOCAL_PATTERN.put(LocalDate.class,Constant.LOCAL_DATE_DEFAULT_PATTERN);
        LOCAL_PATTERN.put(LocalTime.class,Constant.LOCAL_TIME_DEFAULT_PATTERN);
    }

    public static SimpleDateFormat getPatternFormat(String pattern){
        if(StringUtils.isEmpty(pattern)){
            return null;
        }
        SimpleDateFormat instance = PATTERN_MAP.get(pattern);
        if(instance == null){
            PATTERN_MAP.putIfAbsent(pattern,new SimpleDateFormat(pattern));
            instance = PATTERN_MAP.get(pattern);
        }
        return instance;
    }

    /**
     * 格式化日期
     **/
    public static String format(Date date){
        return format(date,StringUtils.EMPTY);
    }

    /**
     * 格式化日期
     **/
    public static String format(Date date,String pattern){
        if(date == null){
            return "";
        }
        if(StringUtils.isEmpty(pattern)){
            pattern = Constant.DATE_DEFAULT_PATTERN;
        }
        SimpleDateFormat simpleDateFormat = getPatternFormat(pattern);
        return simpleDateFormat.format(date);
    }
    /**
     * 格式化LocalDate类型
     **/
    public static String formatLocal(TemporalAccessor local){
        return formatLocal(local,StringUtils.EMPTY);
    }
    /**
     * 格式化LocalDate类型
     **/
    public static String formatLocal(TemporalAccessor local,String pattern){
        if(local == null){
            return "";
        }
        if(StringUtils.isEmpty(pattern)){
            pattern = LOCAL_PATTERN.get(local.getClass());
        }
        return DateTimeFormatter.ofPattern(pattern).format(local);
    }
    /**
     * 字符串转日期
     **/
    public static Date parse(String dateStr){
        return parse(dateStr,StringUtils.EMPTY);
    }

    /**
     * 字符串转日期
     **/
    public static Date parse(String dateStr,String pattern){
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        if(StringUtils.isEmpty(pattern)){
            pattern = Constant.DATE_DEFAULT_PATTERN;
        }
        SimpleDateFormat simpleDateFormat = getPatternFormat(pattern);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转日期
     **/
    public static LocalDateTime parseLocal(String dateStr){
        return parseLocal(dateStr,StringUtils.EMPTY);
    }

    /**
     * 字符串转日期
     **/
    public static LocalDateTime parseLocal(String dateStr,String pattern){
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        if(StringUtils.isEmpty(pattern)){
            pattern = Constant.DATE_DEFAULT_PATTERN;
        }
        return  dateToLocal(parse(dateStr, pattern));
    }

    public static Date localToDate(LocalDateTime localDateTime){
        return localToDate(localDateTime,ZoneId.systemDefault());
    }

    public static Date localToDate(LocalDateTime localDateTime,ZoneId zone){
        if(localDateTime == null){
            return null;
        }
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localToDate(LocalDate localDate){
        return localToDate(localDate,ZoneId.systemDefault());
    }

    public static Date localToDate(LocalDate localDate,ZoneId zone){
        if(localDate == null){
            return null;
        }
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localToDate(LocalTime localTime){
        return localToDate(localTime,ZoneId.systemDefault());
    }

    public static Date localToDate(LocalTime localTime,ZoneId zone){
        if(localTime == null){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(),localTime);
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime dateToLocal(Date date){
        return dateToLocal(date,ZoneId.systemDefault());
    }

    public static LocalDateTime dateToLocal(Date date,ZoneId zoneId){
        if(date == null){
            return null;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime;
    }

    public static void main(String[] args) {
        LocalDateTime localDate = parseLocal("2020-07-20 10:20:20");
        System.out.println(localDate.toLocalTime());
    }

}
