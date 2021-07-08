package com.example.myphone.broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.myphone.dao.DBController;
import com.example.myphone.mode.Calls;
import com.example.myphone.service.PhoneService;
import com.example.myphone.utils.MobileNumberUtils;

public class PhoneReceiver extends BroadcastReceiver {

    private String TAG = "PhoneReceiver";
    private DBController dbController;
    private int flag = -1;


    int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbController = new DBController(context);
        String action = intent.getAction();
        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        String tempNumber = "";
        Log.d(TAG, "number"+number);
        while (number != null){
            tempNumber = number;
        }
        // 如果是拨打电话
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Log.i(TAG, "call OUT:" + phoneNumber);
        } else {
// 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                        flag = 1;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(TAG, "RINGING :" + "kkkkkkkkkk");
                    flag = 1;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(TAG, "IDLE :" + "qqqqqqqq");
//                    String IncomingNumber = intent.getStringExtra("incoming_number");
//                        Calls calls = dbController.CheckContact(tempNumber);
//                        Calls temp = dbController.getLocalCalls(tempNumber);
//                        calls.setDate(temp.getDate());
//                        calls.setType(temp.getType());
//                        calls.setCallDuration(temp.getCallDuration());
//                        int contact_id = calls.getContact_id();
//                        Log.d(TAG, String.valueOf(contact_id));
//                        calls.setContact_id(10);
//                        if (contact_id != 0){
//                            //为联系人
//                            dbController.InsertCall(calls);
//                        }else {
//                            //不为联系人
//                            calls.setNetName(MobileNumberUtils.getNetName(tempNumber, 86));
//                            calls.setArea(MobileNumberUtils.getArea(tempNumber));
//                            dbController.InsertCall(calls);
//                        }
                    Intent intent1 = new Intent(context, PhoneService.class);
                    context.startService(intent1);
                    flag = -1;
                    break;
            }
        }
    }
}
