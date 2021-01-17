package com.example.amazighapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
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
    List<WordSounds> wordSoundList;

    public OefenAdapter(List<TranslatedWord> wordList, List<WordSounds> wordSoundList) {
        this.wordList = wordList;
        this.wordSoundList = wordSoundList;
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
        final String sound_url = wordSoundList.get(position).getSound_url();
        holder.wordNed.setText("Nederlands: " + wordList.get(position).getWord_ned());
        holder.wordAmg.setText("Amazigh: " + wordList.get(position).getWord_ama());
        holder.btnSound.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MediaPlayer player = new MediaPlayer();
                            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            player.setDataSource(sound_url);
                            player.prepare();
                            player.start();
                        } catch (Exception e) {
                            Log.d("Exception sound: ", "Error while playing the sound");
                        }
                    }
                });

        Glide.with(holder.itemView.getContext())
                .load(wordList.get(position).getImage_url())
                .into(holder.wordImage);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

}
