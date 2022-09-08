package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    public void deleteIngredient(View view) {
        //delete ingredient from recipe and refresh it
    }

    public void goToIngredientActivity(View view) {
        Intent intent = new Intent(this, IngredientActivity.class);

        startActivity(intent);
    }

    public void goToMealActivity(View view) {
        // add Meal to Meal database

        Intent intent = new Intent(this, MealActivity.class);

        startActivity(intent);
    }
}