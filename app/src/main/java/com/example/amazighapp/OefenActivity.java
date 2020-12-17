package com.example.amazighapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OefenActivity extends AppCompatActivity {

    DatabaseReference mBase;
    Integer CategoryId;
    OefenAdapter adapter;
    List<TranslatedWord> WordList;
    List<WordSounds> WordSoundList;
    ViewPager2 viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefen);

        CategoryId = Integer.parseInt(getIntent().getStringExtra("CATEGORY_ID"));
        viewpager = findViewById(R.id.viewPager2);

        WordList = new ArrayList<>();
        WordSoundList = new ArrayList<>();

        getData();
    }

    public void getData(){
        mBase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("translated_word");
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

                                            adapter = new OefenAdapter(WordList, WordSoundList);
                                            viewpager.setAdapter(adapter);
                                        }
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