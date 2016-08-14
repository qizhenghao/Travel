package com.bruce.travel.mine.data;

import android.content.Context;

import com.bruce.travel.db.MyDbHelper;
import com.bruce.travel.db.MyDbInfo;
import com.bruce.travel.desktop.ui.DesktopActivity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by 梦亚 on 2016/8/3.
 */
public class CommonData {

//    private static CommonData instance = null;
//    private Context context;
//    private MyDbHelper db = null;
//    private int temp_max_account_id = 0;
//    public HashMap<Integer, AccountData> account = new HashMap<>();
//
//    public static CommonData getInstance() {
//        if(instance == null) {
//            instance = new CommonData();
//        }
//        return instance;
//    }
//
//    public void load(Context context) {
//        db = DesktopActivity.db;
//
//        this.context = context;
//    }
//
//    public boolean existAccount(String name)
//    {
//        for (AccountData adata : account.values()) {
//            if (adata.name.equals(name))
//                return true;
//        }
//
//        return false;
//    }
//
//    /**添加账户*/
//    public boolean addAccount(AccountData adata)
//    {
//        adata.id = temp_max_account_id + 1;
//        account.put(adata.id, adata);
//        String values[] = new String[]{
//                adata.name,
//                adata.phone,
//                adata.password
//        };
//        db.insert(MyDbInfo.getTableNames()[0], new String[]{"NAME","PHONE","PASSWORD"}, values);
//
//
//        return true;
//    }
//
//    /**更新账户*/
//    public void updateAccount(AccountData adata)
//    {
//        account.put(adata.id, adata);
//        String values[] = new String[]{
//                adata.name,
//                adata.phone,
//                adata.password
//        };
//        db.update(MyDbInfo.getTableNames()[0], new String[]{"NAME","PHONE","PASSWORD"}, values, "ID=" + adata.id, null);
//    }
}
