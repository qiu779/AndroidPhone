package com.example.myphone.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myphone.dao.DBController;
import com.example.myphone.mode.Calls;
import com.example.myphone.utils.MobileNumberUtils;

import java.util.Date;

public class PhoneService extends Service {

    private String TAG = "phoneService";
    private MyBinder mBinder;
    private DBController dbController;
    private int flag = -1;
    private static String number = "";

    public static class myReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            number = intent.getStringExtra("number");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        PhoneService getService() {
            return PhoneService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbController = new DBController(this);
        Calls calls = dbController.CheckContact(number);
        Calls temp = dbController.getLocalCalls(number);
        calls.setPhoneNumber(number);
        calls.setDate(new Date().getTime());
        calls.setType(temp.getType());
        calls.setCallDuration(temp.getCallDuration());
        int contact_id = calls.getContact_id();
        Log.d(TAG, String.valueOf(contact_id));
        calls.setContact_id(10);
        if (contact_id != 0){
            //为联系人
            dbController.InsertCall(calls);
            Log.d(TAG, "插入成功");
        }else {
            //不为联系人
            calls.setNetName(MobileNumberUtils.getNetName(number, 86));
            calls.setArea(MobileNumberUtils.getArea(number));
            dbController.InsertCall(calls);
            Log.d(TAG, "插入成功");
        }
    }


}
