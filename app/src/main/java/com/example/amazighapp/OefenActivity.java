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
import java.util.List;

public class OefenActivity extends AppCompatActivity {

    DatabaseReference mBase;
    Integer CategoryId;
    OefenAdapter adapter;
    List<TranslatedWord> WordList;
    ViewPager2 viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oefen);

        CategoryId = Integer.parseInt(getIntent().getStringExtra("CATEGORY_ID"));
        viewpager = findViewById(R.id.viewPager2);

        WordList = new ArrayList<>();

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

                        if (!translatedCheck.getCategory_id().equals(CategoryId)) {
                            continue;
                        }

                        translatedWord.setWord_ama(TranslatedWordSnapshot.child("word_ama").getValue(String.class));
                        translatedWord.setWord_ned(TranslatedWordSnapshot.child("word_ned").getValue(String.class));
                        translatedWord.setImage_url(TranslatedWordSnapshot.child("image_url").getValue(String.class));

                        WordList.add(translatedWord);
                    }

                    adapter = new OefenAdapter(WordList);
                    viewpager.setAdapter(adapter);
                }


                @Override public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("OefenActivity", "Firebase Error: " + error.getMessage());
                }
            });
    }
}