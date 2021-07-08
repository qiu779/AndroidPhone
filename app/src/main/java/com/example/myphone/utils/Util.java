package com.example.myphone.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CallLog;

import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getTypeStr(int type, int duration) {
        String typeStr = "";
        String durationStr = "";
        int hour = duration / 3600;
        int min = duration / 60;
        int sec = duration % 60;
        if (hour != 0) {
            durationStr = hour + "时";
        }
        if (min != 0) {
            durationStr = min + "分";
        }
        if (sec != 0) {
            durationStr = sec + "秒";
        }
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                typeStr = "呼入" + durationStr;
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                if (duration != 0) {
                    typeStr = "呼出" + durationStr;
                } else {
                    typeStr = "未接通";
                }
                break;
            case CallLog.Calls.MISSED_TYPE:
                typeStr = "未接，响铃..";
                break;
            case CallLog.Calls.BLOCKED_TYPE:
                typeStr = "阻止";
                break;
            case CallLog.Calls.REJECTED_TYPE:
                typeStr = "已挂断";
                break;
            case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                typeStr = "已接";
                break;
        }
        return typeStr;
    }

    public static String getDateStr(Date callDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date_today = sdf.format(new Date(System.currentTimeMillis()));
        String callDateStr = sdf.format(callDate);
        if (date_today.equals(callDateStr)) {                           //如果为当天则显示时间
            sdf = new SimpleDateFormat("HH:mm");
            callDateStr = sdf.format(callDate);
        } else if (date_today.contains(callDateStr.substring(0, 4))) {  //如果为当年则不显示年份
            sdf = new SimpleDateFormat("MM-dd");
            callDateStr = sdf.format(callDate);
        }
        return callDateStr;
    }

    public static String getAllDateStr(Date callDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date_today = sdf.format(new Date(System.currentTimeMillis()));
        String callDateStr = sdf.format(callDate);
        if (date_today.equals(callDateStr)) {                           //如果为当天则显示时间
            sdf = new SimpleDateFormat("HH:mm");
            callDateStr = sdf.format(callDate);
        } else if (date_today.contains(callDateStr.substring(0, 4))) {  //如果为当年则不显示年份
            sdf = new SimpleDateFormat("MM-dd HH:mm");
            callDateStr = sdf.format(callDate);
        }
        return callDateStr;
    }

    public static void callPhone(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE}, 1000);
        }
        context.startActivity(intent);
    }
}
