package com.example.finalprojectdv.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.example.finalprojectdv.home.DetailProductActivity;
import com.example.finalprojectdv.store.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.thanguit.toastperfect.ToastPerfect;

public class ItemCartRVAdapter extends RecyclerView.Adapter<ItemCartRVAdapter.ViewHolder> {
    private final ArrayList<Product> productArrayList;
    private final int layout;
    private final Context context;

    private boolean checkScroll= true;

    public ItemCartRVAdapter(ArrayList<Product> productArrayList, int layout, Context context) {
        this.productArrayList = productArrayList;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ItemCartRVAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        Picasso.get().load(product.listImages.get(0)).into(holder.idIVAvaSneaker);
        holder.idTVNameSneaker.setText(product.name);
        holder.idTVColor.setText("Color: "+product.color.get(0));
        holder.idTVSize.setText("Size: "+ product.size);
        holder.idTVPriceSneaker.setText("$ "+product.price);
        holder.idETQuantity.setText(String.valueOf(product.quantity));

        holder.itemView.setOnLongClickListener(v -> {
            if (checkScroll){
                holder.idLnLItemCart.setScrollX(450);
            }
            else
                holder.idLnLItemCart.setScrollX(0);
            checkScroll = !checkScroll;
            return true;
        });

        holder.idBtnDel.setOnClickListener(v -> CartFragment.ListItemCart.remove(product));
        holder.idBtnBuy.setOnClickListener(v -> {
            CartFragment.ListItemCart.remove(product);
            ToastPerfect.makeText(context, ToastPerfect.SUCCESS, "Buy Successful", ToastPerfect.CENTER, ToastPerfect.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView idIVAvaSneaker;
        private TextView idTVNameSneaker,idTVColor, idTVSize,idTVPriceSneaker;
        private Button idBtnUp,idBtnDown,idBtnDel,idBtnBuy;
        private EditText idETQuantity;
        private LinearLayout idLnLItemCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idLnLItemCart = itemView.findViewById(R.id.idLnLItemCart);

            idIVAvaSneaker = itemView.findViewById(R.id.idIVAvaSneaker);
            idTVNameSneaker = itemView.findViewById(R.id.idTVNameSneaker);
            idTVColor = itemView.findViewById(R.id.idTVColor);
            idTVSize = itemView.findViewById(R.id.idTVSize);
            idTVPriceSneaker = itemView.findViewById(R.id.idTVPriceSneaker);
            idBtnUp = itemView.findViewById(R.id.idBtnUp);
            idBtnDown = itemView.findViewById(R.id.idBtnDown);
            idBtnDel = itemView.findViewById(R.id.idBtnDel);
            idBtnBuy = itemView.findViewById(R.id.idBtnBuy);
            idETQuantity = itemView.findViewById(R.id.idETQuantity);
        }
    }
}
