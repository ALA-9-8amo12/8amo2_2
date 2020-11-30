package com.example.amazighapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryViewholder>{

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    static class CategoryViewholder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView Image;
        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.txtCategory);
            Image = itemView.findViewById(R.id.imageCategory);
        }
    }

    protected void onBindViewHolder(@NonNull CategoryViewholder holder, int position, @NonNull Category model) {
        holder.Title.setText(model.getName());

        Glide.with(holder.itemView.getContext())
                .load(model.getImage_url())
                .into(holder.Image);
    }

    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category, parent, false);
        return new CategoryAdapter.CategoryViewholder(view);
    }

}



