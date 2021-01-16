package com.example.amazighapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        for (int i = 0; i < 10; i++) {
            QuizItem       quizItem    = new QuizItem();
            List<String>   wrongWords  = new ArrayList<>();
            String         soundUrl    = "https://raw.githubusercontent.com/anars/blank-audio/master/2-seconds-of-silence.mp3";
            TranslatedWord correctWord;

            int correctWordIndex     = getRandomNumberInRange(0, WordList.size() - 1);
            correctWord              = WordList.get(correctWordIndex);

            for (int soundIndex = 0; soundIndex < WordSoundList.size(); soundIndex++) {
                WordSounds sound = WordSoundList.get(soundIndex);

                if (sound.getTw_id() == correctWord.getTw_id()) {
                    soundUrl = WordSoundList.get(soundIndex).getSound_url();

                    break;
                }
            }

            quizItem.setTw_id(correctWord.getTw_id());
            quizItem.setCategory_id(correctWord.getCategory_id());
            quizItem.setImage_url(correctWord.getImage_url());
            quizItem.setWord_ama(correctWord.getWord_ama());
            quizItem.setWord_ned(correctWord.getWord_ned());
            quizItem.setSound_url(soundUrl);

            while (wrongWords.size() < 3) {
                TranslatedWord randomWord = WordList.get(getRandomNumberInRange(0, WordList.size() - 1));

                if (randomWord.getTw_id() == correctWord.getTw_id()) continue;
                if (wrongWords.contains(randomWord.getWord_ned())) continue;

                wrongWords.add(randomWord.getWord_ned());
            }

            quizItem.setWrongWords(wrongWords);
            QuizList.add(quizItem);
        }

        dialog.dismiss();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
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

                            translatedWord.setTw_id(TranslatedWordSnapshot.child("tw_id").getValue(Integer.class));
                            translatedWord.setCategory_id(TranslatedWordSnapshot.child("category_id").getValue(Integer.class));
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

                                                soundWord.setTw_id(WordSoundsSnapshot.child("tw_id").getValue(Integer.class));
                                                soundWord.setCategory_id(WordSoundsSnapshot.child("category_id").getValue(Integer.class));
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
