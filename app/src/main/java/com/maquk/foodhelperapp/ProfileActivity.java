package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.maquk.foodhelperapp.pojo.Product;
import com.maquk.foodhelperapp.pojo.Weight;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<Weight> weights = new ArrayList<>();
    BigDecimal height;
    EditText heightEditText;
    AnyChartView chartWeight;
    AnyChartView chartBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        heightEditText = findViewById(R.id.heightEditText);

        getWeights();

        chartWeight = findViewById(R.id.chartWeight);
        chartBMI = findViewById(R.id.chartBMI);
    }

    private void getWeights() {
        Call<List<Weight>> call = apiInterface.findAllByDateBetween(LocalDate.now().minusMonths(1), LocalDate.now());
        call.enqueue(new Callback<List<Weight>>() {
            @Override
            public void onResponse(Call<List<Weight>> call, Response<List<Weight>> response) {
                weights.addAll(response.body());
            }
            @Override
            public void onFailure(Call<List<Weight>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setChartBMI(AnyChartView chart) {
        APIlib.getInstance().setActiveAnyChartView(chart);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("BMI over time chart");

        cartesian.yAxis(0).title("BMI");
        cartesian.xAxis(0).labels().padding(5d);

        List<DataEntry> seriesData = new ArrayList<>();

        for (Weight weight : weights
        ) {
            BigDecimal weightInKg = weight.getKilograms();
            BigDecimal heightInM = height.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            seriesData.add(new ValueDataEntry(String.valueOf(weight.getDate()), weightInKg.divide(heightInM.pow(2), 2, RoundingMode.HALF_UP)));

        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series = cartesian.line(seriesMapping);
        series.name("BMI");
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        chart.setChart(cartesian);
    }

    public void setChartWeight(AnyChartView chart) {
        APIlib.getInstance().setActiveAnyChartView(chart);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Weight on time chart");

        cartesian.yAxis(0).title("Weight (kg)");
        cartesian.xAxis(0).labels().padding(5d);

        List<DataEntry> seriesData = new ArrayList<>();


        for (Weight weight : weights
             ) {
            seriesData.add(new ValueDataEntry(String.valueOf(weight.getDate()), weight.getKilograms()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series = cartesian.line(seriesMapping);
        series.name("Weight");
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        chart.setChart(cartesian);
    }
    
    public void saveHeight(View view) {
        height = BigDecimal.valueOf(Long.parseLong(heightEditText.getText().toString()));

        setChartWeight(chartWeight);
        setChartBMI(chartBMI);

    }
}