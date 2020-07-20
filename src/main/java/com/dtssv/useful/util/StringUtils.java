package com.dtssv.useful.util;

/**
 * 字符串工具类
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(final CharSequence str){
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence str){
        return !isEmpty(str);
    }
}
