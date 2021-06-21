package com.example.myphone.dao;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CallLog;
import android.telecom.Call;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.myphone.utils.Calls;
import com.example.myphone.utils.Contacts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBController {
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;
    private Context context;

    private static final String TABLE_NAME1 = "Calls";
    private static final String TABLE_NAME2 = "Contacts";

    public DBController(Context context) {
        this.context = context;
        myDataBaseHelper = new MyDataBaseHelper(context, null);
    }

    /*
    获取数据库中的通话记录
     */
    public List<Calls> getPhoneRvItem() {
        List<Calls> datas = new ArrayList<>();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME1, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Calls calls = new Calls();
            if ("".equals(cursor.getString(cursor.getColumnIndex("name")))) {
                calls.setName(cursor.getString(cursor.getColumnIndex("number")));
            } else {
                calls.setName(cursor.getString(cursor.getColumnIndex("name")));
            }
            calls.setPhoneNumber(cursor.getString(cursor.getColumnIndex("number")));
            //将取出的日期进行格式的转换
            Long date = Long.parseLong((cursor.getString(cursor.getColumnIndex("date"))));
            calls.setDate(date);
            calls.setType(cursor.getInt(cursor.getColumnIndex("type")));
            calls.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            calls.setCallDuration(cursor.getString(cursor.getColumnIndex("callDuration")));
            calls.setArea(cursor.getString(cursor.getColumnIndex("area")));
            datas.add(calls);
        }
        cursor.close();
        //db.close();
        return datas;
    }

    /*
    获取数据库中的联系人
     */
    public List<Contacts> getContactRvItem() {
        List<Contacts> datas = new ArrayList<>();
        db = myDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, new String[] {"name"}, null, null, null, null, "name");
        while (cursor.moveToNext()) {
            Contacts contacts = new Contacts();
            contacts.setName(cursor.getString(0));
            datas.add(contacts);
        }
        cursor.close();
        //db.close();
        return datas;
    }

    /*
    获取手机本地数据库的通话记录,并存入当前数据库中
    */
    public void getLocalHistoryList() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1000);
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                new String[]{
                        CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.TYPE,
                        CallLog.Calls.DATE,
                        CallLog.Calls.DURATION,
                        CallLog.Calls.GEOCODED_LOCATION,
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        db = myDataBaseHelper.getWritableDatabase();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                int type = Integer.parseInt(cursor.getString(2));
                Long tempDate = Long.parseLong(cursor.getString(3));
                String duration = cursor.getString(4);
                String area = cursor.getString(5);
                if (name == null) {
                    name = "";
                }
                if (area == null) {
                    area = "";
                }else{
                    if (area.indexOf("省") > -1){
                        area = area.replace("省","");
                    }
                    if (area.indexOf("市") > -1){
                        area = area.replace("市","");
                    }
                }
                db.execSQL("insert into Calls values (null,'"
                        + name + "','"
                        + number + "',"
                        + tempDate + ","
                        + type + ","
                        + "'未知'" + ","
                        + duration + ",'"
                        + area + "')");
            }
        }
        cursor.close();
        //db.close();
    }

    /*
    获取手机本地数据库的联系人,并存入当前数据库中
    */
    public void getLocalContacts() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1000);
        }
        Uri raw_contacts_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data_uri = Uri.parse("content://com.android.contacts/data");
        db = myDataBaseHelper.getWritableDatabase();
        Cursor cursor = context.getContentResolver().query(raw_contacts_uri, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("contact_id"));
            if (id != null) {
                Cursor dataCursor = context.getContentResolver().query(data_uri, null, "raw_contact_id = ? ",
                        new String[]{id}, null);
                String name = "";
                String number = "";
                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                    String mimetype = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                    Log.d("id", "contact_id = " + id);
                    Log.d("data1", "data1 = " + data1);
                    Log.d("mimetype", "mimetype = " + mimetype);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        if (data1.indexOf("'")>-1){
                            name = "'" + data1;
                        }else {
                            name = data1;
                        }
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                        number = data1;
                    }
                }
                db.execSQL("insert into Contacts values (null,'"
                        + name + "','"
                        + number + "','"
                        + "" + "','"
                        + "" + "','"
                        + "" + "',"
                        + 0 + ",'"
                        + "" + "','"
                        + "" + "','"
                        + "" + "',"
                        + 0 + ")");
                dataCursor.close();
            }
        }
        //db.close();
    }

    public void getread(){
        db = myDataBaseHelper.getWritableDatabase();
    }
}
