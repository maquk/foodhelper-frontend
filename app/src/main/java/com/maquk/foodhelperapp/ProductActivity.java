package com.maquk.foodhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maquk.foodhelperapp.pojo.Product;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText producerEditText;
    EditText gramsProductEditText;
    EditText caloriesEditText;
    EditText proteinEditText;
    EditText fatEditText;
    EditText carbohydratesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        nameEditText = findViewById(R.id.productNameEditText);
        producerEditText = findViewById(R.id.producerEditText);
        gramsProductEditText = findViewById(R.id.gramsProductEditText);
        caloriesEditText = findViewById(R.id.caloriesEditText);
        proteinEditText = findViewById(R.id.proteinEditText);
        fatEditText = findViewById(R.id.fatEditText);
        carbohydratesEditText = findViewById(R.id.carbohydratesEditText);
    }

    public void goToIngredientActivity(View view) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


        Product product = new Product(nameEditText.getText().toString(),
                producerEditText.getText().toString(),
                BigDecimal.valueOf(Long.parseLong(gramsProductEditText.getText().toString())),
                BigDecimal.valueOf(Long.parseLong(caloriesEditText.getText().toString())),
                BigDecimal.valueOf(Long.parseLong(proteinEditText.getText().toString())),
                BigDecimal.valueOf(Long.parseLong(fatEditText.getText().toString())),
                BigDecimal.valueOf(Long.parseLong(carbohydratesEditText.getText().toString())));

        Call<Product> call = apiInterface.createProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast.makeText(getApplicationContext(),
                        "Product " + nameEditText.getText().toString() + " created!",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                call.cancel();
            }

        });

        Intent intent = new Intent(this, IngredientActivity.class);

        finish();
    }
}