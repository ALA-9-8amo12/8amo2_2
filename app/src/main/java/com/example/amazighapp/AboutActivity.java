package com.example.amazighapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnFB, btnTwitter, btnEmail, btnInfo, btnSluiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btnSluiten = findViewById(R.id.btnSluiten);
        btnTwitter = findViewById(R.id.btnTwitter);
        btnEmail = findViewById(R.id.btnEmail);
        btnInfo = findViewById(R.id.btnInfo);
        btnFB = findViewById(R.id.btnFB);

        btnSluiten.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnFB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmail:
                String mailto = "mailto:amazighsupport@gmail.com" +
                        "?cc=" +
                        "&subject=" + Uri.encode("test email") +
                        "&body=" + Uri.encode("test body");
                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse(mailto));

                try {
                    startActivity(intentEmail);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Error to open email app", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnFB:
                Intent intentFacebook = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://www.facebook.com/"));
                startActivity(intentFacebook);

                break;
            case R.id.btnTwitter:
                Intent intentTwitter = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://twitter.com/"));
                startActivity(intentTwitter);

                break;
            case R.id.btnInfo:
                Intent intentInfo = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://nl.wikipedia.org/wiki/Berbers"));
                startActivity(intentInfo);

                break;
            case R.id.btnSluiten:
                Intent intentMain = new Intent(this, MainMenuActivity.class);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentMain.putExtra("EXIT", true);

                startActivity(intentMain);
                finish();

                break;
        }
    }
}