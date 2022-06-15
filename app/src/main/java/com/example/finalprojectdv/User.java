package com.example.finalprojectdv;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String account;
    public String password;
    public String name;
    public String avatar;
    public String address;
    public String phonenumber;

    public User(){

    }

    public User(String account, String password, String name, String avatar) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
    }

    public User(String account, String password, String name, String avatar, String address, String phonenumber) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.phonenumber = phonenumber;
    }
}
