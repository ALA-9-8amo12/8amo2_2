package com.example.amazighapp;

public class HighScore {
    private int score_total, lesson_id;
    private String lesson_name;

    public HighScore(){}

    public HighScore(String lessonName, int scoreTotal, int lessonID){
        this.score_total = scoreTotal;
        this.lesson_id = lessonID;
        this.lesson_name = lessonName;
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

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }
}
