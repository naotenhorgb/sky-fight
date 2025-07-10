package com.naotenhorgb.skyFight.managers;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class LocationsConfig {

    public String lobby_spawn = "world:0:100:0";
    public String game_spawn = "world:0:100:0";
    public List<String> game_safezone = new ArrayList<>();
    public List<String> game_boundaries = new ArrayList<>();

    private static final File file = new File("plugins/SkyFight/locations.yml");

    public void save() {
        try {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("lobby_spawn", lobby_spawn.replace(":", " "));
            data.put("game_spawn", game_spawn.replace(":", " "));
            data.put("game_safezone", replaceInList(game_safezone, ":", " "));
            data.put("game_boundaries", replaceInList(game_boundaries, ":", " "));

            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                Yaml yaml = new Yaml(options);
                yaml.dump(data, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LocationsConfig load() {
        LocationsConfig config = new LocationsConfig();

        if (!file.exists()) {
            config.save();
            return config;
        }

        try (FileReader reader = new FileReader(file)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = (Map<String, Object>) yaml.load(reader);

            config.lobby_spawn = ((String) data.getOrDefault("lobby_spawn", config.lobby_spawn)).replace(" ", ":");
            config.game_spawn = ((String) data.getOrDefault("game_spawn", config.game_spawn)).replace(" ", ":");

            config.game_safezone = replaceInList((List<String>) data.getOrDefault("game_safezone", new ArrayList<>()), " ", ":");
            config.game_boundaries = replaceInList((List<String>) data.getOrDefault("game_boundaries", new ArrayList<>()), " ", ":");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

    private static List<String> replaceInList(List<String> list, String from, String to) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(s.replace(from, to));
        }
        return result;
    }
}
