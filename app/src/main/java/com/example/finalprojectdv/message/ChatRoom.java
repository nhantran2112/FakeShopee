package com.example.finalprojectdv.message;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.finalprojectdv.User;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ChatRoom implements Comparable<ChatRoom> {
    public int id_room;
    public String user1;
    public String user2;
    public User detailuser;
    public DetailMessage Lastmessage;

    public ChatRoom(){

    }

    public ChatRoom(int id_room, String user1, String user2) {
        this.id_room = id_room;
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public int compareTo(ChatRoom o) {
        Date date = null;
        Date date1 = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(o.Lastmessage.time);
            date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(Lastmessage.time);
            Log.d("DDDD", "compareTo: alo");
        } catch (ParseException e) {
            Log.d("DDDD", "compareTo: "+e.getLocalizedMessage());
            e.printStackTrace();
        }
        return date.compareTo(date1);
    }
}
