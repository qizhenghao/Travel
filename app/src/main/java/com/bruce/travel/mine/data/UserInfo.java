package com.bruce.travel.mine.data;

import java.io.Serializable;

/**
 * Created by 梦亚 on 2016/8/7.
 */
public class UserInfo implements Serializable {

    private int id;
    private String mailbox;
    private String username;
    private String password;
    private String phone;

    public UserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    public UserInfo(String username, String phone,String password) {
        super();
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMailbox() {
        return mailbox;
    }
    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
