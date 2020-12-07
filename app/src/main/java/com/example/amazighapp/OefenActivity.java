package com.example.amazighapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OefenActivity extends AppCompatActivity {

    DatabaseReference mBase;
    Integer CategoryId;
    ArrayList WordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelen);

        CategoryId = Integer.parseInt(getIntent().getStringExtra("CATEGORY_ID"));

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

                            TranslatedWords.add(translatedWord);
                        }

                        WordList = TranslatedWords;
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("OefenActivity", "Firebase Error: " + error.getMessage());
                    }
        });
    }
}