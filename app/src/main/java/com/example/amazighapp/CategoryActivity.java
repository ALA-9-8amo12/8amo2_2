package com.example.amazighapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    CategoryAdapter adapter;
    DatabaseReference mBase;
    String gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        gameMode = getIntent().getStringExtra("GAME_MODE");

        mBase = FirebaseDatabase.getInstance().getReference().child("category");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<Category> options
                = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(mBase, Category.class)
                .build();

        adapter = new CategoryAdapter(options, gameMode);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnPractice:
                Intent intentPractice = new Intent(this, CategoryActivity.class);
                intentPractice.putExtra("GAME_MODE", "PRACTICE");
                startActivity(intentPractice);

                break;
            case R.id.btnPlay:
                Intent intentPlay = new Intent(this, CategoryActivity.class);
                intentPlay.putExtra("GAME_MODE", "PLAY");
                startActivity(intentPlay);

                break;
            case R.id.btnAbout:
                // Todo: Add functionality

                break;
        }
    }
}