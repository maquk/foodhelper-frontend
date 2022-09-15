package com.maquk.foodhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maquk.foodhelperapp.pojo.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientActivity extends AppCompatActivity {

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TableLayout tableLayout;
    EditText ingredientEditText;
    EditText gramsEditText;
    String chosenProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        tableLayout = findViewById(R.id.productTable);
        ingredientEditText = findViewById(R.id.ingredientEditText);
        gramsEditText = findViewById(R.id.gramsEditText);


    }

    private void getProducts() {
        Call<List<Product>> call = apiInterface.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tableLayout.removeAllViews();
                List<Product> products = response.body();
                for (Product product : products) {
                    TableRow row = new TableRow(tableLayout.getContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(row.getContext());
                    tv.setText(product.getName());
                    tv.setOnClickListener(view -> {
                        Toast.makeText(getApplicationContext(), "I clicked " + product.getName(), Toast.LENGTH_SHORT).show();
                        chosenProductName = product.getName().toString();
                        }
                    );
                    row.addView(tv);
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                call.cancel();
            }

        });
    }

    public void goToRecipeActivity(View view) {
        Long grams = Long.parseLong(gramsEditText.getText().toString());
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("GRAMS", grams);
        intent.putExtra("PRODUCT_NAME", chosenProductName);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void goToProductActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }

    public void searchForProduct(View view) {
        String name = ingredientEditText.getText().toString();
        if(name.equals("")) {
            getProducts();
            return;
        } else {
            Call<Product> call = apiInterface.getProduct(name);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    tableLayout.removeAllViews();
                    Product product = response.body();
                    TableRow row = new TableRow(tableLayout.getContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(row.getContext());
                    tv.setText(product.getName());
                    chosenProductName = product.getName();
                    tv.setOnClickListener(view -> {
                                Toast.makeText(getApplicationContext(), "I clicked " + product.getName(), Toast.LENGTH_SHORT).show();
                                chosenProductName = product.getName();
                            }
                    );
                    row.addView(tv);
                    tableLayout.addView(row);
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    call.cancel();
                }

            });
        }

    }
}