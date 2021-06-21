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
    private static final int DATABASE_VERSION = 5;

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
            + "area varchar(20) not null)";

    private static final String CREATE_Contacts = "create table " + TABLE_NAME2 + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "number1 varchar(20) not null,"
            + "number2 varchar(20) not null,"
            + "netName varchar(20) not null,"
            + "area varchar(20) not null,"
            + "birthDay varchar(20) not null,"
            + "email varchar(20) not null,"
            + "address varchar(30) not null,"
            + "groupName varchar(20) not null,"
            + "blacklist boolean not null)";


    public MyDataBaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.d(TAG,"Create database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_Calls);
        Log.d(TAG,"Create Calls");
        db.execSQL(CREATE_Contacts);
        Log.d(TAG,"Create Contacts");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists Calls");
        db.execSQL("drop table if exists Contacts");
        onCreate(db);
    }
}
