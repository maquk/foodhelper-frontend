package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TableLayout tableLayout;
    EditText recipeNameEditText;
    String chosenIngredientName;
    Recipe recipe = new Recipe();
    List<ProductConsumed> products;
    boolean passedRecipe;
    boolean passedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        tableLayout = findViewById(R.id.recipeTable);
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
        for (ProductConsumed product : products) {
            tableLayout.removeAllViews();
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
        String name = getIntent().getExtras().getString("RECIPE_NAME");
        Call<Recipe> call = apiInterface.getRecipe(name);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                tableLayout.removeAllViews();
                recipe = response.body();
                for (ProductConsumed product : recipe.getProducts()) {
                    TableRow row = new TableRow(tableLayout.getContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(row.getContext());
                    tv.setText(product.getProduct().getName());
                    tv.setOnClickListener(view -> {
                        Toast.makeText(getApplicationContext(), "I clicked " + product.getProduct().getName(), Toast.LENGTH_SHORT).show();
                        chosenIngredientName = product.getProduct().getName();
                    });
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

        startActivity(intent);
    }

    public void goToMealActivity(View view) {
        recipe.setName(recipeNameEditText.getText().toString());
        recipe.setDescription("");
        recipe.setPreparationTime(0);
        recipe.setRecipeDifficulty("BEGINNER");
        Call<Recipe> call = apiInterface.createRecipe(recipe);
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


        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            if(extras.containsKey("RECIPE_NAME")) {
                passedRecipe = true;
            }
            if(extras.containsKey("PRODUCT_NAME") && extras.containsKey("GRAMS")) {
                passedProduct = true;
            }
        }
        if(passedRecipe) {
            setIngredients();
        } else if(passedProduct){
            products = recipe.getProducts();
            Call<Product> call = apiInterface.getProduct(getIntent().getExtras().getString("PRODUCT_NAME"));
            for (ProductConsumed product: products) {
                TableRow row = new TableRow(tableLayout.getContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TextView tv = new TextView(row.getContext());
                tv.setText(product.getProduct().getName());
                tv.setOnClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "I clicked " + product.getProduct().getName(), Toast.LENGTH_SHORT).show();
                    chosenIngredientName = product.getProduct().getName();
                });
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
                            tableLayout.addView(row);
                            Product productFromResponse = response.body();
                            addProductToList(productFromResponse);
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {
                            call.cancel();
                        }
                    });
        }
    }

    public void addProductToList(Product product) {
        products.add(new ProductConsumed(product, BigDecimal.valueOf(getIntent().getExtras().getLong("GRAMS"))));
        recipe.setProducts(products);
        for (ProductConsumed product2:
                recipe.getProducts()) {
            System.out.println("PO DODANIU");
            System.out.println(product2.toString());
        }
    }
}