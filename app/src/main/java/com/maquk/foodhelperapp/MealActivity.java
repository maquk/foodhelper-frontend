package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
    }

    public void addWater(View view) {
        //add water to database
    }

    public void subtractWater(View view) {
        //subtract water to database
    }

    public void searchForRecipe(View view) {
        //search for recipe and refresh recipe list


    }

    public void goToMainActivity(View view) {
        //add Meal to database

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public void createNewRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);

        startActivity(intent);
    }

    public void copyRecipe(View view) {
        // add values from selected Recipe to next activity

        Intent intent = new Intent(this, RecipeActivity.class);

        startActivity(intent);
    }
}