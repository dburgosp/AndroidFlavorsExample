package com.davidburgosprieto.android.androidflavorsexample.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateTime {

    /**
     * Create a private constructor because no one should ever create a {@link MyDateTime}
     * object. This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name MyDateTime (and an object instance of MyDateTime is not
     * needed).
     */
    private MyDateTime() {
    }

    /**
     * Helper method to get a string with the current date in ""yyyy-MM-dd HH:mm:ss" format.
     *
     * @return a string containing the current date.
     */
    public static String getStringCurrentDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
