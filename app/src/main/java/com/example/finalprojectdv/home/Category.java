package com.example.finalprojectdv.home;


public class Category {
    private String category;
    private int categoryImage;

    public Category(String category, int categoryImage) {
        this.category = category;
        this.categoryImage = categoryImage;
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
