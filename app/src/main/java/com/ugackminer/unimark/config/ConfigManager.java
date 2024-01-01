package com.ugackminer.unimark.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.event.KeyEvent;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;

import okio.Okio;

import com.ugackminer.unimark.robot.RobotManager;

public class ConfigManager {
    
    @Json(ignore=true)
    static final File configFile = new File("./unimark-config.json");

    public boolean isDisabled = false;
    public int[] keyboardShortcut = {RobotManager.modifierKey, KeyEvent.VK_M};

    public void saveConfig() {
        try {
            configFile.createNewFile();
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<ConfigManager> adapter = moshi.adapter(ConfigManager.class);
            JsonWriter writer = JsonWriter.of(Okio.buffer(Okio.sink(configFile)));

            adapter.toJson(writer, this);

            writer.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    
    public static ConfigManager loadConfig() throws FileNotFoundException, IOException {
        if (!configFile.exists())
            throw new FileNotFoundException();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<ConfigManager> adapter = moshi.adapter(ConfigManager.class);

        JsonReader reader = JsonReader.of(Okio.buffer(Okio.source(configFile)));
        ConfigManager previousConfig = adapter.fromJson(reader);
        reader.close();

        return previousConfig;        
    }







}
