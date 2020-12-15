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

import java.util.ArrayList;

public class OefenActivity extends AppCompatActivity {

    DatabaseReference mBase;
    Integer CategoryId;
    TranslatedWordAdapter adapter;
    ArrayList WordList;
    ViewPager2 viewpager;
    String ama, ned, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefen);

        CategoryId = Integer.parseInt(getIntent().getStringExtra("CATEGORY_ID"));
        viewpager = findViewById(R.id.viewPager2);

        mBase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("translated_word");
        mBase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<TranslatedWord> TranslatedWords = new ArrayList<>();
                        
                        for (DataSnapshot TranslatedWordSnapshot: dataSnapshot.getChildren()) {
                            TranslatedWord translatedWord = TranslatedWordSnapshot.getValue(TranslatedWord.class);

                            if (!translatedWord.getCategory_id().equals(CategoryId)) {
                                continue;
                            }

                            ama = translatedWord.getWord_ama();
                            ned = translatedWord.getWord_ned();
                            image = translatedWord.getImage_url();

                            TranslatedWords.add(translatedWord);
                            Log.d("WordEntry", TranslatedWordSnapshot.toString());
                            Log.d("TransWords", String.valueOf(TranslatedWords));
                            Log.d("WordEntryValue", translatedWord.toString());
                        }

                        WordList = TranslatedWords;
                        for (Object WordObject: WordList) {

                        }
                        Log.d("WordEntryValues", ama + " " + ned + " " + image);
                        Log.d("WordList", WordList.toString());
                        adapter = new TranslatedWordAdapter(WordList);
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("OefenActivity", "Firebase Error: " + error.getMessage());
                    }
        });
        viewpager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}