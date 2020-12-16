package com.example.amazighapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OefenAdapter extends RecyclerView.Adapter<OefenAdapter.OefenViewHolder>{
    List<TranslatedWord> wordList;

    public OefenAdapter(List<TranslatedWord> wordList) {
        this.wordList = wordList;
    }

    static class OefenViewHolder extends RecyclerView.ViewHolder {
        TextView wordNed, wordAmg;
        ImageView wordImage;
        Button btnSound;
        RelativeLayout relativeLayout;

        public OefenViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.container);

            wordNed = itemView.findViewById(R.id.wordNed);
            wordAmg = itemView.findViewById(R.id.wordAmg);
            btnSound = itemView.findViewById(R.id.btnSound);
            wordImage = itemView.findViewById(R.id.wordImage);
        }
    }

    public OefenAdapter.OefenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.translated_word, parent, false);
        return new OefenAdapter.OefenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OefenAdapter.OefenViewHolder holder, int position) {
        holder.wordNed.setText(wordList.get(position).getWord_ned());
        holder.wordAmg.setText(wordList.get(position).getWord_ama());
//        holder.Date.setText("Date: " + model.getDate());

        Glide.with(holder.itemView.getContext())
                .load(wordList.get(position).getImage_url())
                .into(holder.wordImage);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

}
