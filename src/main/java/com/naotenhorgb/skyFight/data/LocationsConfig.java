package com.naotenhorgb.skyFight.data;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.naotenhorgb.skyFight.SkyFight;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationsConfig {

    public String void_yloc = " ";
    public String lobby_spawn = " ";
    public String game_spawn = " ";
    public List<String> game_safezone = new ArrayList<>();
    public List<String> game_boundaries = new ArrayList<>();

    private static LocationsConfig locationsConfig;

    public static LocationsConfig getLocationsConfig() {
        if (locationsConfig == null) {
            locationsConfig = load();
        }
        return locationsConfig;
    }

    public static LocationsConfig load() {
        try {
            File folder = new File("plugins/SkyFight");
            folder.mkdir();

            File messagesFile = new File(folder, "locations.yml");

            if (!messagesFile.exists()) {
                URL url = SkyFight.class.getResource("/locations.yml");
                FileUtils.copyURLToFile(Objects.requireNonNull(url), messagesFile);
            }

            InputStream inputStream = Files.newInputStream(messagesFile.toPath());
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            YamlReader yamlReader = new YamlReader(reader);
            LocationsConfig config = yamlReader.read(LocationsConfig.class);
            locationsConfig = config;
            return config;

        } catch (IOException exception) {
            throw new RuntimeException("Não foi possível carregar o locations.yml", exception);
        }
    }

    public void save() {
        try {
            File file = new File("plugins/SkyFight/locations.yml");
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8
            ));

            YamlWriter yamlWriter = new YamlWriter(writer);
            yamlWriter.getConfig().writeConfig.setWriteClassname(YamlConfig.WriteClassName.NEVER);
            yamlWriter.write(this);
            yamlWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o locations.yml", e);
        }
    }

    public static String get(String key) {
        try {
            return (String) locationsConfig.getClass().getField(key).get(locationsConfig);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

}

