package com.example.amazighapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnPractice = findViewById(R.id.btnPractice);
        Button btnPlay     = findViewById(R.id.btnPlay);
        Button btnAbout    = findViewById(R.id.btnAbout);

        btnPractice.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnPractice:
                // Todo: Add functionality

                break;
            case R.id.btnPlay:
                // Todo: Add functionality

                break;
            case R.id.btnAbout:
                // Todo: Add functionality

                break;
        }
    }
}
