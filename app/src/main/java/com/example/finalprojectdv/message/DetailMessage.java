package com.example.finalprojectdv.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMessage {
    public String user_send;
    public String text;
    public String time;

    public  DetailMessage(){

    }

    public DetailMessage(String user_send, String text, String time) {
        this.user_send = user_send;
        this.text = text;
        this.time = time;
    }

}
