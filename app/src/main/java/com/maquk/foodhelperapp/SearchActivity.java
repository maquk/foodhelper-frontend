package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void addFoodConsumed(View view) {
        // pass data to food consumed table

        Intent intent = new Intent(this, FoodConsumedActivity.class);

        startActivity(intent);
    }

    public void addNewFood(View view) {
        Intent intent = new Intent(this, NewFoodActivity.class);

        startActivity(intent);
    }
}