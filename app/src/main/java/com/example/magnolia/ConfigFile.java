package com.example.magnolia;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ConfigFile {
    protected static String configFileName = "config.json";

    public static void writeJson(JSONObject configJson, Context context) {
        try {
            FileOutputStream fos =  context.openFileOutput(configFileName, Context.MODE_PRIVATE);
            fos.write(configJson.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("ConfigFile", String.format("Can't write to file %s", configFileName));
        }
    }

    public static JSONObject readJson(Context context) {
        String data = "";
        JSONObject configJson = null;

        try {
            File configFile = new File(context.getFilesDir(), configFileName);
            Scanner myReader = new Scanner(configFile);

            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            Log.e("ConfigFile", "Error while trying to read config");

        }
        try {
            configJson =  new JSONObject(data);
        } catch (JSONException e) {
            Log.e("ConfigFile", "error while trying to create new JSONObject");
        }

        return configJson;
    }
}
