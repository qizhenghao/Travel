package com.bruce.travel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by 梦亚 on 2016/8/7.
 */
public class MyDbHelper extends SQLiteOpenHelper{

    private final static int DB_VERSION=1;
    private final static String DB_NAME="userInfo.db";
    private final static String TABLE_NAME="user";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"( u_id integer primary key autoincrement,username text,phone text,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(String name,String phone,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into "+TABLE_NAME+"(username,phone,password) values(?,?,?)", new String[]{name,phone,pwd});
    }

    public  void  update(int id,String text1,String text2){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues  cv = new ContentValues();
        cv.put("name", text1);
        cv.put("pwd", text2);
        db.update(TABLE_NAME, cv, "_id= ?", new String[]{Integer.toString(id)});
    }

    public  void  delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id= ?", new String[]{Integer.toString(id)});
    }

    public  Cursor  select(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct * from "+TABLE_NAME, null);
        System.out.println("2232321");

        return cursor;
    }
}
