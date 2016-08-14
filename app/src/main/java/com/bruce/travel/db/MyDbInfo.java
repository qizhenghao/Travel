package com.bruce.travel.db;

/**
 * Created by 梦亚 on 2016/8/9.
 */
public class MyDbInfo {
    private static String TableNames[] = {
            "TBL_ACCOUNT_INFO"
    };

    private static String FiledNames[][] = {
            {"ID","NAME","PHONE","PASSWORD"}

    };

    private static String FiledTypes[][] = {
            {"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","TEXT","TEXT"},

    };

    public MyDbInfo() {

    }

    public static String[] getTableNames() {
        return TableNames;
    }

    public static String[][] getFieldNames() {
        return FiledNames;
    }

    public static String[][] getFiledTypes() {
        return FiledTypes;
    }
}
