package com.example.finalprojectdv.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectdv.R;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private final ArrayList<Category> categoryArrayList;
    private final Context context;
    private final CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<Category> categoryArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_sneaker,parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
        holder.idIVCategory.setImageResource(categoryArrayList.get(position).getCategoryImage());
        holder.itemView.setOnClickListener(view -> categoryClickInterface.onCategoryClick(position));
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView idIVCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idIVCategory =itemView.findViewById(R.id.idIVCategory);
        }
    }
}
