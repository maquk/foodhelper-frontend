package com.maquk.foodhelperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.maquk.foodhelperapp.pojo.Meal;
import com.maquk.foodhelperapp.pojo.Nutrient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    TableLayout tableLayout;
    String date = "SELECT DATE";
    Nutrient nutrient = new Nutrient();
    Button button;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.mealTable);
        button = (Button) findViewById(R.id.dateButton);
        bundle = savedInstanceState;
        refreshDate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDate(bundle);
    }

    private void refreshDate(Bundle bundle) {
        if(bundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras!= null) {
                date = extras.getString("STRING_DATE");
            } else {
                date = LocalDate.now().toString();
                System.out.println(date);
            }
        } else {
            date = (String) bundle.getSerializable("STRING_DATE");
        }
        Button button = (Button) findViewById(R.id.dateButton);
        button.setText(date);

        if(!date.equals("SELECT DATE")) {
            Call<Nutrient> call = apiInterface.findAllByDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            call.enqueue(new Callback<Nutrient>() {
                @Override
                public void onResponse(Call<Nutrient> call, Response<Nutrient> response) {
                    tableLayout.removeAllViews();
                    nutrient = response.body();
                    for (Meal meal: nutrient.getMeals()
                         ) {

                        for (String name : meal.getMealNames()
                             ) {
                            TableRow row = new TableRow(tableLayout.getContext());
                            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            TextView tv = new TextView(row.getContext());
                            tv.setText(name);
                            tv.setOnClickListener(view -> {
                                        Toast.makeText(getApplicationContext(), "I clicked " + name, Toast.LENGTH_SHORT).show();
                                    }
                            );
                            row.addView(tv);
                            tableLayout.addView(row);
                        }
                    }
                    AnyChartView chartCalories = (AnyChartView) findViewById(R.id.chartCalories);
                    setupPieChart(chartCalories, "Calories");

                    AnyChartView chartFat = (AnyChartView) findViewById(R.id.chartFat);
                    setupPieChart(chartFat, "Fat");

                    AnyChartView chartProtein = (AnyChartView) findViewById(R.id.chartProtein);
                    setupPieChart(chartProtein, "Protein");

                    AnyChartView chartCarbohydrates = (AnyChartView) findViewById(R.id.chartCarbohydrates);
                    setupPieChart(chartCarbohydrates, "Carbohydrates");
                }

                @Override
                public void onFailure(Call<Nutrient> call, Throwable t) {
                    System.out.println("CALL FOR MEALS FAILED!");
                    call.cancel();
                }

            });
        }
    }

    public void setupPieChart(AnyChartView chart, String title) {
        APIlib.getInstance().setActiveAnyChartView(chart);
        Pie pie = AnyChart.pie();
        pie.title(title);
        List<DataEntry> dataEntries = new ArrayList<>();
        switch(title) {
            case "Calories":
                dataEntries.add(new ValueDataEntry("Consumed", nutrient.getCalories()));
                dataEntries.add(new ValueDataEntry("Left", BigDecimal.valueOf(1800).subtract(nutrient.getCalories())));
                break;
            case "Fat":
                dataEntries.add(new ValueDataEntry("Consumed", nutrient.getFat()));
                dataEntries.add(new ValueDataEntry("Left", BigDecimal.valueOf(60).subtract(nutrient.getFat())));
                break;
            case "Protein":
                dataEntries.add(new ValueDataEntry("Consumed", nutrient.getProtein()));
                dataEntries.add(new ValueDataEntry("Left", BigDecimal.valueOf(133).subtract(nutrient.getProtein())));
                break;
            case "Carbohydrates":
                dataEntries.add(new ValueDataEntry("Consumed", nutrient.getCarbohydrates()));
                dataEntries.add(new ValueDataEntry("Left", BigDecimal.valueOf(19).subtract(nutrient.getCarbohydrates())));
                break;
            default:
                break;
        }
        pie.data(dataEntries);
        chart.setChart(pie);
    }

    public void goToCalendarActivity(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("STRING_DATE", button.getText());

        startActivity(intent);

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