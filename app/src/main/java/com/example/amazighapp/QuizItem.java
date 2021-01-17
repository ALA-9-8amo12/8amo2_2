package com.example.amazighapp;

import java.util.List;

public class QuizItem {
    private Integer      translatedWordId;
    private Integer      categoryId;
    private String       imageUrl;
    private String       wordNederland;
    private String       wordAmazigh;
    private String       soundUrl;
    private List<String> wrongWords;

    public Integer getTw_id() { return translatedWordId; }
    public void setTw_id(Integer translatedWordId) { this.translatedWordId = translatedWordId; }

    public Integer getCategory_id() { return categoryId; }
    public void setCategory_id(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage_url() { return imageUrl; }
    public void setImage_url(String imageUrl) { this.imageUrl = imageUrl; }

    public String getWord_ned() { return wordNederland; }
    public void setWord_ned(String wordNederland) { this.wordNederland = wordNederland; }

    public String getWord_ama() { return wordAmazigh; }
    public void setWord_ama(String wordAmazigh) { this.wordAmazigh = wordAmazigh; }

    public String getSound_url() { return soundUrl; }
    public void setSound_url(String soundUrl) { this.soundUrl = soundUrl; }

    public List<String> getWrongWords() { return wrongWords; }
    public void setWrongWords(List<String> wrongWords) { this.wrongWords = wrongWords; }
}
