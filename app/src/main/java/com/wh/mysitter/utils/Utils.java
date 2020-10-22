package com.wh.mysitter.utils;

import android.content.SharedPreferences;

import java.util.Objects;

public class Utils {
    public static String ensure2Numbers(int num) {
        if (num >= 0 && num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }
}
