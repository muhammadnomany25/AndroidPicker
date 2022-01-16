package com.alphaapps.androidpickerapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class LocalityUtil {
    private static LocalityUtil localityUtil;

    private LocalityUtil() {

    }

    public static LocalityUtil getInstance() {
        if (localityUtil == null)
            localityUtil = new LocalityUtil();
        return localityUtil;
    }

    public String[] readStringArray(Context context, int strRes) {
        return getArrayStringByLocale(context, strRes);
    }

    public int readColor(Context context, int colRes) {
        return context.getResources().getColor(colRes);
    }


    public void setLocality(Context context, String lang) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        Locale locale = new Locale(lang);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
//            Locale.setDefault(locale);
            configuration.setLocales(localeList);
            configuration.setLocale(locale);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            configuration.setLocale(locale);

        } else {
            configuration.locale = locale;
        }
        res.updateConfiguration(configuration, res.getDisplayMetrics());
    }

    public String getStringByLocale(Context context, int resId) {
        Configuration conf = context.getResources().getConfiguration();
        Resources resources = new Resources(context.getAssets(), context.getResources().getDisplayMetrics(), conf);
        return (resources.getString(resId));
    }

    public String[] getArrayStringByLocale(Context context, int resId) {
        Configuration conf = context.getResources().getConfiguration();
        Resources resources = new Resources(context.getAssets(), context.getResources().getDisplayMetrics(), conf);
        return (resources.getStringArray(resId
        ));
    }

    public int[] readColorArray(Context context, int colRes) {
        return context.getResources().getIntArray(colRes);
    }
}
