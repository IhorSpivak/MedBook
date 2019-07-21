package ua.profarma.medbook.utils;

import android.util.Log;

import ua.profarma.medbook.BuildConfig;

public class LogUtils {

    private static final String TAG = "Medbook";

    /**
     * DBGE == Log debug(Log.d)
     *
     * @param tag
     * @param msg
     */
    public static void logD(final String tag, final String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag == null || tag.isEmpty() ? TAG : tag,
                    msg == null || msg.isEmpty() ? "Debug " : msg);
        }
    }

    /**
     * DBGE == Log errors(Log.e)
     *
     * @param tag
     * @param msg
     */
    public static void logE(final String tag, final String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag == null || tag.isEmpty() ? TAG : tag,
                    msg == null || msg.isEmpty() ? "Error! " : msg);
        }
    }

    /**
     * DBGE == Log warning(Log.w)
     *
     * @param tag
     * @param msg
     */
    public static void logW(final String tag, final String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag == null || tag.isEmpty() ? TAG : tag,
                    msg == null || msg.isEmpty() ? "Warning " : msg);
        }
    }

    /**
     * DBGE == Log info(Log.i)
     *
     * @param tag
     * @param msg
     */
    public static void logI(final String tag, final String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag == null || tag.isEmpty() ? TAG : tag,
                    msg == null || msg.isEmpty() ? "Info " : msg);
        }
    }
}
