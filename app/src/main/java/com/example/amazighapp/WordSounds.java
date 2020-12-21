package com.example.amazighapp;

public class WordSounds {
    private Integer translatedWordId;
    private Integer categoryId;
    private String  soundUrl;

    public Integer getTw_id() { return translatedWordId; }
    public void setTw_id(Integer translatedWordId) { this.translatedWordId = translatedWordId; }

    public Integer getCategory_id() {
        return categoryId;
    }
    public void setCategory_id(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSound_url() { return soundUrl; }
    public void setSound_url(String soundUrl) { this.soundUrl = soundUrl; }
}
