package com.example.amazighapp;

public class Category {
    private Integer categoryId;
    private String name, image_url;

    public Integer getCategory_id() {
        return categoryId;
    }

    public void setCategory_id(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
