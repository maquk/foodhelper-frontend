package com.maquk.foodhelperapp.pojo;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private Long id;
    private String name;
    private Integer preparationTime;
    private String description;
    private String recipeDifficulty;
    private List<ProductConsumed> products;

    public Recipe() {
        products = new ArrayList<>();
    }

    public Recipe(String name, Integer preparationTime, String description, String recipeDifficulty, List<ProductConsumed> products) {
        this.name = name;
        this.preparationTime = preparationTime;
        this.description = description;
        this.recipeDifficulty = recipeDifficulty;
        this.products = products;
    }

    public void addToList(ProductConsumed productConsumed) {
        products.add(productConsumed);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipeDifficulty() {
        return recipeDifficulty;
    }

    public void setRecipeDifficulty(String recipeDifficulty) {
        this.recipeDifficulty = recipeDifficulty;
    }

    public List<ProductConsumed> getProducts() {
        return products;
    }

    public void setProducts(List<ProductConsumed> products) {
        this.products = products;
    }
}
