package com.myapp.instantfoodsreviewapp.model;

public class ListItem {
    private String imageFood;
    private int foodCategory;
    private String foodName;
    private Object reviewContent;
    //    private int imageStar1;
//    private int imageStar2;
//    private int imageStar3;
    private int productRating;
    private FoodCategoryList foodType;

    public ListItem(String imageFood, int foodCategory, String foodName, Object reviewContent, int productRating) {
        this.imageFood = imageFood;
        this.foodCategory = foodCategory;
        this.foodName = foodName;
        this.reviewContent = reviewContent;
        this.productRating = productRating;
//        this.imageStar1 = imageStar1;
//        this.imageStar2 = imageStar2;
//        this.imageStar3 = imageStar3;
    }

    public String getImageFood() {
        return imageFood;
    }

    public int getFoodCategory() {
        return foodCategory;
    }

    public String getFoodName() {
        return foodName;
    }

    public Object getReviewContent() {
        return reviewContent;
    }

    public int getProductRating() {
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
