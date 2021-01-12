package com.example.amazighapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

        String filePath = this.getFilesDir().getPath() + "/scores.json";
        Gson gson = new Gson();

        try {
            Log.d("onCreate: ", filePath);
            File scoresJSON = new File(filePath);
            if (scoresJSON.createNewFile()) {
                System.out.println("File created: " + scoresJSON.getName());
            } else {
                System.out.println("File already exists.");
                try {
                    HighScore highScore = new HighScore(lessonName, scoreTotal, lessonID);
                    gson.toJson(highScore, new FileWriter(filePath));
                    Log.d("onFileWrite: ", "Successfully written to" + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

//        try {
//            BufferedReader br = new BufferedReader(new FileReader(filePath));
//
//            HighScore highScoreJSON = gson.fromJson(br, HighScore.class);
//
//            Log.d("Lesson Name: ", highScoreJSON.getLesson_name());
//            Log.d("Score Total: ", String.valueOf(highScoreJSON.getScore_total()));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            File scoresJSON = new File(filePath);
//            Scanner fileReader = new Scanner(scoresJSON);
//            while (fileReader.hasNextLine()) {
//                String data = fileReader.nextLine();
//                System.out.println(data);
//            }
//            fileReader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

        txtLessonName = findViewById(R.id.txtLessonName);
        txtHighScore = findViewById(R.id.txtHighScore);
        txtNewScore = findViewById(R.id.txtNewScore);
        btnOpnieuw = findViewById(R.id.btnOpnieuw);
        btnSluit = findViewById(R.id.btnSluit);

        txtLessonName.setText(lessonName);
        txtHighScore.setText(scoreTotal.toString());

        if (scoreTotal > 11 && lessonName != "Dieren 02"){
            txtNewScore.setText("Nieuwe highscore!");
        } else {
            txtNewScore.setText("Huidige highscore: " + scoreTotal);
        }

        btnOpnieuw.setOnClickListener(this);
        btnSluit.setOnClickListener(this);
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
