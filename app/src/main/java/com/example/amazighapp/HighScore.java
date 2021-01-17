package com.example.amazighapp;

public class HighScore {
    private int score_total, lesson_id;

    public HighScore(){}

    public HighScore(int scoreTotal, int lessonID){
        this.score_total = scoreTotal;
        this.lesson_id = lessonID;
    }

    public int getScore_total() {
        return score_total;
    }

    public void setScore_total(int score_total) {
        this.score_total = score_total;
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }
}
