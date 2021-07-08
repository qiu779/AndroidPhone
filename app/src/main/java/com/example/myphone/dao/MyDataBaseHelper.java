package com.example.myphone.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDataBaseHelper";
    private Context context;
    //说明数据库的名称和版本
    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 14;

    //表名
    private static final String TABLE_NAME1 = "Calls";
    private static final String TABLE_NAME2 = "Contacts";

    private static final String CREATE_Calls = "create table " + TABLE_NAME1 + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "number varchar(20) not null,"
            + "date Date not null,"
            + "type int not null,"
            + "category varchar(20) not null,"
            + "callDuration varchar(20) not null,"
            + "area varchar(20) not null,"
            + "contact_id integer,"
            + "foreign key(contact_id) references " + TABLE_NAME2 +"(id) on delete set null on update cascade)";

    private static final String CREATE_Contacts = "create table " + TABLE_NAME2 + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "number varchar(20) not null,"
            + "number2 varchar(20) not null,"
            + "netName varchar(20) not null,"
            + "area varchar(20) not null,"
            + "netName2 varchar(20) not null,"
            + "area2 varchar(20) not null,"
            + "birthDay varchar(20) not null,"
            + "email email not null,"
            + "address varchar(30) not null,"
            + "groupName varchar(20) not null,"
            + "blacklist boolean not null)";

//    private static final String TRIGGER_UPDATENAME = "create trigger update_name "
//            + "after update of name on " + TABLE_NAME2
//            + " BEGIN "
//            + "update " + TABLE_NAME1
//            + " set name = new.name where id = old.id"
//            + "END;";
//    private static final String TRIGGER_UPDATENUMBER = "create trigger update_number "
//            + "after update of number,number2 on " + TABLE_NAME2
//            + " BEGIN "
//            + "update " + TABLE_NAME1
//            + " set contact_id = null,name = '' where number = old.number;"
//            + "update"
//            + "END;";


    public MyDataBaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_Contacts);
        db.execSQL(CREATE_Calls);
//        db.execSQL(TRIGGER_UPDATENAME);
//        Log.d(TAG,"Create TRIGGER");
    }

    /*
    打开外键约束，默认为关闭
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists Calls");
        db.execSQL("drop table if exists Contacts");
        onCreate(db);
    }
}
