package com.example.amazighapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import android.widget.RelativeLayout;

public class TranslatedWordAdapter extends RecyclerView.Adapter<TranslatedWordAdapter.TrWordViewHolder> {

    ArrayList<Object> Words;

    public TranslatedWordAdapter(ArrayList wordList) {
        Words = wordList;
        Log.d("Wordlist Content", String.valueOf(Words));
    }

    static class TrWordViewHolder extends RecyclerView.ViewHolder {
        TextView wordNed, wordAmg;
        ImageView wordImage;
        Button btnSound;
        RelativeLayout relativeLayout;

        public TrWordViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.container);
            wordNed = itemView.findViewById(R.id.wordNed);
            wordAmg = itemView.findViewById(R.id.wordAmg);
            btnSound = itemView.findViewById(R.id.btnSound);
            wordImage = itemView.findViewById(R.id.wordImage);
        }
    }

    public TrWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.translated_word, parent, false);
        return new TranslatedWordAdapter.TrWordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrWordViewHolder holder, int position) {
        String woord = (String) Words.get(position);
        holder.wordNed.setText("Nederlands");
        holder.wordAmg.setText("Amazigh");
//        holder.Date.setText("Date: " + model.getDate());

        Glide.with(holder.itemView.getContext())
                .load(R.drawable._general_logo)
                .into(holder.wordImage);
    }

    @Override
    public int getItemCount() {
        return Words.size();
    }

}
