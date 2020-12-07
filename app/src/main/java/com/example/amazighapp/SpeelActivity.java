package com.example.amazighapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SpeelActivity extends AppCompatActivity implements View.OnClickListener {

    String CategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelen);

        CategoryId = getIntent().getStringExtra("CATEGORY_ID");

        TextView uwu = findViewById(R.id.textView);
        uwu.setText(CategoryId);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // do stuff here
        }
    }
}
