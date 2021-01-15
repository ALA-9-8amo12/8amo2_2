package com.example.amazighapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SpeelActivity extends AppCompatActivity implements View.OnClickListener {

    Integer              CategoryId;
    DatabaseReference    mBase;
    ProgressBar          healthBar;
    List<QuizItem>       QuizList;
    List<TranslatedWord> WordList;
    List<WordSounds>     WordSoundList;
    ProgressDialog       dialog;
    Boolean              IsLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelen);

        CategoryId    = Integer.parseInt(getIntent().getStringExtra("CATEGORY_ID"));
        QuizList      = new ArrayList<>();
        WordList      = new ArrayList<>();
        WordSoundList = new ArrayList<>();

        dialog = ProgressDialog.show(this, "", "Les aan het laden...", true);

        getData();

        final Handler handler = new Handler();
        Runnable runnable     = new Runnable() {
            public void run() {
                while (IsLoading) {
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable(){
                    public void run() {
                        ConstraintLayout layoutUwU = findViewById(R.id.LayoutSpelen);
                        layoutUwU.setVisibility(View.VISIBLE);

                        healthBar = findViewById(R.id.healthBar);
                        healthBar.setProgress(100, true);

                        generateQuiz();
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // do stuff here
        }
    }

    public void generateQuiz() {


        dialog.dismiss();
    }

    public void getData(){
        mBase = FirebaseDatabase.getInstance().getReference().child("translated_word");
        mBase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot TranslatedWordSnapshot: dataSnapshot.getChildren()) {
                            TranslatedWord translatedCheck = TranslatedWordSnapshot.getValue(TranslatedWord.class);
                            TranslatedWord translatedWord = new TranslatedWord();
                            String key = translatedCheck.getCategory_id().toString();

                            if (!translatedCheck.getCategory_id().equals(CategoryId)) {
                                continue;
                            }

                            translatedWord.setWord_ama(TranslatedWordSnapshot.child("word_ama").getValue(String.class));
                            translatedWord.setWord_ned(TranslatedWordSnapshot.child("word_ned").getValue(String.class));
                            translatedWord.setImage_url(TranslatedWordSnapshot.child("image_url").getValue(String.class));

                            WordList.add(translatedWord);

                            mBase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("sounds");
                            mBase.addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot WordSoundsSnapshot: dataSnapshot.getChildren()) {
                                                WordSounds translatedCheck = WordSoundsSnapshot.getValue(WordSounds.class);
                                                WordSounds soundWord = new WordSounds();

                                                if (!translatedCheck.getCategory_id().equals(CategoryId)) {
                                                    continue;
                                                }

                                                soundWord.setSound_url(WordSoundsSnapshot.child("sound_url").getValue(String.class));

                                                WordSoundList.add(soundWord);
                                            }

                                            IsLoading = false;
                                        }

                                        @Override public void onCancelled(@NonNull DatabaseError error) {
                                            Log.e("OefenActivity", "Firebase Error in getSoundData(): " + error.getMessage());
                                        }
                                    });
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("OefenActivity", "Firebase Error in getData(): " + error.getMessage());
                    }
                });

    }
}
