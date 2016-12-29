package com.haier.ai.bluetoothspeaker.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * author: qu
 * date: 16-11-4
 * introduce: 日志相关类
 */

public class LogUtil {
    private static final String DEFALUT_TAG = "Logger";

    public static void LogD(String tag, String msg){
        String TAG = null;

        if(TextUtils.isEmpty(tag)){
            TAG = DEFALUT_TAG;
        }

        TAG = tag;

        Log.d(TAG, msg);
    }

    public static void LogI(String tag, String msg){
        String TAG = null;

        if(TextUtils.isEmpty(tag)){
            TAG = DEFALUT_TAG;
        }

        TAG = tag;

        Log.i(TAG, msg);
    }

    public static void LogE(String tag, String msg){
        String TAG = null;

        if(TextUtils.isEmpty(tag)){
            TAG = DEFALUT_TAG;
        }

        TAG = tag;

        Log.e(TAG, msg);
    }
}
