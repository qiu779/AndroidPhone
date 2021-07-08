package com.example.myphone.dao;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.myphone.mode.Calls;
import com.example.myphone.mode.Contacts;
import com.example.myphone.mode.NameSortModel;
import com.example.myphone.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DBController {
    private String TAG = "DBController";
    private MyDataBaseHelper myDataBaseHelper;
    private Context context;
    private SQLiteDatabase db;
    private static final String TABLE_NAME1 = "Calls";
    private static final String TABLE_NAME2 = "Contacts";

    public DBController(Context context) {
        this.context = context;
        myDataBaseHelper = new MyDataBaseHelper(context, null);
    }

    /*
    获取数据库中的通话记录,通话记录每个人或者每个电话号码的只显示最近的一个
     */
    public List<Calls> getPhoneRvItem() {
        List<Calls> datas = new ArrayList<>();
        SQLiteDatabase db = myDataBaseHelper.getReadableDatabase();
        Cursor cursora = db.query(TABLE_NAME1, new String[]{"id"}, null, null, null, null, null);
        if (cursora.getCount() > 0){
            while (cursora.moveToNext()){
                String id = String.valueOf(cursora.getInt(0));
                Cursor cursor = db.query(TABLE_NAME1, null, "contact_id =?", new String[]{id}, null, null, "date desc");
                if (cursor != null && cursor.getCount() > 0){
                    cursor.moveToFirst();
                    Calls calls = new Calls();
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String number = cursor.getString(cursor.getColumnIndex("number"));
                    if (name == null || "".equals(name)) {
                        calls.setName(cursor.getString(cursor.getColumnIndex("number")));
                    } else {
                        calls.setName(name);
                    }
                    calls.setPhoneNumber(number);
                    //将取出的日期进行格式的转换
                    Long date = Long.parseLong((cursor.getString(cursor.getColumnIndex("date"))));
                    calls.setDate(date);
                    calls.setType(cursor.getInt(cursor.getColumnIndex("type")));
                    calls.setNetName(cursor.getString(cursor.getColumnIndex("category")));
                    calls.setCallDuration(cursor.getString(cursor.getColumnIndex("callDuration")));
                    calls.setArea(cursor.getString(cursor.getColumnIndex("area")));
                    calls.setContact_id(cursor.getInt(cursor.getColumnIndex("contact_id")));
                    datas.add(calls);
                }
                cursor.close();
            }
        }
        Cursor cursor1 = db.query(TABLE_NAME1, null, "contact_id is null", null, "number", null, null);
        if (cursor1 !=null && cursor1.getCount() != 0){
            while (cursor1.moveToNext()){
                Calls calls = new Calls();
                String name = cursor1.getString(cursor1.getColumnIndex("name"));
                String number = cursor1.getString(cursor1.getColumnIndex("number"));
                if (name == null || "".equals(name)) {
                    calls.setName(cursor1.getString(cursor1.getColumnIndex("number")));
                } else {
                    calls.setName(name);
                }
                calls.setPhoneNumber(number);
                //将取出的日期进行格式的转换
                Long date = Long.parseLong((cursor1.getString(cursor1.getColumnIndex("date"))));
                calls.setDate(date);
                calls.setType(cursor1.getInt(cursor1.getColumnIndex("type")));
                calls.setNetName(cursor1.getString(cursor1.getColumnIndex("category")));
                calls.setCallDuration(cursor1.getString(cursor1.getColumnIndex("callDuration")));
                calls.setArea(cursor1.getString(cursor1.getColumnIndex("area")));
                datas.add(calls);
            }
        }
        Collections.sort(datas);

        cursora.close();
        cursor1.close();
        //db.close();
        return datas;
    }


    public int getPhoneRvItemCount() {

        int temp = 0;
        List<Calls> datas = new ArrayList<>();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursora = db.query(TABLE_NAME1, new String[]{"id"}, null, null, null, null, null);
        if (cursora.getCount() > 0){
            while (cursora.moveToNext()){
                String id = String.valueOf(cursora.getInt(0));
                Cursor cursor = db.query(TABLE_NAME1, null, "contact_id =?", new String[]{id}, null, null, "date desc");
                if (cursor != null && cursor.getCount() > 0){
                    temp++;
                }
                cursor.close();
            }
        }
        Cursor cursor1 = db.query(TABLE_NAME1, null, "contact_id is null", null, "number", null, null);
        if (cursor1 !=null){
            temp= temp+ cursor1.getCount();
        }

        cursora.close();
        cursor1.close();
        //db.close();
        return temp;
    }


    /*
    获取数据库中的联系人填充recyclerview
     */
    public List<NameSortModel> getContactRvItem() {
        List<NameSortModel> datas = new ArrayList<>();
        db = myDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, new String[] {"id", "name"}, null, null, null, null, "name");
        if (cursor.getCount() == 0){
            cursor.close();
            return datas;
        }
        while (cursor.moveToNext()) {
            NameSortModel nameSortModel = new NameSortModel();
            nameSortModel.setId(cursor.getInt(0));
            nameSortModel.setName(cursor.getString(1));
            datas.add(nameSortModel);
        }
        Log.d("getContactRvItem =", String.valueOf(cursor.getCount()));
        cursor.close();
        ////db.close();
        return datas;
    }

    public int getContactRvItemCount() {
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, new String[] {"id", "name"}, null, null, null, null, "name");
        if (cursor.getCount() == 0){
            cursor.close();
            return 0;
        }
        int temp = cursor.getCount();
        cursor.close();
        //db.close();
        return temp;
    }

    /*
    通过id获取电话号码
     */
    public NameSortModel getPhoneNumberAnd2(int id){
        NameSortModel datas = new NameSortModel();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, new String[] {"number", "number2"}, "id ="+ id, null, null, null, "name");
        while (cursor.moveToNext()) {
            datas.setPhonenumber(cursor.getString(0));
            datas.setPhonenumber2(cursor.getString(1));
        }
        cursor.close();
        //db.close();
        return datas;
    }

    /*
    通过id获取联系人的全部信息
     */
    public Contacts getContact(int id){
        Contacts contacts = new Contacts();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor =db.query(TABLE_NAME2, null, "id = ?", new String[] {String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        contacts.setName(cursor.getString(cursor.getColumnIndex("name")));
        contacts.setPhoneNumber(cursor.getString(cursor.getColumnIndex("number")));
        contacts.setPhoneNumber2(cursor.getString(cursor.getColumnIndex("number2")));
        contacts.setNetName(cursor.getString(cursor.getColumnIndex("netName")));
        contacts.setArea(cursor.getString(cursor.getColumnIndex("area")));
        contacts.setNetName2(cursor.getString(cursor.getColumnIndex("netName2")));
        contacts.setArea2(cursor.getString(cursor.getColumnIndex("area2")));
        contacts.setBirthDay(cursor.getString(cursor.getColumnIndex("birthDay")));
        contacts.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        contacts.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        contacts.setGroupName(cursor.getString(cursor.getColumnIndex("groupName")));
        contacts.setBlacklist(cursor.getInt(cursor.getColumnIndex("blacklist")));
        return contacts;
    }

    /*
    通过id获取所有通话记录
     */
    public List<Calls> getAllCallLogs(int id){
        List<Calls> list = new ArrayList<>();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor =db.query(TABLE_NAME1, null, "contact_id = ?", new String[] {String.valueOf(id)}, null, null, "date desc");
        while (cursor.moveToNext()){
            Calls calls =new Calls();
            calls.setId(cursor.getInt(cursor.getColumnIndex("id")));
            calls.setName(cursor.getString(cursor.getColumnIndex("name")));
            calls.setPhoneNumber(cursor.getString(cursor.getColumnIndex("number")));
            calls.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            calls.setType(cursor.getInt(cursor.getColumnIndex("type")));
            calls.setNetName(cursor.getString(cursor.getColumnIndex("category")));
            calls.setArea(cursor.getString(cursor.getColumnIndex("area")));
            calls.setContact_id(cursor.getInt(cursor.getColumnIndex("contact_id")));
            list.add(calls);
        }
        return list;
    }

    /*
    联系人通过id获取三个通话记录
    */
    public List<Calls> getCallLogs(int id){
        List<Calls> list = new ArrayList<>();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor =db.query(TABLE_NAME1, null, "contact_id = ?", new String[] {String.valueOf(id)}, null, null, "date asc");
        int i =0;
        while (cursor.moveToNext()){
            Calls calls =new Calls();
            calls.setId(cursor.getInt(cursor.getColumnIndex("id")));
            calls.setName(cursor.getString(cursor.getColumnIndex("name")));
            calls.setPhoneNumber(cursor.getString(cursor.getColumnIndex("number")));
            calls.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            calls.setType(cursor.getInt(cursor.getColumnIndex("type")));
            calls.setNetName(cursor.getString(cursor.getColumnIndex("category")));
            calls.setArea(cursor.getString(cursor.getColumnIndex("area")));
            calls.setContact_id(cursor.getInt(cursor.getColumnIndex("contact_id")));
            list.add(calls);
            i++;
            if (i > 4){
                return list;
            }
        }
        cursor.close();
        //db.close();
        return list;
    }

    /*
    陌生人通过id获取三个通话记录
    */
    public List<Calls> getFCallLogs(String number){
        List<Calls> list = new ArrayList<>();
        db = myDataBaseHelper.getReadableDatabase();
        Cursor cursor =db.query(TABLE_NAME1, null, "number = ?", new String[] {number}, null, null, "date asc");
        int i =0;
        while (cursor.moveToNext()){
            Calls calls =new Calls();
            calls.setId(cursor.getInt(cursor.getColumnIndex("id")));
            calls.setName(cursor.getString(cursor.getColumnIndex("name")));
            calls.setPhoneNumber(cursor.getString(cursor.getColumnIndex("number")));
            calls.setDate(cursor.getLong(cursor.getColumnIndex("date")));
            calls.setType(cursor.getInt(cursor.getColumnIndex("type")));
            calls.setNetName(cursor.getString(cursor.getColumnIndex("category")));
            calls.setArea(cursor.getString(cursor.getColumnIndex("area")));
            calls.setContact_id(cursor.getInt(cursor.getColumnIndex("contact_id")));
            list.add(calls);
            i++;
            if (i > 4){
                return list;
            }
        }
        cursor.close();
        //db.close();
        return list;
    }
    /*
    更新blackList
     */
    public void Update_blackList(int id, int value){
        db =myDataBaseHelper.getWritableDatabase();
        String sql = "update " + TABLE_NAME2 + " set blacklist = '" + value + "' where id = " + id ;
        db.execSQL(sql);
        //db.close();
    }

    /*
    对Contacts中的netName添加运营商
     */
    public void UpdateNum_netName(int id, String value){
        db = myDataBaseHelper.getWritableDatabase();
        String sql = "update " + TABLE_NAME2 + " set netName = '" + value + "' where id = " + id ;
        db.execSQL(sql);
        //db.close();
    }

    /*
    对Contacts中的netName2添加运营商
    */
    public void UpdateNum_netName2(int id, String value){
        db = myDataBaseHelper.getWritableDatabase();
        String sql = "update " + TABLE_NAME2 + " set netName2 = '" + value + "' where id = " + id ;
        db.execSQL(sql);
        //db.close();
    }

    /*
    对Contacts中的area添加归属地
     */
    public void UpdateNum_Area(int id, String value){
        db =myDataBaseHelper.getWritableDatabase();
        String sql = "update " + TABLE_NAME2 + " set area = '" + value + "' where id = " + id ;
        db.execSQL(sql);
        //db.close();
    }

    /*
    对Contacts中的area2添加归属地
     */
    public void UpdateNum_Area2(int id, String value){
        db =myDataBaseHelper.getWritableDatabase();
        String sql = "update " + TABLE_NAME2 + " set area2 = '" + value + "' where id = " + id ;
        db.execSQL(sql);
        //db.close();
    }


    /*
    删除联系人
     */
    public void Delete_contact(List<Integer> idList){
        db = myDataBaseHelper.getWritableDatabase();
        for (Integer id : idList){
            String sql = "delete from " + TABLE_NAME2 + " where id = " + id;
            db.execSQL(sql);
        }
        //db.close();
    }

    /*
删除通话记录
 */
    public void Delete_Calls(List<Integer> idList){
        db = myDataBaseHelper.getWritableDatabase();
        for (Integer id : idList){
            String sql = "delete from " + TABLE_NAME1 + " where id = " + id;
            db.execSQL(sql);
        }
        //db.close();
    }

    /*
    将新的通话记录添加到Calls表中
     */
    public void InsertCall(Calls calls){
        db =myDataBaseHelper.getReadableDatabase();
        String name = calls.getName();
        String number = calls.getPhoneNumber();
        long date = calls.getDate();
        int type = calls.getType();
        String area = calls.getArea();
        String netName = calls.getNetName();
        String callDuration = calls.getCallDuration();
        int contact_id = calls.getContact_id();
        Log.d(TAG, String.valueOf(contact_id));
        Log.d(TAG, name);
        Log.d(TAG, number);
        String sql = "insert into " + TABLE_NAME1 + " values (null,'"
                + name + "','"
                + number + "',"
                + date + ","
                + type + ",'"
                + netName + "','"
                + callDuration + "','"
                + area + "',"
                + contact_id + ") ";
        db.execSQL(sql);
        //db.close();
    }

    /*
    将新的联系人插入Contacts数据库中
     */
    public void InsertContact(Contacts contacts){
        String name = contacts.getName();
        String number1 = contacts.getPhoneNumber();
        String number2 = contacts.getPhoneNumber2();
        String netName1 = contacts.getNetName();
        String area1 = contacts.getArea();
        String netName2 = contacts.getNetName2();
        String area2 = contacts.getArea2();
        String email = contacts.getEmail();
        String birthday = contacts.getBirthDay();
        String address = contacts.getAddress();
        String more = contacts.getMore();
        String group = contacts.getGroupName();
        int blacklist = contacts.getBlacklist();
        db = myDataBaseHelper.getWritableDatabase();
        String sql = "insert into " + TABLE_NAME2 + " values (null,'"
                + name + "','"
                + number1 + "','"
                + number2 + "','"
                + netName1 + "','"
                + area1 + "','"
                + netName2 + "','"
                + area2 + "','"
                + birthday + "','"
                + email + "','"
                + address + "','"
                + group + "',"
                + blacklist + ") ";
        db.execSQL(sql);
        //db.close();
    }


    /*
    判断新的通话记录是否为联系人的号码1,如果是则取出姓名和id
     */

    public Calls CheckContact(String number){
        Calls calls = new Calls();
        db = myDataBaseHelper.getReadableDatabase();

        //判断是否为号码1的
        Cursor cursor1 =db.query(TABLE_NAME2, new String[]{"id","name","netName","area"}, "number = ?", new String[] {"18396329095"}, null, null, null);
        if (cursor1 == null || cursor1.getCount() == 0){
            calls.setContact_id(0);
        }else {
            cursor1.moveToNext();
            calls.setContact_id(cursor1.getInt(0));
            calls.setName(cursor1.getString(1));
            calls.setNetName(cursor1.getString(2));
            calls.setArea(cursor1.getString(3));
        }

        if (calls.getId() == 0){
            //判断是否为号码2的
            Cursor cursor2 =db.query(TABLE_NAME2, new String[]{"id","name","netName","area"}, "number2 = ?", new String[] {number}, null, null, null);
            if (cursor2.getCount() == 0){
                calls.setContact_id(0);
            }else {
                cursor2.moveToNext();
                calls.setContact_id(cursor2.getInt(0));
                calls.setName(cursor2.getString(1));
                calls.setNetName(cursor2.getString(2));
                calls.setArea(cursor2.getString(3));
            }
            cursor2.close();
        }
        //db.close();
        return calls;
    }





//    /*
//删除通话记录
// */
//    public void DeleteCall(){
//        db = myDataBaseHelper.getWritableDatabase();
//            String sql = "delete from " + TABLE_NAME1 + " where id > 1000";
//            db.execSQL(sql);
//        ////db.close();
//    }



    /*
   因为没法获得通话时间，以及通话类型的判断，直接从本地数据库中拉取当前电话的相关信息
   */
    public Calls getLocalCalls(String number) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1000);
        }
        Calls calls = new Calls();
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,new String[]{CallLog.Calls.TYPE, CallLog.Calls.DURATION, CallLog.Calls.DATE}, "number = ?", new String[]{number}, CallLog.Calls.DATE + " desc");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            calls.setType(Integer.parseInt(cursor.getString(0)));
            calls.setDate(Long.parseLong(cursor.getString(1)));
            calls.setCallDuration(cursor.getString(2));
        }
        return calls;
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
                }, null, null, CallLog.Calls.DATE);
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
                        + area + "',null)");
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
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        if (data1.indexOf("'")>-1){
                            name = "'" + data1;
                        }else {
                            name = data1;
                        }
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                        number = data1.replace(" ", "");
                    }
                }
                db.execSQL("insert into Contacts values (null,'"
                        + name + "','"
                        + number + "','"
                        + "" + "','"
                        + "" + "','"
                        + "" + "','"
                        + "" + "','"
                        + "" + "','"
                        + "" + "','"
                        + "1481604982@qq.com" + "','"
                        + "" + "','"
                        + "" + "',"
                        + 0 + ")");
                dataCursor.close();
            }
        }
        //db.close();
    }

    /*
    从本地数据库读出数据后，对Contacts和Calls建立联系，联系外键
     */
    public void setContact_id(){
        db = myDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, new String[] {"id", "number", "number2"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id =cursor.getInt(0);
            String number = cursor.getString(1).replace(" ","");
            String number2 = cursor.getString(2).replace(" ","");
            if (number != null && !"".equals(number)){
                String sql = " update " + TABLE_NAME1 + " set contact_id = " + id + " where number = " + number;
                db.execSQL(sql);
            }
            if (number2 != null && !"".equals(number2)){
                String sql = " update " + TABLE_NAME1 + " set contact_id = " + id + " where number2 = " + number2;
                db.execSQL(sql);
            }
        }
        cursor.close();
        String sql = "update " + TABLE_NAME1 + " set name = '' where contact_id is null";
        db.execSQL(sql);
        //db.close();
    }

    public void getread(){
        db = myDataBaseHelper.getWritableDatabase();
    }
}
