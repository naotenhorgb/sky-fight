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

    public int void_yloc = 64;
    public String lobby_spawn = "world:0:100:0:0:0";
    public String game_spawn = "world:0:100:0:0:0";
    public List<String> game_safezone = new ArrayList<>();
    public List<String> game_boundaries = new ArrayList<>();

    private static LocationsConfig instance;

    public static LocationsConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
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
            instance = config;
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

}

