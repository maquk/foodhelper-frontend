package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String date = "SELECT DATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnyChartView chartCalories = (AnyChartView) findViewById(R.id.chartCalories);
        setupPieChart(chartCalories, "Calories");

        AnyChartView chartFat = (AnyChartView) findViewById(R.id.chartFat);
        setupPieChart(chartFat, "Fat");

        AnyChartView chartProtein = (AnyChartView) findViewById(R.id.chartProtein);
        setupPieChart(chartProtein, "Protein");

        AnyChartView chartCarbohydrates = (AnyChartView) findViewById(R.id.chartCarbohydrates);
        setupPieChart(chartCarbohydrates, "Carbohydrates");

        refreshDate(savedInstanceState);
    }

    private void refreshDate(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras!= null) date = extras.getString("STRING_DATE");
        } else {
            date = (String) savedInstanceState.getSerializable("STRING_DATE");
        }
        Button button = (Button) findViewById(R.id.dateButton);
        button.setText(date);
    }

    public void setupPieChart(AnyChartView chart, String title) {
        APIlib.getInstance().setActiveAnyChartView(chart);
        Pie pie = AnyChart.pie();
        pie.title(title);
        List<DataEntry> dataEntries = new ArrayList<>();
        dataEntries.add(new ValueDataEntry("Consumed", 30));
        dataEntries.add(new ValueDataEntry("Left", 70));
        pie.data(dataEntries);
        chart.setChart(pie);
    }

    public void goToCalendarActivity(View view) {
        // select date and refresh list and graphs
        Intent intent = new Intent(this, CalendarActivity.class);
        Button button = (Button) findViewById(R.id.dateButton);
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