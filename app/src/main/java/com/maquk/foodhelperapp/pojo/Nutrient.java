package com.maquk.foodhelperapp.pojo;

import java.math.BigDecimal;
import java.util.List;

public class Nutrient {
    private List<Meal> meals;
    private BigDecimal calories = BigDecimal.ZERO;
    private BigDecimal protein = BigDecimal.ZERO;
    private BigDecimal fat = BigDecimal.ZERO;
    private BigDecimal carbohydrates = BigDecimal.ZERO;
    private String nutrientValue;

    public Nutrient() {
    }

    public Nutrient(List<Meal> meals, BigDecimal calories, BigDecimal protein, BigDecimal fat, BigDecimal carbohydrates, String nutrientValue) {
        this.meals = meals;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.nutrientValue = nutrientValue;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public BigDecimal getCalories() {
        return calories;
    }

    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }

    public BigDecimal getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(BigDecimal carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getNutrientValue() {
        return nutrientValue;
    }

    public void setNutrientValue(String nutrientValue) {
        this.nutrientValue = nutrientValue;
    }
}
