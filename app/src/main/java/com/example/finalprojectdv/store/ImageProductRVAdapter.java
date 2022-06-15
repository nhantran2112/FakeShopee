package com.example.finalprojectdv.store;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageProductRVAdapter extends RecyclerView.Adapter<ImageProductRVAdapter.ImageViewHolder> {
    private final List<String> imageList;
    private final List<Uri> uriList;
    private final int layout;

    public ImageProductRVAdapter(List<String> imageList, int layout, List<Uri> uriList) {
        this.imageList = imageList;
        this.layout = layout;
        this.uriList = uriList;
    }

    @NonNull
    @Override
    public ImageProductRVAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ImageProductRVAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageProductRVAdapter.ImageViewHolder holder, int position) {
        if (imageList != null) {
        } else {
            holder.idIVImagePostDetail.setImageURI(uriList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (imageList != null) {
            return imageList.size();
        } else {
            return uriList.size();
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView idIVImagePostDetail;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            idIVImagePostDetail = itemView.findViewById(R.id.idIVImagePostDetail);
        }
    }
}
