package com.example.amazighapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryViewholder>{

    String gameMode;

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options, String gamemode) {
        super(options);

        gameMode = gamemode;
    }

    static class CategoryViewholder extends RecyclerView.ViewHolder {
        TextView Title;
        ImageView Image;
        RelativeLayout rLayout;

        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);

            Title   = itemView.findViewById(R.id.txtCategory);
            Image   = itemView.findViewById(R.id.imageCategory);
            rLayout = itemView.findViewById(R.id.card);
        }
    }

    protected void onBindViewHolder(@NonNull final CategoryViewholder holder, int position, @NonNull final Category model) {
        holder.Title.setText(model.getName());
        holder.rLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Integer id      = model.getCategory_id();

                        switch(gameMode) {
                            case "PRACTICE":
                                Intent intentPractice = new Intent(context, OefenActivity.class);

                                intentPractice.putExtra("CATEGORY_ID", id.toString());
                                context.startActivity(intentPractice);

                                break;
                            case "PLAY":
                                Intent intentPlay = new Intent(context, SpeelActivity.class);

                                intentPlay.putExtra("CATEGORY_ID", id.toString());
                                context.startActivity(intentPlay);

                                break;
                        }

                    }
                });
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



