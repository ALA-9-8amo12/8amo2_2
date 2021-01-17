package com.example.amazighapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{

    String lessonName, filePath;
    Integer lessonID, scoreTotal;
    TextView txtLessonName, txtHighScore, txtNewScore;
    Button btnOpnieuw, btnSluit;
    Gson gson;

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

        filePath = this.getFilesDir().getPath() + "/" + lessonName + ".json";
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try {
            createFile();
            fileReader();
            fileWriter();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createFile() throws IOException {
        File scoresJSON = new File(filePath);
        
        if (scoresJSON.createNewFile()) {
            HighScore highScore = new HighScore(scoreTotal, lessonID);
            FileWriter jsonWriter = new FileWriter(filePath);

            jsonWriter.write(gson.toJson(highScore));
            jsonWriter.close();
        }
    }

    public void fileWriter() throws IOException {
        Reader reader = new FileReader(filePath);
        HighScore score = gson.fromJson(reader, HighScore.class);

        if (scoreTotal > score.getScore_total()) {
            HighScore highScore = new HighScore(scoreTotal, lessonID);
            FileWriter jsonWriter = new FileWriter(filePath);

            jsonWriter.write(gson.toJson(highScore));
            jsonWriter.close();
        }
    }

    public void fileReader() throws IOException {
        Reader reader = new FileReader(filePath);
        HighScore score = gson.fromJson(reader, HighScore.class);

        if (scoreTotal > score.getScore_total()){
            txtNewScore.setText("Nieuwe highscore!");
        } else {
            txtNewScore.setText("Huidige highscore: " + score.getScore_total());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOpnieuw:
                Intent intentPlay = new Intent(this, SpeelActivity.class);
                intentPlay.putExtra("CATEGORY_ID", lessonID.toString());
                intentPlay.putExtra("CATEGORY_NAME", lessonName);
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
