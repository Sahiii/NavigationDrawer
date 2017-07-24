package com.example.manishacomputer.navigation.DbHndler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MANISHA COMPUTER on 7/20/2017.
 */

public class User_profile extends SQLiteOpenHelper {

    public static final String db_name="profile";
    public static final String table_name="user_profile";
    public static final String table_name1="search";
    public static final String col_1=" _id";
    public static final String col_2="email";
    public static final String col_3="password";
    public static final String col_4="blood_group";
    public static final String col_5="username";
    public static final String col_6="phone";

    public User_profile(Context context) {
        super(context, db_name, null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "( _id integer primary key autoincrement ,email text,password text)");
        db.execSQL("create table " + table_name1 + "( _id integer primary key autoincrement ,blood_group text,username text, phone text)");

        System.out.println("Database "+db_name);
        System.out.println("TAble "+table_name);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + table_name);
        db.execSQL("drop table if exists" + table_name1);
        // db.execSQL("ALTER TABLE "+ table_name +" ADD "+ col_1 +" INTEGER PRIMARY KEY AUTOINCREMENT");
        onCreate(db);
    }

    public void insertData(String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(col_2,email);
        values.put(col_3,password);
        db.insert(table_name,null,values);
    }

    public void insertData1(String blood_group,String username, String phone){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(col_4,blood_group);
        values.put(col_5,username);
        values.put(col_6,phone);
        db.insert(table_name1,null,values);
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cr=db.rawQuery("select _id ,email,password from user_profile",null);
        if (cr != null) {
            cr.moveToFirst();
        }
        System.out.println("Cursor Sizw :"+cr.getCount());
        cr.getCount();
        return cr;
    }

    public Cursor getData1(String blood_group){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cr=db.rawQuery("select _id ,blood_group,username,phone from search where blood_group like '%"+blood_group+"%'",null);
        if (cr != null) {
            cr.moveToFirst();
        }
        System.out.println("Cursor Size :"+cr.getCount());
        cr.getCount();
        return cr;
    }

    public void DeleteData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String delete=("delete from user_profile");
        db.execSQL(delete);
    }

}
