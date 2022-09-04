package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.maquk.foodhelperapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToFoodConsumedActivity(View view) {
        Intent intent = new Intent(this, FoodConsumedActivity.class);

        startActivity(intent);
    }

    public void goToDatabaseActivity(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);

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