package com.example.magnolia;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Garden extends AppCompatActivity {

    protected void increaseStage(JSONObject configJson, int stagesAmount) {
        int currentStage = 0;
        try {
            currentStage = configJson.getInt("currentStage");
            currentStage += stagesAmount;
            configJson.put("currentStage", currentStage);
        } catch (JSONException e) {
            Log.e("Garden", "error while trying to read and update configJson");
        }
    }

    protected void updateStage(JSONObject configJson){
        String nextStageTime = null;
        int plantInterval = 0;

        try {
            nextStageTime = configJson.getString("nextStageTime");
            plantInterval = configJson.getInt("plantInterval");
        } catch (JSONException e) {
            Log.e("Garden", "error while trying read configJson");
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date now = calendar.getTime();
        try {
            calendar.setTime(sdf.parse(nextStageTime));
        } catch (ParseException e) {
            Log.e("Garden", "cant set time to calendar instance");
        }
        Date nextStageTimeDate = calendar.getTime();
        if (now.after(nextStageTimeDate)) {

            long differenceInMilliseconds = Math.abs(now.getTime() - nextStageTimeDate.getTime() );
//        long differenceInDays = (differenceInMilliseconds / (1000 * 60 * 60 * 24));
            int differenceInSeconds = (int) differenceInMilliseconds / 1000;
            int stagesAmount = differenceInSeconds / plantInterval;
            int stageRemainder = differenceInSeconds % plantInterval;
            increaseStage(configJson, stagesAmount);
            calendar.add(Calendar.SECOND, plantInterval - stageRemainder);
            try {
                configJson.put("nextStageTime", calendar.getTime().toString());
            } catch (JSONException e) {
                Log.e("Garden", "error while trying to set nextStageTime in configJson");
            }
            ConfigFile.writeJson(configJson, this.getApplicationContext());
        }
    }

    protected int getCurrentStageImgID(JSONObject configJson) {
        int currentStage = 0;

        try {
            currentStage = configJson.getInt("currentStage");
        } catch (JSONException e) {
            Log.e("Garden", "error has occurred while trying to get currentStage from config");
        }
        switch (currentStage) {
            case 1:
                return R.drawable.stage1;
            case 2:
                return R.drawable.stage2;
            case 3:
                return R.drawable.stage3;
            case 4:
                return R.drawable.stage4;
            default:
                return R.drawable.stage5;
        }
    }

    protected void updateStageImg(JSONObject configJson) {
        int drawableID = getCurrentStageImgID(configJson);
        Drawable img = getResources().getDrawable(drawableID);
        ImageView stageImage = (ImageView) findViewById(R.id.stageImage);
        stageImage.setImageDrawable(img);
    }

    protected void updatePlantName(JSONObject configJson) {
        String plantName = null;

        try {
            plantName = configJson.getString("plantName");
        } catch (JSONException e) {
            Log.e("Garden", "error while trying to get plantName from configJson");
        }

        TextView plantNameTextView = (TextView) findViewById(R.id.plantName);
        plantNameTextView.setText(plantName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_garden);
        JSONObject configJson = ConfigFile.readJson(this.getApplicationContext());
        updatePlantName(configJson);
        updateStage(configJson);
        updateStageImg(configJson);
    }
}