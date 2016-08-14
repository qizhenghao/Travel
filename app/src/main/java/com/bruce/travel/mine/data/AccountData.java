package com.bruce.travel.mine.data;

/**
 * Created by 梦亚 on 2016/8/9.
 */
public class AccountData implements Cloneable {
    public int id;
    public String name;
    public String phone;
    public String password;

    public AccountData() {

    }

    public AccountData(int id, String name,String phone,String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
