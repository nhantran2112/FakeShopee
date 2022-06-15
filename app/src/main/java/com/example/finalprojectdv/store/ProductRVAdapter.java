package com.example.finalprojectdv.store;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.cart.CartFragment;
import com.example.finalprojectdv.home.DetailProductActivity;
import com.example.finalprojectdv.other.DoubleClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewHolder> {
    private final ArrayList<Product> productArrayList;
    private final Context context;
    private final int layout;

    public ProductRVAdapter(ArrayList<Product> productArrayList, Context context, int layout) {
        this.productArrayList = productArrayList;
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
        Product product = productArrayList.get(position);
        holder.idTVNameSneaker.setText(product.name);
        holder.idTVPriceSneaker.setText("$"+product.price);
        Picasso.get().load(product.listImages.get(0)).into(holder.idIVAvaSneaker);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailProductActivity.class);
            i.putExtra("product",product);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView idIVAvaSneaker;
        private final TextView idTVNameSneaker;
        private final TextView idTVPriceSneaker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idIVAvaSneaker = itemView.findViewById(R.id.idIVAvaSneaker);
            idTVNameSneaker = itemView.findViewById(R.id.idTVNameSneaker);
            idTVPriceSneaker = itemView.findViewById(R.id.idTVPriceSneaker);
        }
    }
}
