package com.example.amazighapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SpeelActivity extends AppCompatActivity implements View.OnClickListener {

    Integer CategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelen);

        CategoryId = Integer.parseInt(getIntent().getStringExtra("CATEGORY_ID"));

        ProgressBar healthBar = findViewById(R.id.healthBar);
        //healthBar.setProgress(75, true);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // do stuff here
        }
    }
}
