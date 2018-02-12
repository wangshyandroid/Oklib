package com.oklib.utils.logger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Description: 通用的Log管理工具类
 * 开发阶段LOGLEVEL = 6
 * 发布阶段LOGLEVEL = -1
 */

public class Logger {

    public static String mTag = "ebtm";
    private static int LOGLEVEL = 6;
    private static int VERBOSE = 1;
    private static int DEBUG = 2;
    private static int INFO = 3;
    private static int WARN = 4;
    private static int ERROR = 5;


    public static void v(@NonNull String tag, String msg) {
        if (LOGLEVEL > VERBOSE && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).v(msg);
        }
    }

    public static void d(@NonNull String tag, String msg) {
        if (LOGLEVEL > DEBUG && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).d(msg);
        }
    }

    public static void i(@NonNull String tag, String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).i(msg);
        }
    }

    public static void w(@NonNull String tag, String msg) {
        if (LOGLEVEL > WARN && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).w(msg);
        }
    }

    public static void e(@NonNull String tag, String msg) {
        if (LOGLEVEL > ERROR && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).e(msg);
        }
    }

    public static void e(@NonNull String tag, Exception e) {
        tag = checkTag(tag);
        if (LOGLEVEL > ERROR) {
            com.orhanobut.logger.Logger.t(tag).e(e == null ? "Unkonwn Error" : e.getMessage());
        }
    }

    public static void v(String msg) {
        if (LOGLEVEL > VERBOSE && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.v(msg);
        }
    }

    public static void d(String msg) {
        if (LOGLEVEL > DEBUG && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.i(msg);
        }
    }

    public static void w(String msg) {
        if (LOGLEVEL > WARN && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.v(msg);
        }
    }

    public static void e(String msg) {
        if (LOGLEVEL > ERROR && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.e(msg);
        }
    }

    public static void e(Exception e) {
        if (LOGLEVEL > ERROR) {
            com.orhanobut.logger.Logger.e(e == null ? "Unkonwn Error" : e.getMessage());
        }
    }

    public static void wtf(@NonNull String tag, String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).wtf(msg);
        }
    }

    public static void json(@NonNull String tag, String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).json(msg);
        }
    }

    public static void xml(@NonNull String tag, String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            tag = checkTag(tag);
            com.orhanobut.logger.Logger.t(tag).xml(msg);
        }
    }

    public static void wtf(String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.wtf(msg);
        }
    }

    public static void json(String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.json(msg);
        }
    }

    public static void xml(String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            com.orhanobut.logger.Logger.xml(msg);
        }
    }

    private static String checkTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = mTag;
        }
        return tag;
    }
}
