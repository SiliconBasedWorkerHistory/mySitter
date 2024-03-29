package com.wh.mysitter.utils;


import android.content.Context;
import android.os.BatteryManager;

public class HardwareUtils {
    public static BatteryManager batteryManager;

    public static int getBatteryLevel(Context context) {
        if (HardwareUtils.batteryManager == null) {
            HardwareUtils.batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        }
        return HardwareUtils.batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public static int getBatteryLevel() {
        if (HardwareUtils.batteryManager != null) {
            return HardwareUtils.batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        return -1;
    }
}
