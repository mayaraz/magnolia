package com.example.magnolia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlantConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_plant_config);
    }
}