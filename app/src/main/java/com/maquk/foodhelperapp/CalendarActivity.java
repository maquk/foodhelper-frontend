package com.maquk.foodhelperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private final int NUMBER_OF_DAYS_IN_6_WEEKS = 42;
    private final String EMPTY_STRING = "";
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final String SELECT_DATE = "SELECT DATE";
    private final String STRING_DATE = "STRING_DATE";

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_calendar);
        this.initWidgets();
        String datePassed = null;
        Bundle extras = this.getIntent().getExtras();
        if(extras != null) {
            if(!extras.getString(STRING_DATE).isEmpty()) {
                datePassed = extras.getString(STRING_DATE);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        System.out.println(datePassed);
        if(!datePassed.equals(SELECT_DATE)) {
            selectedDate = LocalDate.parse(datePassed, formatter);
        } else {
            selectedDate = LocalDate.now();
        }
        this.setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = this.findViewById(R.id.calendarRecyclerView);
        monthYearText = this.findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(formatDate(selectedDate));
        ArrayList<String> daysInMonth = this.getArrayOfDaysInTheMonth(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> getArrayOfDaysInTheMonth(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearAndMonth = YearMonth.from(date);

        int amountOfDaysInMonth = yearAndMonth.lengthOfMonth();

        LocalDate firstDayOfMonthOfSelectedDate = selectedDate.withDayOfMonth(1);
        int numericValueOfDayOfWeekOfFirstDayOfTheMonthOfSelectedDate = firstDayOfMonthOfSelectedDate.getDayOfWeek().getValue();

        for(int i = 1; i <= NUMBER_OF_DAYS_IN_6_WEEKS; i++)
        {
            if(i <= numericValueOfDayOfWeekOfFirstDayOfTheMonthOfSelectedDate || i > amountOfDaysInMonth + numericValueOfDayOfWeekOfFirstDayOfTheMonthOfSelectedDate)
            {
                daysInMonthArray.add(EMPTY_STRING);
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - numericValueOfDayOfWeekOfFirstDayOfTheMonthOfSelectedDate));
            }
        }
        return daysInMonthArray;
    }

    private String formatDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }

    public void changeScreenToPreviousMonth(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        this.setMonthView();
    }

    public void changeScreenToNextMonth(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        this.setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(EMPTY_STRING))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(STRING_DATE, formatDate(selectedDate.withDayOfMonth(Integer.parseInt(dayText))));
            this.startActivity(i);
        }
    }
}
