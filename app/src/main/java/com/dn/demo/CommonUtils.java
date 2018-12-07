package com.dn.demo;

import android.content.Context;
import android.util.Log;

public class CommonUtils {
    public static final String TEST_FRAGMENT_TAG = "test_fragment_tag";
    private static final String LOG_TAG = "DN_Demo";

    /**
     * px转换sp
     */
    public static float px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale + 0.5f);
    }

    public static void printLogs(String tag, String log) {
        Log.d(LOG_TAG, "[" + tag + "] " + log);
    }
}