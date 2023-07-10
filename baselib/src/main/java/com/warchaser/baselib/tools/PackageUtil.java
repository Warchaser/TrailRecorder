package com.warchaser.baselib.tools;

import android.app.ActivityManager;
import android.content.Context;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PackageUtil {

    private PackageUtil(){

    }

    public static String getSimpleClassName(Object object){
        Class clazz = object.getClass();
        String str1 = clazz.getName().replace("$", ".");
        String str2 = str1.replace(clazz.getPackage().getName(), "") + ".";

        return str2.substring(1);
    }

    @SuppressWarnings("all")
    public static boolean isBackground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        boolean isBackground = true;
        String processName = "empty";
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                processName = appProcess.processName;
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_CACHED) {
                    isBackground = true;
                } else if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        || appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    isBackground = false;
                } else {
                    isBackground = true;
                }
                break;
            }
        }
        return isBackground;
    }

}
