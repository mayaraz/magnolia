package com.example.magnolia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps NEW PLANT */
    public void newPlantPage(View view) {
        Intent intent = new Intent(this, PlantConfig.class);
        startActivity(intent);
    }

    /** Called when the user taps GO TO YOUR GARDEN */
    public void gardenPage(View view) {
        Intent intent = new Intent(this, Garden.class);
        startActivity(intent);
    }
}