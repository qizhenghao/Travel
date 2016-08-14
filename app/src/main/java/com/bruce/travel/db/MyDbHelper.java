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
        SQLiteDatabase db = this.getWritableDatabase();
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

//    private DatabaseHelper mDbHelper;
//    private SQLiteDatabase  mDb;
//    private static MyDbHelper openHelper = null;
//    private static int version = 1;
//    private static String mDbName = "mydb";
//
//    private static String TableNames[];
//    private static String FiledNames[][];
//    private static String FiledTypes[][];
//    private static String NO_CREATE_TABLE = "no tables";
//    private static String message = "";
//
//    private final Context mCtx;
//
//    public MyDbHelper(Context ctx) {
//        this.mCtx = ctx;
//    }
//
//    public static MyDbHelper getInstance(Context context) {
//        if(openHelper == null) {
//            openHelper = new MyDbHelper(context);
//            TableNames = MyDbInfo.getTableNames();
//            FiledNames = MyDbInfo.getFieldNames();
//            FiledTypes = MyDbInfo.getFiledTypes();
//        }
//        return openHelper;
//    }
//
//    private static class DatabaseHelper extends SQLiteOpenHelper {
//
//
//        public DatabaseHelper(Context context) {
//            super(context, mDbName, null, version);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            if(TableNames == null) {
//                message = NO_CREATE_TABLE;
//            }
//            for(int i = 0; i < TableNames.length; i++) {
//                String sql = "CREATE TABLE " + TableNames[i] + " (";
//                for(int j = 0; j < FiledNames[i].length; j++) {
//                    sql += FiledNames[i][j] + " " + FiledTypes[i][j] + ",";
//                }
//                sql = sql.substring(0,sql.length() - 1);
//                sql += ")";
//                db.execSQL(sql);
//            }
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
//            for(int i = 0; i < TableNames[i].length(); i ++) {
//                String sql = "DROP TABLE IF EXITS " + TableNames[i];
//                db.execSQL(sql);
//            }
//            onCreate(db);
//        }
//    }
//
//    public void insertTables(String[] tableNames, String[][] filedNames, String[][] filedTypes) {
//        TableNames = tableNames;
//        FiledNames = filedNames;
//        FiledTypes = filedTypes;
//    }
//
//    public MyDbHelper open() throws SQLException {
//        mDbHelper = new DatabaseHelper(mCtx);
//        mDb = mDbHelper.getWritableDatabase();
//        return this;
//    }
//
//    public void close() {
//        mDbHelper.close();
//    }
//
//    public void execSQL(String sql) throws java.sql.SQLException{
//        mDb.execSQL(sql);
//    }
//
//    public Cursor rawQuery(String sql, String[] selectionArgs) {
//        Cursor cursor = mDb.rawQuery(sql, selectionArgs);
//        return cursor;
//    }
//
//    public  Cursor  select(String table){
//        Cursor cursor = mDb.rawQuery("select distinct * from "+ table, null);
//
//        return cursor;
//    }
//
//    public Cursor select(String table, String[] columns, String selection, String[]
//            selectionArgs, String groupBy, String having, String orderBy) {
//        Cursor cursor = mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
//        return cursor;
//    }
//
//    public long insert(String table, String fields[], String values[]) {
//        ContentValues cv = new ContentValues();
//        for(int i = 0; i < fields.length; i++) {
//            cv.put(fields[i], values[i]);
//        }
//        return mDb.insert(table,null,cv);
//    }
//
//    public int delete(String table, String where, String[] whereValue) {
//        return mDb.delete(table, where, whereValue);
//    }
//
//    public int update(String table, String updateFields[], String updateValues[], String where, String[] whereValue) {
//        ContentValues cv = new ContentValues();
//        for(int i = 0; i < updateFields.length; i++) {
//            cv.put(updateFields[i], updateValues[i]);
//        }
//        return mDb.update(table, cv, where, whereValue);
//    }
//
//    public String getMessage() {
//        return message;
//    }
}
