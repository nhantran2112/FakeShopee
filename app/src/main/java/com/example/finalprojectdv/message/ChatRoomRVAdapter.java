package com.example.finalprojectdv.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.User;
import com.example.finalprojectdv.info.DataUser;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ChatRoomRVAdapter extends RecyclerView.Adapter<ChatRoomRVAdapter.ViewHolderChatRoom> {
    private List<ChatRoom> chatRoomList;
    private Context context;
    private int layout;

    public ChatRoomRVAdapter(List<ChatRoom> chatRoomList, Context context, int layout) {
        this.chatRoomList = chatRoomList;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolderChatRoom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolderChatRoom(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderChatRoom holder, int position) {
        Collections.sort(chatRoomList);
        ChatRoom chatRoom = chatRoomList.get(position);

        Picasso.get().load(chatRoom.detailuser.avatar).into(holder.idIVAvaUser);
        holder.idTVNameUser.setText(chatRoom.detailuser.name);
        if (chatRoom.Lastmessage.user_send.equals(DataUser.account))
            holder.idTVLastMess.setText("You: "+chatRoom.Lastmessage.text);
        else
            holder.idTVLastMess.setText(chatRoom.Lastmessage.text);
        holder.idTVTimeLM.setText(chatRoom.Lastmessage.time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , ChatRoomActivity.class);
                i.putExtra("account" , chatRoom.detailuser.account);
                i.putExtra("avatar", chatRoom.detailuser.avatar);
                i.putExtra("name", chatRoom.detailuser.name);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public class ViewHolderChatRoom extends RecyclerView.ViewHolder {
        private ImageView idIVAvaUser;
        private TextView idTVNameUser, idTVTimeLM, idTVLastMess;
        public ViewHolderChatRoom(@NonNull View itemView) {
            super(itemView);

            idIVAvaUser = itemView.findViewById(R.id.idIVAvaUser);
            idTVNameUser = itemView.findViewById(R.id.idTVNameUser);
            idTVTimeLM = itemView.findViewById(R.id.idTVTimeLM);
            idTVLastMess = itemView.findViewById(R.id.idTVLastMess);
        }
    }
}
