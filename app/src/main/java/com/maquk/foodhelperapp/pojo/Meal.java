package com.maquk.foodhelperapp.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meal {
    private Long id;
    private Date date;
    private List<ProductConsumed> productConsumeds;
    private List<String> mealNames;

    public Meal(Date date, List<ProductConsumed> productConsumeds) {
        this.date = date;
        this.productConsumeds = productConsumeds;
        this.mealNames = new ArrayList<>();
    }

    public List<String> getMealNames() {
        return mealNames;
    }

    public void addToList(String name) {
        mealNames.add(name);
    }

    public void setMealNames(List<String> mealNames) {
        this.mealNames = mealNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductConsumed> getProductConsumeds() {
        return productConsumeds;
    }

    public void setProductConsumeds(List<ProductConsumed> productConsumeds) {
        this.productConsumeds = productConsumeds;
    }
}
