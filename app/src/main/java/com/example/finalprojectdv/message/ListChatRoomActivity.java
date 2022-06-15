package com.example.finalprojectdv.message;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.User;
import com.example.finalprojectdv.info.DataUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListChatRoomActivity extends AppCompatActivity {
    private Button idBtnBack;
    private RecyclerView idRVListChats;
    private ChatRoomRVAdapter chatRoomRVAdapter;
    private List<ChatRoom> chatRoomList;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat_room);

        anhxa();
        getChatRoom();

        idBtnBack.setOnClickListener(v -> finish());
    }

    private void anhxa(){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        idBtnBack = findViewById(R.id.idBtnBack);
        idRVListChats = findViewById(R.id.idRVListChats);
        chatRoomList = new ArrayList<>();

        chatRoomRVAdapter = new ChatRoomRVAdapter(chatRoomList , this , R.layout.item_lastmessage);
        idRVListChats.setAdapter(chatRoomRVAdapter);
    }

    private void getChatRoom(){
        mDatabase.child("chatroom").orderByChild("user2").equalTo(DataUser.account).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                mDatabase.child("chatroom/"+chatRoom.id_room+"/message").orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        chatRoom.Lastmessage = snapshot.getValue(DetailMessage.class);

                        mDatabase.child("user/"+chatRoom.user1).get().addOnSuccessListener(dataSnapshot -> {
                            chatRoom.detailuser = dataSnapshot.getValue(User.class);
                            chatRoomList.removeIf(temp -> temp.id_room == chatRoom.id_room);
                            chatRoomList.add(chatRoom);
                            chatRoomRVAdapter.notifyDataSetChanged();

                        }).addOnFailureListener(e -> Log.d("CCCC", "onFailure: "));
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

        //
        mDatabase.child("chatroom").orderByChild("user1").equalTo(DataUser.account).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("CCCC", "onChildAdded: "+ snapshot.getValue() );
                ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                mDatabase.child("chatroom/"+chatRoom.id_room+"/message").orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d("CCCC", "onChildAdded: "+snapshot.getValue());
                        chatRoom.Lastmessage = snapshot.getValue(DetailMessage.class);

                        mDatabase.child("user/"+chatRoom.user2).get().addOnSuccessListener(dataSnapshot -> {
                            Log.d("CCCC", "onSuccess: user" + dataSnapshot.getValue());
                            chatRoom.detailuser = dataSnapshot.getValue(User.class);
                            chatRoomList.removeIf(temp -> temp.id_room == chatRoom.id_room);
                            chatRoomList.add(chatRoom);
                            chatRoomRVAdapter.notifyDataSetChanged();

                        }).addOnFailureListener(e -> Log.d("CCCC", "onFailure: "));
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