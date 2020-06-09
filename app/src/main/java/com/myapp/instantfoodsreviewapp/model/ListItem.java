package com.myapp.instantfoodsreviewapp.model;

public class ListItem {
    private int imageFood;
    private String foodCategory;
    private String foodName;
    private String reviewContent;
    private int imageStar1;
    private int imageStar2;
    private int imageStar3;

    public ListItem(int imageFood, String foodCategory, String foodName, String reviewContent, int imageStar1, int imageStar2, int imageStar3){
        this.imageFood = imageFood;
        this.foodCategory = foodCategory;
        this.foodName = foodName;
        this.reviewContent = reviewContent;
        this.imageStar1 = imageStar1;
        this.imageStar2 = imageStar2;
        this.imageStar3 = imageStar3;
    }

    public int getImageFood() {
        return imageFood;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public int getImageStar1() {
        return imageStar1;
    }

    public int getImageStar2() {
        return imageStar2;
    }

    public int getImageStar3() {
        return imageStar3;
    }
}
