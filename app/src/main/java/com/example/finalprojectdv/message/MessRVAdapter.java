package com.example.finalprojectdv.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.info.DataUser;
import com.example.finalprojectdv.store.ProductRVAdapter;

import java.text.ParseException;
import java.util.List;

public class MessRVAdapter extends RecyclerView.Adapter<MessRVAdapter.ViewHolder> {
    private final List<DetailMessage> detailMessages;
    private final Context context;
    private final int layout;

    public MessRVAdapter(List<DetailMessage> detailMessages, Context context, int layout) {
        this.detailMessages = detailMessages;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailMessage message = detailMessages.get(position);
        holder.setIsRecyclable(false);
        holder.idTVTextMess.setText(message.text);
        if (!message.user_send.equals(DataUser.account)){
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.BELOW , R.id.idTVTime);

            holder.idTVTextMess.setLayoutParams(layoutParams);
            holder.idTVTextMess.setTextColor(Color.BLACK);
            holder.idTVTextMess.setBackgroundResource(R.drawable.custom_edittext_search);
        }
        holder.idTVTime.setText(message.time);


        holder.idTVTextMess.setOnClickListener(v ->{
            if (holder.idTVTime.getVisibility() == View.VISIBLE )
                holder.idTVTime.setVisibility(View.GONE);
            else
                holder.idTVTime.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return detailMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idTVTime, idTVTextMess;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idTVTime = itemView.findViewById(R.id.idTVTime);
            idTVTextMess = itemView.findViewById(R.id.idTVTextMess);
        }
    }
}
