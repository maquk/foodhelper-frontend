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
import com.maquk.foodhelperapp.pojo.Recipe;
import com.maquk.foodhelperapp.pojo.Water;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealActivity extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TableLayout tableLayout;
    String chosenRecipe;
    EditText recipeEditText;
    EditText portionEditText;
    EditText waterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        tableLayout = findViewById(R.id.recipeTable);
        recipeEditText = findViewById(R.id.recipeEditText);
        waterEditText = findViewById(R.id.waterEditText);
        portionEditText = findViewById(R.id.portionEditText);
    }

    public void addWater(View view) {
        Long amount = Long.valueOf(waterEditText.getText().toString());
        Water water = new Water(null, BigDecimal.valueOf(amount));
        Call<Water> call = apiInterface.addWater(water);
        call.enqueue(new Callback<Water>() {
            @Override
            public void onResponse(Call<Water> call, Response<Water> response) {
                Toast.makeText(getApplicationContext(), amount + " ml of water added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Water> call, Throwable t) {
                call.cancel();
            }

        });
    }

    public void subtractWater(View view) {
        Long amount = Long.parseLong(waterEditText.getText().toString());
        Water water = new Water(null, BigDecimal.valueOf(-amount));
        Call<Water> call = apiInterface.addWater(water);
        call.enqueue(new Callback<Water>() {
            @Override
            public void onResponse(Call<Water> call, Response<Water> response) {
                Toast.makeText(getApplicationContext(), amount + " ml of water added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Water> call, Throwable t) {
                call.cancel();
            }

        });
    }

    public void searchForRecipe(View view) {
        String name = recipeEditText.getText().toString();
        if(name.equals("")) {
            getRecipes();
            return;
        } else {
            Call<Recipe> call = apiInterface.getRecipe(name);
            call.enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    tableLayout.removeAllViews();
                    Recipe recipe = response.body();
                    TableRow row = new TableRow(tableLayout.getContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(row.getContext());
                    tv.setText(recipe.getName());
                    chosenRecipe = recipe.getName();
                    tv.setOnClickListener(view -> {
                                Toast.makeText(getApplicationContext(), "I clicked " + recipe.getName(), Toast.LENGTH_SHORT).show();
                                chosenRecipe = recipe.getName();
                            }
                    );
                    row.addView(tv);
                    tableLayout.addView(row);
                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {
                    call.cancel();
                }

            });
        }

    }

    public void goToMainActivity(View view) {
        Long portions = Long.parseLong(portionEditText.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("MEAL_NAME", chosenRecipe);
        intent.putExtra("PORTIONS", portions);

        startActivity(intent);
    }

    public void createNewRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    public void copyRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("RECIPE_NAME", chosenRecipe);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecipes();
    }

    private void getRecipes() {
        Call<List<Recipe>> call = apiInterface.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                tableLayout.removeAllViews();
                List<Recipe> recipes = response.body();
                for (Recipe recipe : recipes) {
                    TableRow row = new TableRow(tableLayout.getContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(row.getContext());
                    tv.setText(recipe.getName());
                    tv.setOnClickListener(view -> {
                                Toast.makeText(getApplicationContext(), "I clicked " + recipe.getName(), Toast.LENGTH_SHORT).show();
                                chosenRecipe = recipe.getName().toString();
                            }
                    );
                    row.addView(tv);
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                call.cancel();
            }

        });
    }


}