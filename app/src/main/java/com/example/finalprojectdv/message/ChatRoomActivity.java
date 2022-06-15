package com.example.finalprojectdv.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalprojectdv.LoginRegister.LoginActivity;
import com.example.finalprojectdv.R;
import com.example.finalprojectdv.info.DataUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import vn.thanguit.toastperfect.ToastPerfect;

public class ChatRoomActivity extends AppCompatActivity {
    private Button idBtnBack,idBtnSendMess;
    private TextView idTVNameUser,idTVNotifi;
    private ImageView idIVAvaUser;
    private EditText idETText;
    private RecyclerView idRVListMess;
    private MessRVAdapter messRVAdapter;

    private String user_sell;
    private String text;

    private int id_room = 0;
    private List<DetailMessage> messageList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        anhxa();
        setData();

        messRVAdapter = new MessRVAdapter(messageList , this , R.layout.item_message);
        idRVListMess.setAdapter(messRVAdapter);
        //messRVAdapter.notifyDataSetChanged();


        CheckNewOrOldChat();

        idBtnBack.setOnClickListener(v -> finish());
        idBtnSendMess.setOnClickListener(v -> {
            text = idETText.getText().toString();
            if(text.length() >0){
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("ddMMyyyyHHmmss");
                SimpleDateFormat ft1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Log.d("CCCC", "onCreate: date time" + ft.format(date));
                DetailMessage message = new DetailMessage(DataUser.account , text ,ft1.format(date));
                if (id_room == 0){
                    Random random = new Random();
                    id_room = random.nextInt(9999);
                    ChatRoom chatRoom = new ChatRoom(id_room ,DataUser.account , user_sell);
                    mDatabase.child("chatroom/"+id_room).setValue(chatRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("CCCC", "onSuccess: setup chat room thanh cong");
                        }
                    });
                }
                mDatabase.child("chatroom/"+ id_room+"/message/"+ft.format(date)).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("CCCC", "onSuccess: up load mess thanh cong roi");
                        idETText.setText("");
                        text = null;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CCCC", "onFailure: kh upload mess thanh cong");
                    }
                });
            }
            else
                ToastPerfect.makeText(ChatRoomActivity.this, ToastPerfect.WARNING, "Input message , please!", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
        });
    }

    private void anhxa(){
        idBtnBack = findViewById(R.id.idBtnBack);
        idBtnSendMess = findViewById(R.id.idBtnSendMess);
        idTVNameUser = findViewById(R.id.idTVNameUser);
        idIVAvaUser = findViewById(R.id.idIVAvaUser);
        idETText = findViewById(R.id.idETText);
        idTVNotifi = findViewById(R.id.idTVNotifi);

        idRVListMess = findViewById(R.id.idRVListMess);
        messageList = new ArrayList<>();

        user_sell = getIntent().getStringExtra("account");

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void setData(){
        idTVNameUser.setText(getIntent().getStringExtra("name"));
        Picasso.get().load(getIntent().getStringExtra("avatar")).into(idIVAvaUser);
    }

    private void CheckNewOrOldChat(){
        mDatabase.child("chatroom").orderByChild("user1").equalTo(DataUser.account).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                if(user_sell.equals(chatRoom.user2)) {
                    id_room = chatRoom.id_room;
                    getOldMess();
                    return;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("chatroom").orderByChild("user2").equalTo(DataUser.account).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                if(user_sell.equals(chatRoom.user1)) {
                    id_room = chatRoom.id_room;
                    getOldMess();
                    return;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOldMess(){
        mDatabase.child("chatroom/"+ id_room+"/message").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                messageList.add(snapshot.getValue(DetailMessage.class));
                idTVNotifi.setVisibility(View.GONE);
                messRVAdapter.notifyItemInserted(messageList.size()-1);
                idRVListMess.smoothScrollToPosition(messageList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}