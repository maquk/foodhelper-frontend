package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.maquk.foodhelperapp.pojo.Water;
import com.maquk.foodhelperapp.pojo.Weight;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeightActivity extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    EditText weightEditText;
    EditText waterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        weightEditText = findViewById(R.id.weightEditText);
        waterEditText = findViewById(R.id.waterEditText);
    }

    public void saveWeight(View view) {
        Long kilograms = Long.valueOf(weightEditText.getText().toString());
        Weight weight = new Weight(null, BigDecimal.valueOf(kilograms));
        Call<Weight> call = apiInterface.addWeight(weight);
        call.enqueue(new Callback<Weight>() {
            @Override
            public void onResponse(Call<Weight> call, Response<Weight> response) {
                Toast.makeText(getApplicationContext(), "Current weight submitted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Weight> call, Throwable t) {
                call.cancel();
            }

        });

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public void saveWater(View view) {
        if(!waterEditText.getText().toString().matches("")) {
            MainActivity.waterNeed = BigDecimal.valueOf(Long.parseLong(waterEditText.getText().toString()));
        }

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}