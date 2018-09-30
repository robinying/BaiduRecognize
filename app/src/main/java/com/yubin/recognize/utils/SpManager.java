package com.yubin.recognize.utils;

import com.blankj.utilcode.util.SPUtils;

import java.util.Map;

/**
 * author : Yubin.Ying
 * time : 2018/9/28
 */
public class SpManager {
    private static final String SPNAME ="recognize";

    private static final SPUtils spUtils = SPUtils.getInstance(SPNAME);

    public static void putInt(String key, int i) {
        spUtils.put(key, i);
    }

    public static int getInt(String key){
       return spUtils.getInt(key);
    }

    public static int getInt(String key,int defaultvalue){
        return spUtils.getInt(key,defaultvalue);
    }

    public static void putString(String key, String value) {
        spUtils.put(key, value);
    }

    public static String getString(String key){
        return  spUtils.getString(key);
    }

    public static String getString(String key,String defaultvalue){
        return spUtils.getString(key,defaultvalue);
    }

    public static void putBoolean(String key, boolean value) {
        spUtils.put(key, value);
    }

    public static boolean getBoolean(String key){
        return spUtils.getBoolean(key);
    }

    public static boolean getBoolean(String key,boolean defaultvalue){
        return spUtils.getBoolean(key,defaultvalue);
    }

    public static void putLong(String key, long value) {
        spUtils.put(key, value);
    }

    public static long getLong(String key){
        return spUtils.getLong(key);
    }

    public static long getLong(String key,long defaultvalue){
        return spUtils.getLong(key,defaultvalue);
    }

    public static void clear() {
        spUtils.clear();
    }

    public static String sp2String() {
        StringBuilder sb = new StringBuilder();
        Map<String, ?> map = spUtils.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return sb.toString();
    }
}
