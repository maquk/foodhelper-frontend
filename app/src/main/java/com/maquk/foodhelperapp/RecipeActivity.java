package com.maquk.foodhelperapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.maquk.foodhelperapp.pojo.Product;
import com.maquk.foodhelperapp.pojo.ProductConsumed;
import com.maquk.foodhelperapp.pojo.Recipe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {
    private static final int INGREDIENT_ACTIVITY_REQUEST_CODE = 0;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TableLayout tableLayout;
    EditText recipeNameEditText;
    String chosenIngredientName;
    Recipe recipe = new Recipe();
    List<ProductConsumed> products = new ArrayList<>();
    String returnedProductName;
    Long grams;
    boolean alreadyAdded;
    boolean passedRecipe;
    boolean passedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        tableLayout = findViewById(R.id.ingredientTable);
        recipeNameEditText = findViewById(R.id.recipeNameEditText);

    }

    public void deleteIngredient(View view) {
        List<ProductConsumed> products = recipe.getProducts();
        for (ProductConsumed product: products) {
            if(product.getProduct().getName().equals(chosenIngredientName)) {
                products.remove(product);
                break;
            }
        }
        recipe.setProducts(products);
        tableLayout.removeAllViews();
        for (ProductConsumed product : products) {
            TableRow row = new TableRow(tableLayout.getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tv = new TextView(row.getContext());
            tv.setText(product.getProduct().getName());
            tv.setOnClickListener(view2 -> {
                Toast.makeText(getApplicationContext(), "I clicked " + product.getProduct().getName(), Toast.LENGTH_SHORT).show();
                chosenIngredientName = product.getProduct().getName();
            });
            row.addView(tv);
            tableLayout.addView(row);
        }
    }

    private void setIngredients() {
        alreadyAdded = true;
        String name = getIntent().getExtras().getString("RECIPE_NAME");
        Call<Recipe> call = apiInterface.getRecipe(name);
        call.enqueue(new Callback<Recipe>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                tableLayout.removeAllViews();
                recipe = response.body();
                products.addAll(recipe.getProducts());
                for (ProductConsumed product : recipe.getProducts()) {
                    TableRow row = new TableRow(tableLayout.getContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(row.getContext());
                    tv.setText(product.getProduct().getName() + ", " + product.getProduct().getGrams() + "g");
                    tv.setOnClickListener(view -> {
                        Toast.makeText(getApplicationContext(), "I clicked " + product.getProduct().getName(), Toast.LENGTH_SHORT).show();
                        chosenIngredientName = product.getProduct().getName();
                    });
                    System.out.println("I ADD THIS TO VIEW (SET INGREDIENTS)" + tv.getText());
                    row.addView(tv);
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                call.cancel();
            }

        });
    }

    public void goToIngredientActivity(View view) {
        Intent intent = new Intent(this, IngredientActivity.class);

        startActivityForResult(intent, INGREDIENT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INGREDIENT_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                returnedProductName = data.getStringExtra("PRODUCT_NAME");
                grams = data.getLongExtra("GRAMS", 0);
                passedProduct = true;
                passedRecipe = false;
            }
        }
    }

    public void goToMealActivity(View view) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipeNameEditText.getText().toString());
        newRecipe.setDescription("");
        newRecipe.setPreparationTime(0);
        newRecipe.setRecipeDifficulty("BEGINNER");
        newRecipe.setProducts(products);
        Call<Recipe> call = apiInterface.createRecipe(newRecipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Toast.makeText(getApplicationContext(),
                        "Product " + recipeNameEditText.getText().toString() + " created!",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                call.cancel();
            }

        });

        Intent intent = new Intent(this, MealActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("Produkty przy odświeżeniu widoku: " + products.toString());

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            if (extras.containsKey("RECIPE_NAME")) {
                if (extras.getString("RECIPE_NAME").length() > 0) {
                    passedRecipe = true;
                    extras.remove("RECIPE_NAME");
                }
            }
        }
        if(passedRecipe && !alreadyAdded) {
            setIngredients();
            passedRecipe = false;
        }
        if(passedProduct){
            System.out.println("Produkty przed dodaniem z bazy: " + products.toString());
            Call<Product> call = apiInterface.getProduct(returnedProductName);
            tableLayout.removeAllViews();
            for (ProductConsumed product: products) {
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(row.getContext());
                tv.setText(product.getProduct().getName());
                tv.setOnClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "I clicked " + product.getProduct().getName(), Toast.LENGTH_SHORT).show();
                    chosenIngredientName = product.getProduct().getName();
                });
                System.out.println("I ADD THIS TO VIEW (PASSED PRODUCT 1)" + tv.getText());
                row.addView(tv);
                tableLayout.addView(row);
            }
            call.enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {

                            TableRow row = new TableRow(tableLayout.getContext());
                            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            TextView tv = new TextView(row.getContext());
                            tv.setText(response.body().getName());
                            tv.setOnClickListener(view -> {
                                Toast.makeText(getApplicationContext(), "I clicked " + response.body().getName(), Toast.LENGTH_SHORT).show();
                                chosenIngredientName = tv.getText().toString();
                            });
                            row.addView(tv);
                            System.out.println("I ADD THIS TO VIEW (PASSED PRODUCT 2)" + tv.getText());
                            tableLayout.addView(row);
                            Product productFromResponse = response.body();
                            products.add(new ProductConsumed(productFromResponse, BigDecimal.valueOf(grams)));
                            recipe.setProducts(products);
                            System.out.println("Produkty PO dodaniem z bazy: " + products.toString());
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {
                            call.cancel();
                        }
                    });
        }
    }
}