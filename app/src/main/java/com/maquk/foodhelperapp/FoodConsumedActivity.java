package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FoodConsumedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_consumed);
    }

    public void goToSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);

        startActivity(intent);
    }

    public void addWaterNotification(View view) {
        Context context = getApplicationContext();

        TextView waterAmount = (TextView) findViewById(R.id.waterAmountTextView);

        CharSequence notificationText = "Water added (" + waterAmount.getText().toString() + " ml)";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, notificationText, duration);
        toast.show();
    }
}