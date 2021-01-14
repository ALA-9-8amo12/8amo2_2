package com.example.amazighapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{

    String lessonName;
    Integer lessonID, scoreTotal;
    TextView txtLessonName, txtHighScore, txtNewScore;
    Button btnOpnieuw, btnSluit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lessonName = getIntent().getStringExtra("LESSON_NAME");
        lessonID = Integer.parseInt(getIntent().getStringExtra("LESSON_ID"));
        scoreTotal = Integer.parseInt(getIntent().getStringExtra("SCORE_TOTAL"));

        txtLessonName = findViewById(R.id.txtLessonName);
        txtHighScore = findViewById(R.id.txtHighScore);
        txtNewScore = findViewById(R.id.txtNewScore);
        btnOpnieuw = findViewById(R.id.btnOpnieuw);
        btnSluit = findViewById(R.id.btnSluit);

        txtLessonName.setText(lessonName);
        txtHighScore.setText(scoreTotal.toString());

        btnOpnieuw.setOnClickListener(this);
        btnSluit.setOnClickListener(this);

        String filePath = this.getFilesDir().getPath() + "/" + lessonName + ".json";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try {
            File scoresJSON = new File(filePath);
            if (scoresJSON.createNewFile()) {
                System.out.println("File created: " + scoresJSON.getName());
            } else {
                System.out.println("File already exists.");

                // add check for score/highscore whether its higher or lower than the registered score

                try {
                    HighScore highScore = new HighScore(scoreTotal, lessonID);
                    FileWriter jsonWriter = new FileWriter(filePath);

                    System.out.println("onJSONWriter" + gson.toJson(highScore));
                    System.out.println("onFileWrite: " + filePath);

                    jsonWriter.write(gson.toJson(highScore));
                    jsonWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    Reader reader = new FileReader(filePath);
                    HighScore score = gson.fromJson(reader, HighScore.class);

                    int lesson_id = score.getLesson_id();
                    int score_total = score.getScore_total();

                    System.out.println(lesson_id);
                    System.out.println(score_total);

                    if (scoreTotal > score.getLesson_id()){
                        txtNewScore.setText("Nieuwe highscore!");
                    } else {
                        txtNewScore.setText("Huidige highscore: " + scoreTotal);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOpnieuw:
                Intent intentPlay = new Intent(this, OefenActivity.class);
                intentPlay.putExtra("CATEGORY_ID", lessonID.toString());
                startActivity(intentPlay);

                break;
            case R.id.btnSluit:
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
