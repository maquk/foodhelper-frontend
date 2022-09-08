package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
    }

    public void goToRecipeActivity(View view) {
        // add ingredient to Recipe

        Intent intent = new Intent(this, RecipeActivity.class);

        startActivity(intent);
    }

    public void goToProductActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);

        startActivity(intent);
    }
}