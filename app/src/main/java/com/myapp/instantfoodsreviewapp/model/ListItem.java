package com.myapp.instantfoodsreviewapp.model;

public class ListItem {
    private String imageFood;
    private int foodCategory;
    private String foodName;
    private Object reviewContent;
    private int productRating;
    private FoodCategoryList foodType;

    public ListItem(String imageFood, int foodCategory, String foodName, Object reviewContent, int productRating) {
        this.imageFood = imageFood;
        this.foodCategory = foodCategory;
        this.foodName = foodName;
        this.reviewContent = reviewContent;
        this.productRating = productRating;
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

    public FoodCategoryList getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodCategoryList foodType) {
        this.foodType = foodType;
    }
}
