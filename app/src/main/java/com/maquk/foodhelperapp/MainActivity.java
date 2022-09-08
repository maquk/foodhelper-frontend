package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectDate(View view) {
        // select date and refresh list and graphs


    }

    public void goToMealActivity(View view) {
        Intent intent = new Intent(this, MealActivity.class);

        startActivity(intent);
    }

    public void goToWeightActivity(View view) {
        Intent intent = new Intent(this, WeightActivity.class);

        startActivity(intent);
    }

    public void goToProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);

        startActivity(intent);
    }

    public void goToTipActivity(View view) {
        Intent intent = new Intent(this, TipActivity.class);

        startActivity(intent);
    }
}