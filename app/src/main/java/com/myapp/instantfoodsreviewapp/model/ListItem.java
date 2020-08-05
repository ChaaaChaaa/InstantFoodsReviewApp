package com.myapp.instantfoodsreviewapp.model;

public class ListItem {
    private int imageFood;
    private String foodCategory;
    private String foodName;
    private String reviewContent;
//    private int imageStar1;
//    private int imageStar2;
//    private int imageStar3;
    private String productRating;
    private FoodCategoryList foodType;

    public ListItem(int imageFood, FoodCategoryList foodType, String foodName, String reviewContent, String productRating){
        this.imageFood = imageFood;
        this.foodType = foodType;
        this.foodName = foodName;
        this.reviewContent = reviewContent;
        this.productRating = productRating;
//        this.imageStar1 = imageStar1;
//        this.imageStar2 = imageStar2;
//        this.imageStar3 = imageStar3;
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

    public String getProductRating(){
        return productRating;
    }

//    public int getImageStar1() {
//        return imageStar1;
//    }
//
//    public int getImageStar2() {
//        return imageStar2;
//    }
//
//    public int getImageStar3() {
//        return imageStar3;
//    }

    public FoodCategoryList getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodCategoryList foodType) {
        this.foodType = foodType;
    }
}
