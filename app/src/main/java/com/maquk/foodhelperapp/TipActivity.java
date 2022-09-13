package com.maquk.foodhelperapp;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        TextView textView = (TextView) findViewById(R.id.tipsTextView);
        Linkify.addLinks(textView, Linkify.ALL);
    }
}