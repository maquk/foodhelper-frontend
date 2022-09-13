package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AnyChartView chartWeight = findViewById(R.id.chartWeight);
        setChartWeight(chartWeight);

        AnyChartView chartBMI = findViewById(R.id.chartBMI);
        setChartBMI(chartBMI);
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
        seriesData.add(new ValueDataEntry("2022-09-07", 20.8));
        seriesData.add(new ValueDataEntry("2022-09-08", 24.2));
        seriesData.add(new ValueDataEntry("2022-09-09", 27.7));
        seriesData.add(new ValueDataEntry("2022-09-10", 31.1));
        seriesData.add(new ValueDataEntry("2022-09-11", 34.6));
        seriesData.add(new ValueDataEntry("2022-09-12", 38.1));
        seriesData.add(new ValueDataEntry("2022-09-13", 31.1));

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
        seriesData.add(new ValueDataEntry("2022-09-07", 60));
        seriesData.add(new ValueDataEntry("2022-09-08", 70));
        seriesData.add(new ValueDataEntry("2022-09-09", 80));
        seriesData.add(new ValueDataEntry("2022-09-10", 90));
        seriesData.add(new ValueDataEntry("2022-09-11", 100));
        seriesData.add(new ValueDataEntry("2022-09-12", 110));
        seriesData.add(new ValueDataEntry("2022-09-13", 90));

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
        //calculate BMI and refresh BMI graph


    }
}