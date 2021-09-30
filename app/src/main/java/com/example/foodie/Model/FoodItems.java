package com.example.foodie.Model;

public class FoodItems {
    private String label, image, url, group;
    private int totalTime;
    private double calories;

    public FoodItems(String label, String image, String url, int totalTime, double calories,String group) {
        this.label = label;
        this.image = image;
        this.url = url;
        this.totalTime = totalTime;
        this.calories = calories;
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
