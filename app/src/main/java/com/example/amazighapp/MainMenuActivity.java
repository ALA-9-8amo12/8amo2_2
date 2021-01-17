package com.example.amazighapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            Log.d("SplashScreen", "onCreate: " + e);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnPractice = findViewById(R.id.btnOefenen);
        Button btnPlay     = findViewById(R.id.btnSpelen);
        Button btnAbout    = findViewById(R.id.btnAbout);

        btnPractice.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOefenen:
                Intent intentPractice = new Intent(this, CategoryActivity.class);
                intentPractice.putExtra("GAME_MODE", "PRACTICE");
                startActivity(intentPractice);

                break;
            case R.id.btnSpelen:
                Intent intentPlay = new Intent(this, CategoryActivity.class);
                intentPlay.putExtra("GAME_MODE", "PLAY");
                startActivity(intentPlay);

                break;
            case R.id.btnAbout:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);

                break;
        }
    }
}
