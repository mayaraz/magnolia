package com.example.magnolia;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class PlantConfig extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_plant_config);
    }

    protected String getNextStageTime(int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, interval);
        return calendar.getTime().toString();
    }

    public void onSave(View view) {

        final EditText plantName = (EditText) findViewById(R.id.editTextPlantName);
        final EditText plantInterval = (EditText) findViewById(R.id.editTextPlantInterval);
        JSONObject plantConfig = new JSONObject();
        String plantNameValue = plantName.getText().toString();
        String plantIntervalValue = plantInterval.getText().toString();
        String nextStageTime = getNextStageTime(Integer.parseInt(plantIntervalValue));

        try {
            plantConfig.put("plantName", plantNameValue);
            plantConfig.put("plantInterval", plantIntervalValue);
            plantConfig.put("nextStageTime", nextStageTime);
            plantConfig.put("currentStage", 1);

        } catch (JSONException e) {
            Log.e("PlantConfig", "Can't write to json");
        }

        ConfigFile.writeJson(plantConfig, this.getApplicationContext());

        Intent intent = new Intent(this, Garden.class);
        startActivity(intent);

    }

}